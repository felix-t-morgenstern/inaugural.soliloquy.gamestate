package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.TileHandler;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.fakes.persistence.*;
import inaugural.soliloquy.gamestate.test.stubs.SpriteStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentFactory;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TileHandlerTests {
    private final TileFactory TILE_FACTORY = new FakeTileFactory();
    private final TileWallSegmentFactory TILE_WALL_SEGMENT_FACTORY =
            new FakeTileWallSegmentFactory();

    private final FakeTypeHandler<Character> CHAR_HANDLER =
            new FakeCharacterHandler();
    private final FakeTypeHandler<Item> ITEM_HANDLER =
            new FakeItemHandler();
    private final FakeTypeHandler<TileFixture> FIXTURE_HANDLER =
            new FakeTileFixtureHandler();
    private final FakeTypeHandler<Sprite> SPRITE_HANDLER =
            new FakeSpriteHandler();
    private final TypeHandler<VariableCache> DATA_HANDLER =
            new FakeVariableCacheHandler();

    private final int X = 123;
    private final int Y = 456;
    private final int HEIGHT = 789;
    private final Character CHARACTER = new FakeCharacter();
    private final Item ITEM = new FakeItem();
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();
    private final TileWallSegment TILE_WALL_SEGMENT = new FakeTileWallSegment();
    private final Sprite SPRITE = new SpriteStub();
    private final VariableCache DATA = new VariableCacheStub();

    private final String SEGMENT_TYPE_ID = "segmentTypeId";
    private final WallSegmentType SEGMENT_TYPE = new FakeWallSegmentType(SEGMENT_TYPE_ID);
    private final HashMap<String, WallSegmentType> SEGMENT_TYPES = new HashMap<>();

    private final String MOVEMENT_EVENT_ID = "movementEventId";
    private final GameMovementEvent MOVEMENT_EVENT =
            new FakeGameMovementEvent(MOVEMENT_EVENT_ID);
    private final HashMap<String,GameMovementEvent> MOVEMENT_EVENTS = new HashMap<>();

    private final String ABILITY_EVENT_ID = "abilityEventId";
    private final GameAbilityEvent ABILITY_EVENT =
            new FakeGameAbilityEvent(ABILITY_EVENT_ID);
    private final HashMap<String, GameAbilityEvent> ABILITY_EVENTS = new HashMap<>();

    private final String GROUND_TYPE_ID = "groundTypeId";
    private final GroundType GROUND_TYPE = new FakeGroundType(GROUND_TYPE_ID);
    private final HashMap<String, GroundType> GROUND_TYPES = new HashMap<>();

    private final String WRITTEN_DATA = "{\"x\":123,\"y\":456,\"height\":789,\"groundTypeId\":\"groundTypeId\",\"characters\":[{\"z\":111,\"entity\":\"Character0\"}],\"items\":[{\"z\":222,\"entity\":\"Item0\"}],\"fixtures\":[{\"z\":333,\"entity\":\"TileFixture0\"}],\"wallSegments\":[{\"type\":\"segmentTypeId\",\"direction\":1,\"height\":444,\"z\":555,\"data\":\"VariableCache0\"}],\"movementEvents\":[\"movementEventId\"],\"abilityEvents\":[\"abilityEventId\"],\"sprites\":[{\"z\":666,\"entity\":\"Sprite0\"}],\"data\":\"VariableCache1\"}";

    private TypeHandler<Tile> _tileHandler;

    @BeforeEach
    void setUp() {
        SEGMENT_TYPES.put(SEGMENT_TYPE_ID, SEGMENT_TYPE);
        MOVEMENT_EVENTS.put(MOVEMENT_EVENT_ID, MOVEMENT_EVENT);
        ABILITY_EVENTS.put(ABILITY_EVENT_ID, ABILITY_EVENT);
        GROUND_TYPES.put(GROUND_TYPE_ID, GROUND_TYPE);

        TILE_WALL_SEGMENT.setType(SEGMENT_TYPE);

        _tileHandler = new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY,
                CHAR_HANDLER, ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get, GROUND_TYPES::get);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(null, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get, GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, null, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, null,
                        ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        null, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, null, SPRITE_HANDLER, DATA_HANDLER,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, null, DATA_HANDLER,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, null,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                        null, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get, GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                        SEGMENT_TYPES::get, null, ABILITY_EVENTS::get, GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, null, GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, TILE_WALL_SEGMENT_FACTORY, CHAR_HANDLER,
                        ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER, DATA_HANDLER,
                        SEGMENT_TYPES::get, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get, null));
    }

    @Test
    void testWrite() {
        Tile tile = new FakeTile(X, Y, DATA);
        tile.setHeight(HEIGHT);
        tile.setGroundType(GROUND_TYPE);
        tile.characters().add(CHARACTER, 111);
        tile.items().add(ITEM, 222);
        tile.fixtures().add(TILE_FIXTURE, 333);
        tile.wallSegments().add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 444, 555);
        tile.movementEvents().add(MOVEMENT_EVENT);
        tile.abilityEvents().add(ABILITY_EVENT);
        tile.sprites().put(SPRITE, 666);

        assertEquals(WRITTEN_DATA, _tileHandler.write(tile));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileHandler.write(null));
    }

    @Test
    void testRead() {
        Tile readTile = _tileHandler.read(WRITTEN_DATA);

        assertNotNull(readTile);
        assertEquals(X, readTile.location().getX());
        assertEquals(Y, readTile.location().getY());
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER).READ_OUTPUTS.get(0),
                readTile.data());
        assertEquals(HEIGHT, readTile.getHeight());
        assertSame(GROUND_TYPE, readTile.getGroundType());

        assertEquals(1, readTile.characters().size());
        Character characterFromHandler = ((FakeCharacterHandler) CHAR_HANDLER)
                .READ_OUTPUTS.get(0);
        assertTrue(readTile.characters().contains(characterFromHandler));
        assertEquals("Character0", ((FakeCharacterHandler) CHAR_HANDLER)
                .READ_INPUTS.get(0));
        assertEquals(111, readTile.characters().getZIndex(characterFromHandler));

        assertEquals(1, readTile.items().size());
        Item itemFromHandler = ((FakeItemHandler) ITEM_HANDLER)
                .READ_OUTPUTS.get(0);
        assertTrue(readTile.items().contains(itemFromHandler));
        assertEquals("Item0", ((FakeItemHandler) ITEM_HANDLER)
                .READ_INPUTS.get(0));
        assertEquals(222, readTile.items().getZIndex(itemFromHandler));

        assertEquals(1, readTile.fixtures().size());
        TileFixture fixtureFromHandler = ((FakeTileFixtureHandler) FIXTURE_HANDLER)
                .READ_OUTPUTS.get(0);
        assertTrue(readTile.fixtures().contains(fixtureFromHandler));
        assertEquals("TileFixture0", ((FakeTileFixtureHandler) FIXTURE_HANDLER)
                .READ_INPUTS.get(0));
        assertEquals(333, readTile.fixtures().getZIndex(fixtureFromHandler));

        assertEquals(1, readTile.wallSegments().size());
        TileWallSegment segmentFromFactory =
                ((FakeTileWallSegmentFactory) TILE_WALL_SEGMENT_FACTORY).FROM_FACTORY.get(0);
        assertTrue(readTile.wallSegments().contains(segmentFromFactory));
        assertSame(SEGMENT_TYPE, segmentFromFactory.getType());
        assertSame(TileWallSegmentDirection.NORTH,
                readTile.wallSegments().getDirection(segmentFromFactory));
        assertEquals(444, readTile.wallSegments().getHeight(segmentFromFactory));
        assertEquals(555, readTile.wallSegments().getZIndex(segmentFromFactory));
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER).READ_OUTPUTS.get(1),
                segmentFromFactory.data());

        assertEquals(1, readTile.movementEvents().size());
        assertTrue(readTile.movementEvents().contains(MOVEMENT_EVENT));

        assertEquals(1, readTile.abilityEvents().size());
        assertTrue(readTile.abilityEvents().contains(ABILITY_EVENT));

        assertEquals(1, readTile.sprites().size());
        Sprite spriteFromHandler = ((FakeSpriteHandler) SPRITE_HANDLER)
                .READ_OUTPUTS.get(0);
        assertTrue(readTile.sprites().containsKey(spriteFromHandler));
        assertEquals(666, readTile.sprites().get(spriteFromHandler));
        assertEquals("Sprite0", ((FakeSpriteHandler) SPRITE_HANDLER)
                .READ_INPUTS.get(0));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _tileHandler.read(""));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                Tile.class.getCanonicalName() + ">",
                _tileHandler.getInterfaceName());
    }
}
