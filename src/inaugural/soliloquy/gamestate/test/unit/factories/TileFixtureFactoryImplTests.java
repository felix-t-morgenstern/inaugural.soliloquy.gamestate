package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileFixtureFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.EntityUuidFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFixtureFactoryImplTests {
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY =
            new FakeTileFixtureItemsFactory();
    private final VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final FakeFixtureType FIXTURE_TYPE = new FakeFixtureType();
    private final VariableCache DATA = new VariableCacheStub();
    private final EntityUuid ID = new FakeEntityUuid();

    private TileFixtureFactory _tileFixtureFactory;

    @BeforeEach
    void setUp() {
        _tileFixtureFactory = new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, LIST_FACTORY,
                TILE_FIXTURE_ITEMS_FACTORY, DATA_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(null, LIST_FACTORY,
                        TILE_FIXTURE_ITEMS_FACTORY, DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, null,
                        TILE_FIXTURE_ITEMS_FACTORY, DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, LIST_FACTORY,
                        null, DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(ENTITY_UUID_FACTORY, LIST_FACTORY,
                        TILE_FIXTURE_ITEMS_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFixtureFactory.class.getCanonicalName(),
                _tileFixtureFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        TileFixture tileFixture = _tileFixtureFactory.make(FIXTURE_TYPE, null);

        assertNotNull(tileFixture);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(((FakeVariableCacheFactory)DATA_FACTORY).Created.get(0), tileFixture.data());
        assertEquals(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                tileFixture.getXTileWidthOffset());
        assertEquals(FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET,
                tileFixture.getYTileHeightOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((FakeTileFixtureItems)tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testMakeWithData() {
        TileFixture tileFixture = _tileFixtureFactory.make(FIXTURE_TYPE, DATA);

        assertNotNull(tileFixture);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(DATA, tileFixture.data());
        assertEquals(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                tileFixture.getXTileWidthOffset());
        assertEquals(FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET,
                tileFixture.getYTileHeightOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((FakeTileFixtureItems)tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testMakeWithId() {
        TileFixture tileFixture = _tileFixtureFactory.make(FIXTURE_TYPE, null, ID);

        assertNotNull(tileFixture);
        assertSame(ID, tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(((FakeVariableCacheFactory)DATA_FACTORY).Created.get(0), tileFixture.data());
        assertEquals(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                tileFixture.getXTileWidthOffset());
        assertEquals(FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET,
                tileFixture.getYTileHeightOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((FakeTileFixtureItems)tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testMakeWithIdAndData() {
        TileFixture tileFixture = _tileFixtureFactory.make(FIXTURE_TYPE, DATA, ID);

        assertNotNull(tileFixture);
        assertSame(ID, tileFixture.id());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(DATA, tileFixture.data());
        assertEquals(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                tileFixture.getXTileWidthOffset());
        assertEquals(FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET,
                tileFixture.getYTileHeightOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((FakeTileFixtureItems)tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureFactory.make(null, null));

        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureFactory.make(null, null, ID));
        assertThrows(IllegalArgumentException.class,
                () -> _tileFixtureFactory.make(FIXTURE_TYPE, null, null));
    }
}
