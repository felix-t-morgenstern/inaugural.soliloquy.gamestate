package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileWallSegmentsFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentsFactoryImplTests {
    private final Tile TILE = new FakeTile();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();

    private TileWallSegmentsFactory _tileWallSegmentsFactory;

    @BeforeEach
    void setUp() {
        _tileWallSegmentsFactory = new TileWallSegmentsFactoryImpl(MAP_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegmentsFactoryImpl(null));
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
