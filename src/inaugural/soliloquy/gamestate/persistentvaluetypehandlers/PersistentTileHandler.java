package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

import java.util.function.Function;

public class PersistentTileHandler implements PersistentValueTypeHandler<Tile> {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY;
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;

    private final PersistentValueTypeHandler<Character> CHARACTERS_HANDLER;
    private final PersistentValueTypeHandler<Item> ITEMS_HANDLER;
    private final PersistentValueTypeHandler<TileFixture> FIXTURES_HANDLER;
    private final PersistentValueTypeHandler<TileWallSegment> WALL_SEGMENTS_HANDLER;
    private final PersistentValueTypeHandler<Sprite> SPRITE_HANDLER;

    private final Function<String, GameZone> GET_GAME_ZONE;
    private final Function<String, GameMovementEvent> GET_MOVEMENT_EVENT;
    private final Function<String, GameAbilityEvent> GET_ABILITY_EVENT;
    private final Function<String, GroundType> GET_GROUND_TYPE;

    @SuppressWarnings("ConstantConditions")
    public PersistentTileHandler(CoordinateFactory coordinateFactory,
                                 TileEntitiesFactory tileEntitiesFactory,
                                 TileWallSegmentsFactory tileWallSegmentsFactory,
                                 MapFactory mapFactory,
                                 CollectionFactory collectionFactory,
                                 PersistentValueTypeHandler<Character> charactersHandler,
                                 PersistentValueTypeHandler<Item> itemsHandler,
                                 PersistentValueTypeHandler<TileFixture> fixturesHandler,
                                 PersistentValueTypeHandler<TileWallSegment>
                                         wallSegmentsHandler,
                                 PersistentValueTypeHandler<Sprite> spriteHandler,
                                 Function<String, GameZone> getGameZone,
                                 Function<String, GameMovementEvent> getMovementEvent,
                                 Function<String, GameAbilityEvent> getAbilityEvent,
                                 Function<String, GroundType> getGroundType) {
        if (coordinateFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: coordinateFactory cannot be null");
        }
        COORDINATE_FACTORY = coordinateFactory;
        if (tileEntitiesFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: tileEntitiesFactory cannot be null");
        }
        TILE_ENTITIES_FACTORY = tileEntitiesFactory;
        if (tileWallSegmentsFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: tileWallSegmentsFactory cannot be null");
        }
        TILE_WALL_SEGMENTS_FACTORY = tileWallSegmentsFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (charactersHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: charactersHandler cannot be null");
        }
        CHARACTERS_HANDLER = charactersHandler;
        if (itemsHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: itemsHandler cannot be null");
        }
        ITEMS_HANDLER = itemsHandler;
        if (fixturesHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: fixturesHandler cannot be null");
        }
        FIXTURES_HANDLER = fixturesHandler;
        if (wallSegmentsHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: wallSegmentsHandler cannot be null");
        }
        WALL_SEGMENTS_HANDLER = wallSegmentsHandler;
        if (spriteHandler == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: spriteHandler cannot be null");
        }
        SPRITE_HANDLER = spriteHandler;
        if (getGameZone == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: getGameZone cannot be null");
        }
        GET_GAME_ZONE = getGameZone;
        if (getMovementEvent == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: getMovementEvent cannot be null");
        }
        GET_MOVEMENT_EVENT = getMovementEvent;
        if (getAbilityEvent == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: getAbilityEvent cannot be null");
        }
        GET_ABILITY_EVENT = getAbilityEvent;
        if (getGroundType == null) {
            throw new IllegalArgumentException(
                    "PersistentTileHandler: getGroundType cannot be null");
        }
        GET_GROUND_TYPE = getGroundType;
    }

    @Override
    public Tile read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Tile tile) {
        TileDTO dto = new TileDTO();
        dto.gameZoneId = tile.gameZone().id();
        dto.x = tile.location().getX();
        dto.y = tile.location().getY();
        dto.height = tile.getHeight();
        dto.groundTypeId = tile.getGroundType().id();

        dto.characters = new String[tile.characters().size()];

        return null;
    }

    @Override
    public Tile getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    private class TileDTO {
        String gameZoneId;
        int x;
        int y;
        int height;
        String groundTypeId;
        String[] characters;
        String[] items;
        String[] fixtures;
        String[] wallSegments;
        String[] movementEvents;
        String[] abilityEvents;
        String[] sprites;
        String data;
    }
}
