package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentTypeHandler;
import inaugural.soliloquy.gamestate.archetypes.GameZoneArchetype;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.function.Function;

public class PersistentGameZoneHandler extends PersistentTypeHandler<GameZone> {
    private final GameZoneFactory GAME_ZONE_FACTORY;
    private final PersistentValueTypeHandler<Tile> TILE_HANDLER;
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER;
    private final CollectionFactory COLLECTION_FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Action> GET_ACTION;

    private static final GameZone ARCHETYPE = new GameZoneArchetype();

    @SuppressWarnings({"rawtypes", "ConstantConditions"})
    public PersistentGameZoneHandler(GameZoneFactory gameZoneFactory,
                                     PersistentValueTypeHandler<Tile> tileHandler,
                                     PersistentValueTypeHandler<VariableCache> dataHandler,
                                     CollectionFactory collectionFactory,
                                     Function<String, Action> getAction) {
        if (gameZoneFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentGameZoneHandler: gameZoneFactory cannot be null");
        }
        GAME_ZONE_FACTORY = gameZoneFactory;
        if (tileHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameZoneHandler: tileHandler cannot be null");
        }
        TILE_HANDLER = tileHandler;
        if (dataHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentGameZoneHandler: dataHandler cannot be null");
        }
        DATA_HANDLER = dataHandler;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentGameZoneHandler: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (getAction == null) {
            throw new IllegalArgumentException(
                    "PersistentGameZoneHandler: getAction cannot be null");
        }
        GET_ACTION = getAction;
    }

    @Override
    public GameZone getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public GameZone read(String writtenData) throws IllegalArgumentException {
        GameZoneDTO dto = new Gson().fromJson(writtenData, GameZoneDTO.class);

        Tile[][] tiles = new Tile[dto.maxX+1][dto.maxY+1];
        for(int x = 0; x <= dto.maxX; x++) {
            for(int y = 0; y <= dto.maxY; y++) {
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
                    "PersistentGameZoneHandler.write: gameZone cannot be null");
        }
        GameZoneDTO dto = new GameZoneDTO();
        dto.id = gameZone.id();
        dto.type = gameZone.type();
        dto.name = gameZone.getName();
        dto.data = DATA_HANDLER.write(gameZone.data());
        dto.onEntry = new String[gameZone.onEntry().size()];
        int index = 0;
        for(Action action : gameZone.onEntry()) {
            dto.onEntry[index++] = action.id();
        }
        dto.onExit = new String[gameZone.onExit().size()];
        index = 0;
        for(Action action : gameZone.onExit()) {
            dto.onExit[index++] = action.id();
        }
        ReadableCoordinate maxCoordinates = gameZone.maxCoordinates();
        dto.maxX = maxCoordinates.getX();
        dto.maxY = maxCoordinates.getY();
        dto.tiles = new String[maxCoordinates.getX()+1][maxCoordinates.getY()+1];
        for(int x = 0; x <= maxCoordinates.getX(); x++) {
            for(int y = 0; y <= maxCoordinates.getY(); y++) {
                dto.tiles[x][y] = TILE_HANDLER.write(gameZone.tile(x,y));
            }
        }
        return new Gson().toJson(dto);
    }

    private class GameZoneDTO {
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
