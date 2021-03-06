package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileWallSegmentFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.factories.TileWallSegmentFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentFactoryImplTests {
    private VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private VariableCache DATA = new VariableCacheStub();

    private TileWallSegmentFactory _tileWallSegmentFactory;

    @BeforeEach
    void setUp() {
        _tileWallSegmentFactory = new TileWallSegmentFactoryImpl(DATA_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegmentFactoryImpl(null));
    }

    @Test
    void testMakeWithoutData() {
        TileWallSegment segment = _tileWallSegmentFactory.make();

        assertNotNull(segment);
        assertSame(((FakeVariableCacheFactory)DATA_FACTORY).Created.get(0), segment.data());
    }

    @Test
    void testMakeWithData() {
        TileWallSegment segment = _tileWallSegmentFactory.make(DATA);

        assertNotNull(segment);
        assertSame(DATA, segment.data());
    }

    @Test
    void testMakeWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> _tileWallSegmentFactory.make(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegmentFactory.class.getCanonicalName(),
                _tileWallSegmentFactory.getInterfaceName());
    }
}
