package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileWallSegmentImpl;
import inaugural.soliloquy.gamestate.test.stubs.GenericParamsSetFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import inaugural.soliloquy.gamestate.test.stubs.TileWallSegmentsStub;
import inaugural.soliloquy.gamestate.test.stubs.WallSegmentTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentImplTests {
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();
    private final WallSegmentType WALL_SEGMENT_TYPE = new WallSegmentTypeStub();

    private TileWallSegment _tileWallSegment;

    @BeforeEach
    void setUp() {
        _tileWallSegment = new TileWallSegmentImpl(GENERIC_PARAMS_SET_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegmentImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegment.class.getCanonicalName(),
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
    void testAssignTileWallSegmentsToTileAfterAddingToTileWallSegmentsAndGetTile() {
        Tile tile = new TileStub();
        ((TileWallSegmentsStub)tile.wallSegments()).TILE_WALL_SEGMENTS
                .get(TileWallSegmentDirection.NORTH).add(_tileWallSegment);

        assertNull(_tileWallSegment.tile());

        _tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(tile);

        assertSame(tile, _tileWallSegment.tile());
    }

    @Test
    void testSetAndGetName() {
        final String name = "name";

        _tileWallSegment.setName(name);

        assertEquals(name, _tileWallSegment.getName());
    }

    @Test
    void testDelete() {
        // TODO: Add a TileWallSegment here
        assertFalse(_tileWallSegment.isDeleted());

        _tileWallSegment.delete();

        // TODO: Test whether added TileWallSegment was deleted
        assertTrue(_tileWallSegment.isDeleted());
    }

    @Test
    void testDeletionInvariant() {
        _tileWallSegment.delete();

        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getWallSegmentType());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setWallSegmentType(null));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getHeight());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setHeight(0));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getZIndex());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setZIndex(0));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.tile());
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.data());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setName(""));
    }

    @Test
    void testAggregateAssignmentInvariant() {
        Tile tile = new TileStub();
        tile.wallSegments().add(TileWallSegmentDirection.NORTH, _tileWallSegment);

        ((TileWallSegmentsStub)tile.wallSegments()).TILE_WALL_SEGMENTS
                .get(TileWallSegmentDirection.NORTH).remove(_tileWallSegment);

        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getWallSegmentType());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setWallSegmentType(null));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getHeight());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setHeight(0));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getZIndex());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setZIndex(0));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.tile());
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.data());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setName(""));
    }
}
