package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileWallSegment;
import inaugural.soliloquy.gamestate.test.stubs.GenericParamsSetFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.WallSegmentTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.IGenericParamsSetFactory;
import soliloquy.gamestate.specs.ITileWallSegment;
import soliloquy.ruleset.gameentities.specs.IWallSegmentType;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentTests {
    private final IGenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();
    private final IWallSegmentType WALL_SEGMENT_TYPE = new WallSegmentTypeStub();

    private ITileWallSegment _tileWallSegment;

    @BeforeEach
    void setUp() {
        _tileWallSegment = new TileWallSegment(GENERIC_PARAMS_SET_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegment(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileWallSegment.class.getCanonicalName(),
                _tileWallSegment.getInterfaceName());
    }

    @Test
    void testData() {
        assertSame(GenericParamsSetFactoryStub.GENERIC_PARAMS_SET, _tileWallSegment.data());
    }

    @Test
    void testSetAndGetWallSegmentType() {
        _tileWallSegment.setWallSegmentType(WALL_SEGMENT_TYPE);

        assertSame(WALL_SEGMENT_TYPE, _tileWallSegment.getWallSegmentType());
    }

    @Test
    void testSetAndGetHeight() {
        final int height = 123123;

        _tileWallSegment.setHeight(height);

        assertEquals(height, _tileWallSegment.getHeight());
    }

    @Test
    void testSetAndGetZIndex() {
        final int zIndex = 123123123;

        _tileWallSegment.setZIndex(zIndex);

        assertEquals(zIndex, _tileWallSegment.getZIndex());
    }

    @Test
    void testSetAndGetName() {
        final String name = "name";

        _tileWallSegment.setName(name);

        assertEquals(name, _tileWallSegment.getName());
    }

    @Test
    void testDelete() {
        assertFalse(_tileWallSegment.isDeleted());

        _tileWallSegment.delete();

        assertTrue(_tileWallSegment.isDeleted());
    }

    @Test
    void testDeletionInvariant() {
        _tileWallSegment.delete();

        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getWallSegmentType());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setWallSegmentType(null));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getHeight());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setHeight(0));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getZIndex());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setZIndex(0));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setName(""));
    }
}
