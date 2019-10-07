package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileWallSegmentsImpl;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.MapFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import inaugural.soliloquy.gamestate.test.stubs.TileWallSegmentStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.gamestate.entities.TileWallSegments;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentsImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final Tile TILE = new TileStub();
    private final TileWallSegment TILE_WALL_SEGMENT = new TileWallSegmentStub();
    private final TileWallSegment TILE_WALL_SEGMENT_2 = new TileWallSegmentStub();
    private final TileWallSegment TILE_WALL_SEGMENT_3 = new TileWallSegmentStub();

    private TileWallSegments _tileWallSegments;

    @BeforeEach
    void setUp() {
        _tileWallSegments = new TileWallSegmentsImpl(TILE, COLLECTION_FACTORY, MAP_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsImpl(null, COLLECTION_FACTORY, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsImpl(TILE, null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsImpl(TILE, COLLECTION_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegments.class.getCanonicalName(),
                _tileWallSegments.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        assertFalse(_tileWallSegments.contains(TILE_WALL_SEGMENT));
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT);

        assertTrue(_tileWallSegments.contains(TILE_WALL_SEGMENT));
    }

    @Test
    void testGetDirection() {
        assertEquals(TileWallSegmentDirection.NOT_FOUND,
                _tileWallSegments.getDirection(TILE_WALL_SEGMENT));

        TileWallSegment northTileWallSegment = new TileWallSegmentStub();
        TileWallSegment northwestTileWallSegment = new TileWallSegmentStub();
        TileWallSegment westTileWallSegment = new TileWallSegmentStub();


        _tileWallSegments.add(TileWallSegmentDirection.NORTH, northTileWallSegment);
        _tileWallSegments.add(TileWallSegmentDirection.NORTHWEST, northwestTileWallSegment);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, westTileWallSegment);

        assertEquals(TileWallSegmentDirection.NORTH,
                _tileWallSegments.getDirection(northTileWallSegment));
        assertEquals(TileWallSegmentDirection.NORTHWEST,
                _tileWallSegments.getDirection(northwestTileWallSegment));
        assertEquals(TileWallSegmentDirection.WEST,
                _tileWallSegments.getDirection(westTileWallSegment));
    }

    @Test
    void testAddTileWallSegmentInAnotherTileWallSegments() {
        TileWallSegments previousTileWallSegments = new TileWallSegmentsImpl(new TileStub(),
                COLLECTION_FACTORY, MAP_FACTORY);
        TileWallSegment tileWallSegment = new TileWallSegmentStub();
        previousTileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment);

        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment));
    }

    @Test
    void testRemove() {
        assertFalse(_tileWallSegments.remove(TILE_WALL_SEGMENT));
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT);

        assertTrue(_tileWallSegments.remove(TILE_WALL_SEGMENT));
        assertFalse(_tileWallSegments.remove(TILE_WALL_SEGMENT));
    }

    @Test
    void testAddContainsAndRemoveWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(null, TILE_WALL_SEGMENT));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.UNKNOWN, TILE_WALL_SEGMENT));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NOT_FOUND,
                        TILE_WALL_SEGMENT));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, null));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.contains(null));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.remove(null));
    }

    @Test
    void testGetRepresentation() {
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT);
        _tileWallSegments.add(TileWallSegmentDirection.NORTHWEST, TILE_WALL_SEGMENT_2);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3);

        ReadableMap<TileWallSegmentDirection,ReadableCollection<TileWallSegment>> representation =
                _tileWallSegments.representation();

        assertNotNull(representation);
        assertNotNull(representation.getFirstArchetype());
        assertEquals(TileWallSegment.class.getCanonicalName(),
                representation.getSecondArchetype().getArchetype().getInterfaceName());
        assertEquals(3, representation.size());
        assertTrue(representation.get(TileWallSegmentDirection.NORTH)
                .contains(TILE_WALL_SEGMENT));
        assertTrue(representation.get(TileWallSegmentDirection.NORTHWEST)
                .contains(TILE_WALL_SEGMENT_2));
        assertTrue(representation.get(TileWallSegmentDirection.WEST)
                .contains(TILE_WALL_SEGMENT_3));
    }

    @Test
    void testDelete() {
        assertFalse(_tileWallSegments.isDeleted());

        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT);
        _tileWallSegments.add(TileWallSegmentDirection.NORTHWEST, TILE_WALL_SEGMENT_2);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3);

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
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.remove(TILE_WALL_SEGMENT));
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.deleteAfterDeletingContainingGameZone();

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.representation());
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.remove(TILE_WALL_SEGMENT));
    }

    @Test
    void testTileWallSegmentInCorrectTileWallSegmentsInvariant() {
        TileWallSegmentStub tileWallSegment = new TileWallSegmentStub();
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment);

        tileWallSegment._tile = null;

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.contains(tileWallSegment));
        assertThrows(IllegalStateException.class, () ->
                _tileWallSegments.getDirection(tileWallSegment));

        tileWallSegment._tile = new TileStub();

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.contains(tileWallSegment));
        assertThrows(IllegalStateException.class, () ->
                _tileWallSegments.getDirection(tileWallSegment));

        tileWallSegment._tile = TILE;
        tileWallSegment._tileWallSegmentDirection = TileWallSegmentDirection.WEST;

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.contains(tileWallSegment));
        assertThrows(IllegalStateException.class, () ->
                _tileWallSegments.getDirection(tileWallSegment));
    }
}
