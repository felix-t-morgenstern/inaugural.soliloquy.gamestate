package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileWallSegments;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import inaugural.soliloquy.gamestate.test.stubs.TileWallSegmentStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileWallSegment;
import soliloquy.specs.gamestate.entities.ITileWallSegments;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentsTests {
    private final ITile TILE = new TileStub();
    private final ITileWallSegment TILE_WALL_SEGMENT = new TileWallSegmentStub();
    private final ITileWallSegment TILE_WALL_SEGMENT_2 = new TileWallSegmentStub();
    private final ITileWallSegment TILE_WALL_SEGMENT_3 = new TileWallSegmentStub();
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private ITileWallSegments _tileWallSegments;

    @BeforeEach
    void setUp() {
        _tileWallSegments = new TileWallSegments(TILE, COLLECTION_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegments(null, COLLECTION_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegments(TILE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITileWallSegments.class.getCanonicalName(),
                _tileWallSegments.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        assertFalse(_tileWallSegments.contains(TILE_WALL_SEGMENT));
        _tileWallSegments.add(TILE_WALL_SEGMENT);

        assertTrue(_tileWallSegments.contains(TILE_WALL_SEGMENT));
    }

    @Test
    void testRemove() {
        assertFalse(_tileWallSegments.remove(TILE_WALL_SEGMENT));
        _tileWallSegments.add(TILE_WALL_SEGMENT);

        assertTrue(_tileWallSegments.remove(TILE_WALL_SEGMENT));
        assertFalse(_tileWallSegments.remove(TILE_WALL_SEGMENT));
    }

    @Test
    void testAddContainsAndRemoveWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileWallSegments.add(null));
        assertThrows(IllegalArgumentException.class, () -> _tileWallSegments.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _tileWallSegments.remove(null));
    }

    @Test
    void testGetRepresentation() {
        _tileWallSegments.add(TILE_WALL_SEGMENT);
        _tileWallSegments.add(TILE_WALL_SEGMENT_2);
        _tileWallSegments.add(TILE_WALL_SEGMENT_3);

        ICollection<ITileWallSegment> representation = _tileWallSegments.getRepresentation();

        assertNotNull(representation);
        assertNotNull(representation.getArchetype());
        assertEquals(ITileWallSegment.class.getCanonicalName(),
                representation.getArchetype().getInterfaceName());
        assertEquals(3, representation.size());
        assertTrue(representation.contains(TILE_WALL_SEGMENT));
        assertTrue(representation.contains(TILE_WALL_SEGMENT_2));
        assertTrue(representation.contains(TILE_WALL_SEGMENT_3));
    }

    @Test
    void testDelete() {
        assertFalse(_tileWallSegments.isDeleted());

        _tileWallSegments.add(TILE_WALL_SEGMENT);
        _tileWallSegments.add(TILE_WALL_SEGMENT_2);
        _tileWallSegments.add(TILE_WALL_SEGMENT_3);

        _tileWallSegments.delete();

        assertTrue(_tileWallSegments.isDeleted());

        assertTrue(TILE_WALL_SEGMENT.isDeleted());
        assertTrue(TILE_WALL_SEGMENT_2.isDeleted());
        assertTrue(TILE_WALL_SEGMENT_3.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        _tileWallSegments.delete();

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.getRepresentation());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.add(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(TILE_WALL_SEGMENT));
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.delete();

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.getRepresentation());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.add(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(TILE_WALL_SEGMENT));
    }
}
