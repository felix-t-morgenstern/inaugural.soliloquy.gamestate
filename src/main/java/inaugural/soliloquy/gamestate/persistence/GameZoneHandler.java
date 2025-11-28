package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.collections.Collections;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.setOf;
import static inaugural.soliloquy.tools.concurrency.Concurrency.runTaskAsync;
import static inaugural.soliloquy.tools.concurrency.Concurrency.waitUntilTasksCompleted;
import static soliloquy.specs.common.valueobjects.Coordinate2d.coordinate2dOf;
import static soliloquy.specs.common.valueobjects.Coordinate3d.coordinate3dOf;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class GameZoneHandler extends AbstractTypeHandler<GameZone> {
    private final GameZoneFactory GAME_ZONE_FACTORY;
    private final TypeHandler<Tile> TILE_HANDLER;
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> MAP_HANDLER;
    @SuppressWarnings("rawtypes")
    private final Function<String, soliloquy.specs.common.entities.Consumer> GET_CONSUMER;
    private final int TILES_PER_BATCH;
    private final ExecutorService EXECUTOR;

    private Throwable innerThrowable;
    private final Supplier<Boolean> TASK_HAS_THROWN_EXCEPTION = () -> innerThrowable != null;

    @SuppressWarnings("rawtypes")
    public GameZoneHandler(GameZoneFactory gameZoneFactory,
                           TypeHandler<Tile> tileHandler,
                           TypeHandler<Map> mapHandler,
                           Function<String, soliloquy.specs.common.entities.Consumer> getConsumer,
                           int tilesPerBatch,
                           int threadPoolSize) {
        GAME_ZONE_FACTORY = Check.ifNull(gameZoneFactory, "gameZoneFactory");
        TILE_HANDLER = Check.ifNull(tileHandler, "tileHandler");
        MAP_HANDLER = Check.ifNull(mapHandler, "mapHandler");
        GET_CONSUMER = Check.ifNull(getConsumer, "getConsumer");
        TILES_PER_BATCH = Check.ifNonNegative(tilesPerBatch, "tilesPerBatch");
        EXECUTOR = Executors.newFixedThreadPool(threadPoolSize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public GameZone read(String writtenData) throws IllegalArgumentException {
        var dto = JSON.fromJson(writtenData, GameZoneDTO.class);

        //noinspection unchecked
        var data = (Map<String, Object>) MAP_HANDLER.read(dto.data);

        var gameZone = GAME_ZONE_FACTORY.make(dto.id, coordinate2dOf(dto.maxX, dto.maxY), data);
        gameZone.setName(dto.name);
        for (var onEntry : dto.onEntry) {
            gameZone.onEntry().add(GET_CONSUMER.apply(onEntry));
        }
        for (var onExit : dto.onExit) {
            gameZone.onExit().add(GET_CONSUMER.apply(onExit));
        }

        runTileTasksBatched(setOf(dto.tiles), this::readFromGameZoneTileDto,
                tileWithLoc -> gameZone.putTile(tileWithLoc.FIRST, tileWithLoc.SECOND),
                gameZone);

        return gameZone;
    }

    private Pair<Tile, Coordinate3d> readFromGameZoneTileDto(String dtoStr) {
        var dto = JSON.fromJson(dtoStr, GameZoneTileDTO.class);
        return pairOf(TILE_HANDLER.read(dto.tile), coordinate3dOf(dto.x, dto.y, dto.z));
    }

    @Override
    public String write(GameZone gameZone) {
        Check.ifNull(gameZone, "gameZone");
        var dto = new GameZoneDTO();
        dto.id = gameZone.id();
        dto.name = gameZone.getName();
        dto.data = MAP_HANDLER.write(gameZone.data());
        dto.onEntry = new String[gameZone.onEntry().size()];
        var index = 0;
        for (var action : gameZone.onEntry()) {
            dto.onEntry[index++] = action.id();
        }
        dto.onExit = new String[gameZone.onExit().size()];
        index = 0;
        for (var action : gameZone.onExit()) {
            dto.onExit[index++] = action.id();
        }
        var maxCoordinates = gameZone.maxCoordinates();
        dto.maxX = maxCoordinates.X;
        dto.maxY = maxCoordinates.Y;

        var writtenTiles = Collections.<String>listOf();

        runTileTasksBatched(gameZone.tiles(), this::writeGameZoneTileDto, writtenTiles::add,
                writtenTiles);

        dto.tiles = writtenTiles.toArray(new String[0]);

        return JSON.toJson(dto);
    }

    private String writeGameZoneTileDto(Tile tile) {
        var gameZoneTileDto = new GameZoneTileDTO();
        gameZoneTileDto.x = tile.location().X;
        gameZoneTileDto.y = tile.location().Y;
        gameZoneTileDto.z = tile.location().Z;
        gameZoneTileDto.tile = TILE_HANDLER.write(tile);
        return JSON.toJson(gameZoneTileDto);
    }

    private <T1, T2> void runTileTasksBatched(Set<T1> inputs, Function<T1, T2> inputFunction,
                                              Consumer<T2> outputConsumer, Object lock) {
        var batch = Collections.<Runnable>listOf();
        var batchTasks = Collections.<CompletableFuture<Void>>listOf();
        for (var input : inputs) {
            batch.add(() -> {
                var taskResult = inputFunction.apply(input);
                synchronized (lock) {
                    outputConsumer.accept(taskResult);
                }
            });
            if (batch.size() >= TILES_PER_BATCH) {
                final var batchToRun = batch;
                batchTasks.add(runTaskAsync(() ->
                        batchToRun.forEach(Runnable::run), this::handleThrowable, EXECUTOR));
                batch = listOf();
            }
        }
        if (!batch.isEmpty()) {
            final var batchToRun = batch;
            batchTasks.add(runTaskAsync(() -> batchToRun.forEach(Runnable::run),
                    this::handleThrowable, EXECUTOR));
        }
        waitUntilTasksCompleted(batchTasks, TASK_HAS_THROWN_EXCEPTION);

        if (innerThrowable != null) {
            if (innerThrowable instanceof RuntimeException) {
                throw (RuntimeException) innerThrowable;
            }
            else {
                throw new RuntimeException(innerThrowable.getMessage());
            }
        }
    }

    private synchronized void handleThrowable(Throwable e) {
        if (innerThrowable == null) {
            innerThrowable = e.getCause();
        }
    }

    // TODO: Handle Segment persistence!
    private static class GameZoneDTO {
        String id;
        String name;
        String data;
        String[] onEntry;
        String[] onExit;
        int maxX;
        int maxY;
        String[] tiles;
    }

    private static class GameZoneTileDTO {
        int x;
        int y;
        int z;
        String tile;
    }
}
