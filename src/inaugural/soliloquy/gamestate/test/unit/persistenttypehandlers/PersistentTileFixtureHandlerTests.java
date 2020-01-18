package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentTileFixtureHandler;
import inaugural.soliloquy.gamestate.test.stubs.*;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentEntityUuidHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentItemHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentValueTypeHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentVariableCacheHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersistentTileFixtureHandlerTests {
    private final Map<String, FixtureType> FIXTURE_TYPES = new HashMap<>();
    private final String FIXTURE_TYPE_ID = "fixtureTypeId";
    private final FixtureType FIXTURE_TYPE = new FixtureTypeStub(FIXTURE_TYPE_ID);
    private final TileFixtureFactory TILE_FIXTURE_FACTORY = new TileFixtureFactoryStub();
    private final PersistentValueTypeHandler<EntityUuid> ID_HANDLER =
            new PersistentEntityUuidHandlerStub();
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER =
            new PersistentVariableCacheHandlerStub();
    private final PersistentValueTypeHandlerStub<Item> ITEM_HANDLER =
            new PersistentItemHandlerStub();
    private final int PIXEL_OFFSET_X = 123;
    private final int PIXEL_OFFSET_Y = 456;
    private final String NAME = "fixtureName";

    private final String WRITTEN_VALUE = "{\"id\":\"EntityUuid0\",\"fixtureTypeId\":\"fixtureTypeId\",\"pixelOffsetX\":123,\"pixelOffsetY\":456,\"items\":[\"Item0\",\"Item1\",\"Item2\"],\"data\":\"VariableCache0\",\"name\":\"fixtureName\"}";

    private PersistentValueTypeHandler<TileFixture> _persistentTileFixtureHandler;

    @BeforeEach
    void setUp() {
        FIXTURE_TYPES.put(FIXTURE_TYPE_ID, FIXTURE_TYPE);

        _persistentTileFixtureHandler = new PersistentTileFixtureHandler(FIXTURE_TYPES::get,
                TILE_FIXTURE_FACTORY, ID_HANDLER, DATA_HANDLER, ITEM_HANDLER);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentTileFixtureHandler(null, TILE_FIXTURE_FACTORY,
                        ID_HANDLER, DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentTileFixtureHandler(FIXTURE_TYPES::get, null,
                        ID_HANDLER, DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentTileFixtureHandler(FIXTURE_TYPES::get, TILE_FIXTURE_FACTORY,
                        null, DATA_HANDLER, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentTileFixtureHandler(FIXTURE_TYPES::get, TILE_FIXTURE_FACTORY,
                        ID_HANDLER, null, ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentTileFixtureHandler(FIXTURE_TYPES::get, TILE_FIXTURE_FACTORY,
                        ID_HANDLER, DATA_HANDLER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        TileFixture.class.getCanonicalName() + ">",
                _persistentTileFixtureHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_persistentTileFixtureHandler.getArchetype());
        assertEquals(TileFixture.class.getCanonicalName(),
                _persistentTileFixtureHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testWrite() {
        EntityUuid id = new EntityUuidStub();
        VariableCache data = new VariableCacheStub();
        Item item1 = new ItemStub();
        Item item2 = new ItemStub();
        Item item3 = new ItemStub();
        TileFixture tileFixture = new TileFixtureStub(id, FIXTURE_TYPE, data);
        tileFixture.pixelOffset().setX(PIXEL_OFFSET_X);
        tileFixture.pixelOffset().setY(PIXEL_OFFSET_Y);
        tileFixture.items().add(item1);
        tileFixture.items().add(item2);
        tileFixture.items().add(item3);
        tileFixture.setName(NAME);

        String writtenValue = _persistentTileFixtureHandler.write(tileFixture);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentTileFixtureHandler.write(null));
    }

    @Test
    void testRead() {
        TileFixture tileFixture = _persistentTileFixtureHandler.read(WRITTEN_VALUE);

        assertNotNull(tileFixture);
        assertSame(((PersistentEntityUuidHandlerStub)ID_HANDLER).READ_OUTPUTS.get(0),
                tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(((PersistentVariableCacheHandlerStub)DATA_HANDLER).READ_OUTPUTS.get(0),
                tileFixture.data());
        assertEquals(PIXEL_OFFSET_X, tileFixture.pixelOffset().getX());
        assertEquals(PIXEL_OFFSET_Y, tileFixture.pixelOffset().getY());
        assertEquals(3, tileFixture.items().representation().size());
        assertTrue(tileFixture.items().contains(((PersistentItemHandlerStub)ITEM_HANDLER)
                .READ_OUTPUTS.get(0)));
        assertTrue(tileFixture.items().contains(((PersistentItemHandlerStub)ITEM_HANDLER)
                .READ_OUTPUTS.get(1)));
        assertTrue(tileFixture.items().contains(((PersistentItemHandlerStub)ITEM_HANDLER)
                .READ_OUTPUTS.get(2)));
        assertEquals(NAME, tileFixture.getName());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentTileFixtureHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentTileFixtureHandler.read(""));
    }
}
