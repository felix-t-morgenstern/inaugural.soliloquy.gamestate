package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileWallSegmentsFactory;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileWallSegmentsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentsFactoryTests {
    private final ITile TILE = new TileStub();
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private ITileWallSegmentsFactory _tileWallSegmentsFactory;

    @BeforeEach
    void setUp() {
        _tileWallSegmentsFactory = new TileWallSegmentsFactory(COLLECTION_FACTORY);
    }

    @Test
    void testConstructorWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegmentsFactory(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileWallSegmentsFactory.class.getCanonicalName(),
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
