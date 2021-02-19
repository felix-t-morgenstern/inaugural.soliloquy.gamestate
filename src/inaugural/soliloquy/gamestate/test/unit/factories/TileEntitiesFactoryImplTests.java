package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileEntitiesFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakePairFactory;
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
    private final Tile TILE = new FakeTile();
    private final Item ARCHETYPE = new FakeItem();
    private final PairFactory PAIR_FACTORY = new FakePairFactory();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();

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
