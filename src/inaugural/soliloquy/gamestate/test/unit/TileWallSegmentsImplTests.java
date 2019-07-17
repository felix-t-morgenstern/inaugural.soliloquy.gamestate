package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileWallSegmentsImpl;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import inaugural.soliloquy.gamestate.test.stubs.TileWallSegmentStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.ReadOnlyCollection;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegments;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentsImplTests {
    private final Tile TILE = new TileStub();
    private final TileWallSegment TILE_WALL_SEGMENT = new TileWallSegmentStub();
    private final TileWallSegment TILE_WALL_SEGMENT_2 = new TileWallSegmentStub();
    private final TileWallSegment TILE_WALL_SEGMENT_3 = new TileWallSegmentStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private TileWallSegments _tileWallSegments;

    @BeforeEach
    void setUp() {
        _tileWallSegments = new TileWallSegmentsImpl(TILE, COLLECTION_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsImpl(null, COLLECTION_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegmentsImpl(TILE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegments.class.getCanonicalName(),
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

        ReadOnlyCollection<TileWallSegment> representation = _tileWallSegments.representation();

        assertNotNull(representation);
        assertNotNull(representation.getArchetype());
        assertEquals(TileWallSegment.class.getCanonicalName(),
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
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.representation());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.add(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(TILE_WALL_SEGMENT));
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.delete();

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.representation());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.add(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(TILE_WALL_SEGMENT));
    }
}
