package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileWallSegmentsFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.PairFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentsFactoryImplTests {
    private final Tile TILE = new TileStub();
    private final PairFactory PAIR_FACTORY = new PairFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private TileWallSegmentsFactory _tileWallSegmentsFactory;

    @BeforeEach
    void setUp() {
        _tileWallSegmentsFactory = new TileWallSegmentsFactoryImpl(PAIR_FACTORY,
                MAP_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsFactoryImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsFactoryImpl(PAIR_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegmentsFactory.class.getCanonicalName(),
                _tileWallSegmentsFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_tileWallSegmentsFactory.make(TILE));
    }

    @Test
    void testMakeWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> _tileWallSegmentsFactory.make(null));
    }
}
