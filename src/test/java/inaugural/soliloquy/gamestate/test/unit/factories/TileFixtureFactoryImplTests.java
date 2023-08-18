package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileFixtureFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeFixtureType;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixtureItems;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixtureItemsFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

import java.util.UUID;

import static org.junit.Assert.*;

public class TileFixtureFactoryImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY =
            new FakeTileFixtureItemsFactory();
    private final VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final FakeFixtureType FIXTURE_TYPE = new FakeFixtureType();
    private final VariableCache DATA = new VariableCacheStub();

    private TileFixtureFactory tileFixtureFactory;

    @Before
    public void setUp() {
        tileFixtureFactory = new TileFixtureFactoryImpl(TILE_FIXTURE_ITEMS_FACTORY, DATA_FACTORY);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(null, DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureFactoryImpl(TILE_FIXTURE_ITEMS_FACTORY, null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TileFixtureFactory.class.getCanonicalName(),
                tileFixtureFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        TileFixture tileFixture = tileFixtureFactory.make(FIXTURE_TYPE, null);

        assertNotNull(tileFixture);
        assertNotNull(tileFixture.uuid());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(((FakeVariableCacheFactory) DATA_FACTORY).Created.get(0), tileFixture.data());
        assertEquals(Vertex.of(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET), tileFixture.getTileOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((FakeTileFixtureItems) tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    public void testMakeWithData() {
        TileFixture tileFixture = tileFixtureFactory.make(FIXTURE_TYPE, DATA);

        assertNotNull(tileFixture);
        assertNotNull(tileFixture.uuid());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(DATA, tileFixture.data());
        assertEquals(Vertex.of(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET), tileFixture.getTileOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((FakeTileFixtureItems) tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    public void testMakeWithId() {
        TileFixture tileFixture = tileFixtureFactory.make(FIXTURE_TYPE, null, UUID);

        assertNotNull(tileFixture);
        assertSame(UUID, tileFixture.uuid());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(((FakeVariableCacheFactory) DATA_FACTORY).Created.get(0), tileFixture.data());
        assertEquals(Vertex.of(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET), tileFixture.getTileOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((FakeTileFixtureItems) tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    public void testMakeWithIdAndData() {
        TileFixture tileFixture = tileFixtureFactory.make(FIXTURE_TYPE, DATA, UUID);

        assertNotNull(tileFixture);
        assertSame(UUID, tileFixture.uuid());
        assertSame(FIXTURE_TYPE, tileFixture.type());
        assertSame(DATA, tileFixture.data());
        assertEquals(Vertex.of(FakeFixtureType.DEFAULT_X_TILE_WIDTH_OFFSET,
                FakeFixtureType.DEFAULT_Y_TILE_HEIGHT_OFFSET), tileFixture.getTileOffset());
        assertNotNull(tileFixture.movementEvents());
        assertNotNull(tileFixture.abilityEvents());
        assertSame(tileFixture, ((FakeTileFixtureItems) tileFixture.items()).TILE_FIXTURE);
    }

    @Test
    public void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> tileFixtureFactory.make(null, null));

        assertThrows(IllegalArgumentException.class,
                () -> tileFixtureFactory.make(null, null, UUID));
        assertThrows(IllegalArgumentException.class,
                () -> tileFixtureFactory.make(FIXTURE_TYPE, null, null));
    }
}
