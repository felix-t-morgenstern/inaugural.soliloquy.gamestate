package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.TileFixtureHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeFixtureType;
import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixture;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixtureFactory;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeItemHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeTypeHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeUuidHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeVariableCacheHandler;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureHandlerTests {
    private final Map<String, FixtureType> FIXTURE_TYPES = new HashMap<>();
    private final String FIXTURE_TYPE_ID = "fixtureTypeId";
    private final FixtureType FIXTURE_TYPE = new FakeFixtureType(FIXTURE_TYPE_ID);
    private final TileFixtureFactory TILE_FIXTURE_FACTORY = new FakeTileFixtureFactory();
    private final TypeHandler<UUID> UUID_HANDLER = new FakeUuidHandler();
    private final TypeHandler<VariableCache> DATA_HANDLER =
            new FakeVariableCacheHandler();
    private final FakeTypeHandler<Item> ITEM_HANDLER =
            new FakeItemHandler();
    private final float X_TILE_WIDTH_OFFSET = 0.123f;
    private final float Y_TILE_HEIGHT_OFFSET = 0.456f;
    private final String NAME = "fixtureName";

    private final String WRITTEN_VALUE =
            "{\"uuid\":\"UUID0\",\"fixtureTypeId\":\"fixtureTypeId\",\"tileWidthOffset\":0.123," +
                    "\"tileHeightOffset\":0.456,\"items\":[\"Item0\",\"Item1\",\"Item2\"]," +
                    "\"data\":\"VariableCache0\",\"name\":\"fixtureName\"}";

    private TypeHandler<TileFixture> _tileFixtureHandler;

    @BeforeEach
    void setUp() {
        FIXTURE_TYPES.put(FIXTURE_TYPE_ID, FIXTURE_TYPE);

        _tileFixtureHandler = new TileFixtureHandler(FIXTURE_TYPES::get, TILE_FIXTURE_FACTORY,
                UUID_HANDLER, DATA_HANDLER, ITEM_HANDLER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(null, TILE_FIXTURE_FACTORY,
                        UUID_HANDLER, DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(FIXTURE_TYPES::get, null,
                        UUID_HANDLER, DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(FIXTURE_TYPES::get, TILE_FIXTURE_FACTORY,
                        null, DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(FIXTURE_TYPES::get, TILE_FIXTURE_FACTORY,
                        UUID_HANDLER, null, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(FIXTURE_TYPES::get, TILE_FIXTURE_FACTORY,
                        UUID_HANDLER, DATA_HANDLER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        TileFixture.class.getCanonicalName() + ">",
                _tileFixtureHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_tileFixtureHandler.getArchetype());
        assertEquals(TileFixture.class.getCanonicalName(),
                _tileFixtureHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testWrite() {
        UUID uuid = UUID.randomUUID();
        VariableCache data = new VariableCacheStub();
        Item item1 = new FakeItem();
        Item item2 = new FakeItem();
        Item item3 = new FakeItem();
        TileFixture tileFixture = new FakeTileFixture(uuid, FIXTURE_TYPE, data);
        tileFixture.setXTileWidthOffset(X_TILE_WIDTH_OFFSET);
        tileFixture.setYTileHeightOffset(Y_TILE_HEIGHT_OFFSET);
        tileFixture.items().add(item1);
        tileFixture.items().add(item2);
        tileFixture.items().add(item3);
        tileFixture.setName(NAME);

        String writtenValue = _tileFixtureHandler.write(tileFixture);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureHandler.write(null));
    }

    @Test
    void testRead() {
        TileFixture tileFixture = _tileFixtureHandler.read(WRITTEN_VALUE);

        assertNotNull(tileFixture);
        assertSame(((FakeUuidHandler) UUID_HANDLER).READ_OUTPUTS.get(0),
                tileFixture.uuid());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER).READ_OUTPUTS.get(0),
                tileFixture.data());
        assertEquals(X_TILE_WIDTH_OFFSET, tileFixture.getXTileWidthOffset());
        assertEquals(Y_TILE_HEIGHT_OFFSET, tileFixture.getYTileHeightOffset());
        assertEquals(3, tileFixture.items().representation().size());
        assertTrue(tileFixture.items().contains(ITEM_HANDLER.READ_OUTPUTS.get(0)));
        assertTrue(tileFixture.items().contains(ITEM_HANDLER.READ_OUTPUTS.get(1)));
        assertTrue(tileFixture.items().contains(ITEM_HANDLER.READ_OUTPUTS.get(2)));
        assertEquals(NAME, tileFixture.getName());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureHandler.read(""));
    }
}
