package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.concurrency.Concurrency.runTaskAsync;
import static inaugural.soliloquy.tools.concurrency.Concurrency.waitUntilTasksCompleted;
import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class GameZoneHandler extends AbstractTypeHandler<GameZone> {
    private final GameZoneFactory GAME_ZONE_FACTORY;
    private final TypeHandler<Tile> TILE_HANDLER;
    private final TypeHandler<VariableCache> DATA_HANDLER;
    @SuppressWarnings("rawtypes")
    private final Function<String, Action> GET_ACTION;
    private final int TILES_PER_BATCH;
    private final ExecutorService EXECUTOR;

    private Throwable innerThrowable;
    private final Supplier<Boolean> TASK_HAS_THROWN_EXCEPTION = () -> innerThrowable != null;

    @SuppressWarnings("rawtypes")
    public GameZoneHandler(GameZoneFactory gameZoneFactory,
                           TypeHandler<Tile> tileHandler,
                           TypeHandler<VariableCache> dataHandler,
                           Function<String, Action> getAction,
                           int tilesPerBatch,
                           int threadPoolSize) {
        super(generateSimpleArchetype(GameZone.class));
        GAME_ZONE_FACTORY = Check.ifNull(gameZoneFactory, "gameZoneFactory");
        TILE_HANDLER = Check.ifNull(tileHandler, "tileHandler");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        GET_ACTION = Check.ifNull(getAction, "getAction");
        TILES_PER_BATCH = Check.ifNonNegative(tilesPerBatch, "tilesPerBatch");
        EXECUTOR = Executors.newFixedThreadPool(threadPoolSize);
    }

    @Override
    public GameZone read(String writtenData) throws IllegalArgumentException {
        var dto = JSON.fromJson(writtenData, GameZoneDTO.class);

        var tiles = new Tile[dto.maxX + 1][dto.maxY + 1];

        runTileTasksBatched(dto.maxX, dto.maxY, (x, y) -> TILE_HANDLER.read(dto.tiles[x][y]),
                tiles);

        var data = DATA_HANDLER.read(dto.data);

        var gameZone = GAME_ZONE_FACTORY.make(dto.id, dto.type, tiles, data);
        gameZone.setName(dto.name);
        for (var onEntry : dto.onEntry) {
            gameZone.onEntry().add(GET_ACTION.apply(onEntry));
        }
        for (var onExit : dto.onExit) {
            gameZone.onExit().add(GET_ACTION.apply(onExit));
        }

        return gameZone;
    }

    @Override
    public String write(GameZone gameZone) {
        Check.ifNull(gameZone, "gameZone");
        var dto = new GameZoneDTO();
        dto.id = gameZone.id();
        dto.type = gameZone.type();
        dto.name = gameZone.getName();
        dto.data = DATA_HANDLER.write(gameZone.data());
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

        dto.tiles = new String[maxCoordinates.X + 1][maxCoordinates.Y + 1];

        runTileTasksBatched(dto.maxX, dto.maxY,
                (x, y) -> TILE_HANDLER.write(gameZone.tile(Coordinate2d.of(x, y))), dto.tiles);

        return JSON.toJson(dto);
    }

    private <T> void runTileTasksBatched(int maxX, int maxY, BiFunction<Integer, Integer, T> task,
                                         T[][] resultsArray) {
        List<Runnable> batch = listOf();
        List<CompletableFuture<Void>> batchTasks = listOf();
        for (var x = 0; x <= maxX; x++) {
            for (var y = 0; y <= maxY; y++) {
                // NB: These variables are necessary, since lambdas can only reference values which
                //     are final.
                var finalX = x;
                var finalY = y;
                batch.add(() -> {
                    var taskResult = task.apply(finalX, finalY);
                    synchronized (resultsArray) {
                        resultsArray[finalX][finalY] = taskResult;
                    }
                });
                if (batch.size() >= TILES_PER_BATCH) {
                    final var batchToRun = batch;
                    batchTasks.add(runTaskAsync(() -> batchToRun.forEach(Runnable::run),
                            this::handleThrowable, EXECUTOR));
                    batch = listOf();
                }
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
        String type;
        String name;
        String data;
        String[] onEntry;
        String[] onExit;
        int maxX;
        int maxY;
        String[][] tiles;
    }
}
