package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.function.Function;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class GameZoneHandler extends AbstractTypeHandler<GameZone> {
    private final GameZoneFactory GAME_ZONE_FACTORY;
    private final TypeHandler<Tile> TILE_HANDLER;
    private final TypeHandler<VariableCache> DATA_HANDLER;
    @SuppressWarnings("rawtypes")
    private final Function<String, Action> GET_ACTION;

    @SuppressWarnings("rawtypes")
    public GameZoneHandler(GameZoneFactory gameZoneFactory,
                           TypeHandler<Tile> tileHandler,
                           TypeHandler<VariableCache> dataHandler,
                           Function<String, Action> getAction) {
        super(generateSimpleArchetype(GameZone.class));
        GAME_ZONE_FACTORY = Check.ifNull(gameZoneFactory, "gameZoneFactory");
        TILE_HANDLER = Check.ifNull(tileHandler, "tileHandler");
        DATA_HANDLER = Check.ifNull(dataHandler, "dataHandler");
        GET_ACTION = Check.ifNull(getAction, "getAction");
    }

    @Override
    public GameZone read(String writtenData) throws IllegalArgumentException {
        GameZoneDTO dto = JSON.fromJson(writtenData, GameZoneDTO.class);

        Tile[][] tiles = new Tile[dto.maxX + 1][dto.maxY + 1];
        for (int x = 0; x <= dto.maxX; x++) {
            for (int y = 0; y <= dto.maxY; y++) {
                tiles[x][y] = TILE_HANDLER.read(dto.tiles[x][y]);
            }
        }
        VariableCache data = DATA_HANDLER.read(dto.data);

        GameZone gameZone = GAME_ZONE_FACTORY.make(dto.id, dto.type, tiles, data);
        gameZone.setName(dto.name);
        for (String onEntry : dto.onEntry) {
            gameZone.onEntry().add(GET_ACTION.apply(onEntry));
        }
        for (String onExit : dto.onExit) {
            gameZone.onExit().add(GET_ACTION.apply(onExit));
        }

        return gameZone;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String write(GameZone gameZone) {
        if (gameZone == null) {
            throw new IllegalArgumentException(
                    "GameZoneHandler.write: gameZone cannot be null");
        }
        GameZoneDTO dto = new GameZoneDTO();
        dto.id = gameZone.id();
        dto.type = gameZone.type();
        dto.name = gameZone.getName();
        dto.data = DATA_HANDLER.write(gameZone.data());
        dto.onEntry = new String[gameZone.onEntry().size()];
        int index = 0;
        for (Action action : gameZone.onEntry()) {
            dto.onEntry[index++] = action.id();
        }
        dto.onExit = new String[gameZone.onExit().size()];
        index = 0;
        for (Action action : gameZone.onExit()) {
            dto.onExit[index++] = action.id();
        }
        Coordinate maxCoordinates = gameZone.maxCoordinates();
        dto.maxX = maxCoordinates.x();
        dto.maxY = maxCoordinates.y();
        dto.tiles = new String[maxCoordinates.x() + 1][maxCoordinates.y() + 1];
        for (int x = 0; x <= maxCoordinates.x(); x++) {
            for (int y = 0; y <= maxCoordinates.y(); y++) {
                dto.tiles[x][y] = TILE_HANDLER.write(gameZone.tile(Coordinate.of(x, y)));
            }
        }
        return JSON.toJson(dto);
    }

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
