package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileEntitiesFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.PairFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileEntitiesFactoryImplTests {
    private final Tile TILE = new TileStub();
    private final Item ARCHETYPE = new ItemStub();
    private final PairFactory PAIR_FACTORY = new PairFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private TileEntitiesFactory _tileEntitiesFactory;

    @BeforeEach
    void setUp() {
        _tileEntitiesFactory = new TileEntitiesFactoryImpl(PAIR_FACTORY, MAP_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileEntitiesFactoryImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileEntitiesFactoryImpl(PAIR_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileEntitiesFactory.class.getCanonicalName(),
                _tileEntitiesFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        TileEntities<Item> tileEntities = _tileEntitiesFactory.make(TILE, ARCHETYPE);

        assertNotNull(tileEntities);
        assertSame(ARCHETYPE, tileEntities.getArchetype());
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileEntitiesFactory.make(null, ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> _tileEntitiesFactory.make(TILE, null));
    }
}
