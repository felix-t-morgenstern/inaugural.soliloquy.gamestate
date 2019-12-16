package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentTileHandler;
import inaugural.soliloquy.gamestate.test.stubs.*;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersistentTileHandlerTests {
    private final CoordinateFactory COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY = new TileEntitiesFactoryStub();
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY =
            new TileWallSegmentsFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private final PersistentValueTypeHandlerStub<Character> CHAR_HANDLER =
            new PersistentCharacterHandlerStub();
    private final PersistentValueTypeHandlerStub<Item> ITEM_HANDLER =
            new PersistentItemHandlerStub();
    private final PersistentValueTypeHandlerStub<TileFixture> FIXTURE_HANDLER =
            new PersistentTileFixtureHandlerStub();
    private final PersistentValueTypeHandlerStub<TileWallSegment> WALL_SEGMENT_HANDLER =
            new PersistentTileWallSegmentHandlerStub();
    private final PersistentValueTypeHandlerStub<Sprite> SPRITE_HANDLER =
            new PersistentSpriteHandlerStub();

    private final GameZone GAME_ZONE = new GameZoneStub();
    private final HashMap<String, GameZone> GAME_ZONES = new HashMap<>();

    private final int X = 123;
    private final int Y = 456;
    private final int HEIGHT = 789;
    private final ReadableCoordinate LOCATION = new ReadableCoordinateStub(X, Y);
    private final Character CHARACTER = new CharacterStub();
    private final Item ITEM = new ItemStub();
    private final TileFixture TILE_FIXTURE = new TileFixtureStub();
    private final TileWallSegment TILE_WALL_SEGMENT = new TileWallSegmentStub();
    private final Sprite SPRITE = new SpriteStub();
    private final GenericParamsSet DATA = new GenericParamsSetStub();

    private final String MOVEMENT_EVENT_ID = "movementEventId";
    private final GameMovementEvent MOVEMENT_EVENT =
            new GameMovementEventStub(MOVEMENT_EVENT_ID);
    private final HashMap<String,GameMovementEvent> MOVEMENT_EVENTS = new HashMap<>();

    private final String ABILITY_EVENT_ID = "movementEventId";
    private final GameAbilityEvent ABILITY_EVENT =
            new GameAbilityEventStub(ABILITY_EVENT_ID);
    private final HashMap<String, GameAbilityEvent> ABILITY_EVENTS = new HashMap<>();

    private final String GROUND_TYPE_ID = "groundTypeId";
    private final GroundType GROUND_TYPE = new GroundTypeStub(GROUND_TYPE_ID);
    private final HashMap<String, GroundType> GROUND_TYPES = new HashMap<>();

    private final String WRITTEN_DATA = "";

    private PersistentValueTypeHandler<Tile> _tileHandler;

    @BeforeEach
    void setUp() {
        GAME_ZONES.put(GAME_ZONE.id(), GAME_ZONE);
        MOVEMENT_EVENTS.put(MOVEMENT_EVENT_ID, MOVEMENT_EVENT);
        ABILITY_EVENTS.put(ABILITY_EVENT_ID, ABILITY_EVENT);
        GROUND_TYPES.put(GROUND_TYPE_ID, GROUND_TYPE);

        _tileHandler = new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get, GROUND_TYPES::get);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(null, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, null,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        null, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, null, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, null, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, null,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        null, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, null, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, null, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, null,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        null, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, null, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, null,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new PersistentTileHandler(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, WALL_SEGMENT_HANDLER, SPRITE_HANDLER,
                        GAME_ZONES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        null));
    }

    @Test
    void testWrite() {
        Tile tile = new TileStub(GAME_ZONE, LOCATION, DATA);
        tile.setHeight(HEIGHT);
        tile.setGroundType(GROUND_TYPE);
        tile.characters().add(CHARACTER);
        tile.items().add(ITEM);
        tile.fixtures().add(TILE_FIXTURE);
        tile.wallSegments().add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT);
        tile.movementEvents().add(MOVEMENT_EVENT);
        tile.abilityEvents().add(ABILITY_EVENT);
        tile.sprites().add(SPRITE);

        assertEquals(WRITTEN_DATA, _tileHandler.write(tile));
    }
}
