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
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static org.junit.jupiter.api.Assertions.*;

class TileHandlerTests {
    private final TileFactory TILE_FACTORY = new FakeTileFactory();

    private final FakeTypeHandler<Character> CHAR_HANDLER = new FakeCharacterHandler();
    private final FakeTypeHandler<Item> ITEM_HANDLER = new FakeItemHandler();
    private final FakeTypeHandler<TileFixture> FIXTURE_HANDLER = new FakeTileFixtureHandler();
    private final FakeTypeHandler<Sprite> SPRITE_HANDLER = new FakeSpriteHandler();
    private final TypeHandler<VariableCache> DATA_HANDLER = new FakeVariableCacheHandler();

    private final int X = 123;
    private final int Y = 456;
    private final int HEIGHT = 789;
    private final Character CHARACTER = new FakeCharacter();
    private final Item ITEM = new FakeItem();
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();
    private final Sprite SPRITE = new SpriteStub();
    private final VariableCache DATA = new VariableCacheStub();

    private final String MOVEMENT_EVENT_ID = "movementEventId";
    private final GameMovementEvent MOVEMENT_EVENT =
            new FakeGameMovementEvent(MOVEMENT_EVENT_ID);
    private final Map<String, GameMovementEvent> MOVEMENT_EVENTS = mapOf();

    private final String ABILITY_EVENT_ID = "abilityEventId";
    private final GameAbilityEvent ABILITY_EVENT =
            new FakeGameAbilityEvent(ABILITY_EVENT_ID);
    private final Map<String, GameAbilityEvent> ABILITY_EVENTS = mapOf();

    private final String GROUND_TYPE_ID = "groundTypeId";
    private final GroundType GROUND_TYPE = new FakeGroundType(GROUND_TYPE_ID);
    private final Map<String, GroundType> GROUND_TYPES = mapOf();

    private final String WRITTEN_DATA =
            "{\"x\":123,\"y\":456,\"height\":789,\"groundTypeId\":\"groundTypeId\"," +
                    "\"characters\":[{\"z\":111,\"entity\":\"Character0\"}]," +
                    "\"items\":[{\"z\":222,\"entity\":\"Item0\"}],\"fixtures\":[{\"z\":333," +
                    "\"entity\":\"TileFixture0\"}],\"movementEvents\":[\"movementEventId\"]," +
                    "\"abilityEvents\":[\"abilityEventId\"],\"sprites\":[{\"z\":666," +
                    "\"entity\":\"Sprite0\"}],\"data\":\"VariableCache0\"}";

    private TypeHandler<Tile> tileHandler;

    @BeforeEach
    void setUp() {
        MOVEMENT_EVENTS.put(MOVEMENT_EVENT_ID, MOVEMENT_EVENT);
        ABILITY_EVENTS.put(ABILITY_EVENT_ID, ABILITY_EVENT);
        GROUND_TYPES.put(GROUND_TYPE_ID, GROUND_TYPE);

        tileHandler = new TileHandler(TILE_FACTORY, CHAR_HANDLER, ITEM_HANDLER, FIXTURE_HANDLER,
                SPRITE_HANDLER, DATA_HANDLER, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                GROUND_TYPES::get);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(null, CHAR_HANDLER, ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER,
                        DATA_HANDLER, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, null, ITEM_HANDLER, FIXTURE_HANDLER, SPRITE_HANDLER,
                        DATA_HANDLER, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, CHAR_HANDLER, null, FIXTURE_HANDLER, SPRITE_HANDLER,
                        DATA_HANDLER, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, CHAR_HANDLER, ITEM_HANDLER, null, SPRITE_HANDLER,
                        DATA_HANDLER, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, CHAR_HANDLER, ITEM_HANDLER, FIXTURE_HANDLER, null,
                        DATA_HANDLER, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, CHAR_HANDLER, ITEM_HANDLER, FIXTURE_HANDLER,
                        SPRITE_HANDLER, null, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, CHAR_HANDLER, ITEM_HANDLER, FIXTURE_HANDLER,
                        SPRITE_HANDLER, DATA_HANDLER, null, ABILITY_EVENTS::get,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, CHAR_HANDLER, ITEM_HANDLER, FIXTURE_HANDLER,
                        SPRITE_HANDLER, DATA_HANDLER, MOVEMENT_EVENTS::get, null,
                        GROUND_TYPES::get));
        assertThrows(IllegalArgumentException.class, () ->
                new TileHandler(TILE_FACTORY, CHAR_HANDLER, ITEM_HANDLER, FIXTURE_HANDLER,
                        SPRITE_HANDLER, DATA_HANDLER, MOVEMENT_EVENTS::get, ABILITY_EVENTS::get,
                        null));
    }

    @Test
    void testWrite() {
        var tile = new FakeTile(X, Y, DATA);
        tile.setHeight(HEIGHT);
        tile.setGroundType(GROUND_TYPE);
        tile.characters().add(CHARACTER, 111);
        tile.items().add(ITEM, 222);
        tile.fixtures().add(TILE_FIXTURE, 333);
        tile.movementEvents().add(MOVEMENT_EVENT);
        tile.abilityEvents().add(ABILITY_EVENT);
        tile.sprites().put(SPRITE, 666);

        assertEquals(WRITTEN_DATA, tileHandler.write(tile));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> tileHandler.write(null));
    }

    @Test
    void testRead() {
        var readTile = tileHandler.read(WRITTEN_DATA);

        assertNotNull(readTile);
        assertEquals(X, readTile.location().X);
        assertEquals(Y, readTile.location().Y);
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER).READ_OUTPUTS.get(0),
                readTile.data());
        assertEquals(HEIGHT, readTile.getHeight());
        assertSame(GROUND_TYPE, readTile.getGroundType());

        assertEquals(1, readTile.characters().size());
        Character characterFromHandler = CHAR_HANDLER.READ_OUTPUTS.get(0);
        assertTrue(readTile.characters().contains(characterFromHandler));
        assertEquals("Character0", CHAR_HANDLER.READ_INPUTS.get(0));
        assertEquals(111, readTile.characters().getZIndex(characterFromHandler));

        assertEquals(1, readTile.items().size());
        Item itemFromHandler = ITEM_HANDLER.READ_OUTPUTS.get(0);
        assertTrue(readTile.items().contains(itemFromHandler));
        assertEquals("Item0", ITEM_HANDLER.READ_INPUTS.get(0));
        assertEquals(222, readTile.items().getZIndex(itemFromHandler));

        assertEquals(1, readTile.fixtures().size());
        TileFixture fixtureFromHandler = FIXTURE_HANDLER.READ_OUTPUTS.get(0);
        assertTrue(readTile.fixtures().contains(fixtureFromHandler));
        assertEquals("TileFixture0", FIXTURE_HANDLER.READ_INPUTS.get(0));
        assertEquals(333, readTile.fixtures().getZIndex(fixtureFromHandler));

        assertEquals(1, readTile.movementEvents().size());
        assertTrue(readTile.movementEvents().contains(MOVEMENT_EVENT));

        assertEquals(1, readTile.abilityEvents().size());
        assertTrue(readTile.abilityEvents().contains(ABILITY_EVENT));

        assertEquals(1, readTile.sprites().size());
        Sprite spriteFromHandler = SPRITE_HANDLER.READ_OUTPUTS.get(0);
        assertTrue(readTile.sprites().containsKey(spriteFromHandler));
        assertEquals(666, readTile.sprites().get(spriteFromHandler));
        assertEquals("Sprite0", SPRITE_HANDLER.READ_INPUTS.get(0));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> tileHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> tileHandler.read(""));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Tile.class.getCanonicalName() + ">",
                tileHandler.getInterfaceName());
    }
}
