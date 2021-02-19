package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileWallSegmentsImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.fakes.FakeMapFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakePairFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileWallSegment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentsImplTests {
    private final PairFactory PAIR_FACTORY = new FakePairFactory();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();
    private final Tile TILE = new FakeTile();
    private final TileWallSegment TILE_WALL_SEGMENT = new FakeTileWallSegment();
    private final TileWallSegment TILE_WALL_SEGMENT_2 = new FakeTileWallSegment();
    private final TileWallSegment TILE_WALL_SEGMENT_3 = new FakeTileWallSegment();

    private TileWallSegments _tileWallSegments;

    @BeforeEach
    void setUp() {
        _tileWallSegments = new TileWallSegmentsImpl(TILE, PAIR_FACTORY, MAP_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsImpl(null, PAIR_FACTORY, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsImpl(TILE, null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileWallSegmentsImpl(TILE, PAIR_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegments.class.getCanonicalName(),
                _tileWallSegments.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        assertFalse(_tileWallSegments.contains(TILE_WALL_SEGMENT));
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0);

        assertTrue(_tileWallSegments.contains(TILE_WALL_SEGMENT));
    }

    @Test
    void testAddAndGetZIndex() {
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0);

        assertEquals(0, _tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
    }

    @Test
    void testAddWithZIndexAndGetZIndex() {
        final int z = 123;

        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0, z);

        assertEquals(z, _tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
    }

    @Test
    void testSetZIndex() {
        final int z1 = 123;
        final int z2 = 456;
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, z1);

        _tileWallSegments.setZIndex(TILE_WALL_SEGMENT, z2);

        assertEquals(z2, _tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
    }

    @Test
    void testGetDirection() {
        assertEquals(TileWallSegmentDirection.NOT_FOUND,
                _tileWallSegments.getDirection(TILE_WALL_SEGMENT));

        TileWallSegment northTileWallSegment = new FakeTileWallSegment();
        TileWallSegment northwestTileWallSegment = new FakeTileWallSegment();
        TileWallSegment westTileWallSegment = new FakeTileWallSegment();


        _tileWallSegments.add(TileWallSegmentDirection.NORTH, northTileWallSegment, 0);
        _tileWallSegments.add(TileWallSegmentDirection.NORTHWEST, northwestTileWallSegment, 0);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, westTileWallSegment, 0);

        assertEquals(TileWallSegmentDirection.NORTH,
                _tileWallSegments.getDirection(northTileWallSegment));
        assertEquals(TileWallSegmentDirection.NORTHWEST,
                _tileWallSegments.getDirection(northwestTileWallSegment));
        assertEquals(TileWallSegmentDirection.WEST,
                _tileWallSegments.getDirection(westTileWallSegment));
    }

    @Test
    void testAddTileWallSegmentInAnotherTileWallSegments() {
        TileWallSegments previousTileWallSegments = new TileWallSegmentsImpl(new FakeTile(),
                PAIR_FACTORY, MAP_FACTORY);
        TileWallSegment tileWallSegment = new FakeTileWallSegment();
        previousTileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment, 0);

        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment, 0));
    }

    @Test
    void testRemove() {
        assertFalse(_tileWallSegments.remove(TILE_WALL_SEGMENT));
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0);

        assertTrue(_tileWallSegments.remove(TILE_WALL_SEGMENT));
        assertFalse(_tileWallSegments.remove(TILE_WALL_SEGMENT));
    }

    @Test
    void testAddContainsAndRemoveWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(null, TILE_WALL_SEGMENT, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.UNKNOWN, TILE_WALL_SEGMENT,
                        0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NOT_FOUND,
                        TILE_WALL_SEGMENT, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, null, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(null, TILE_WALL_SEGMENT, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.UNKNOWN, TILE_WALL_SEGMENT,
                        0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NOT_FOUND,
                        TILE_WALL_SEGMENT, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, null, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.contains(null));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.remove(null));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.getZIndex(null));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.setZIndex(null, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.setZIndex(TILE_WALL_SEGMENT, 0));
    }

    @Test
    void testSize() {
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0);
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT_2, 0);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, 0);

        assertEquals(3, _tileWallSegments.size());
    }

    @Test
    void testSizeWithinDirection() {
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0);
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT_2, 0);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, 0);

        assertEquals(2, _tileWallSegments.size(TileWallSegmentDirection.NORTH));
        assertEquals(0, _tileWallSegments.size(TileWallSegmentDirection.NORTHWEST));
        assertEquals(1, _tileWallSegments.size(TileWallSegmentDirection.WEST));
    }

    @Test
    void testSizeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileWallSegments.size(null));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.size(TileWallSegmentDirection.UNKNOWN));
        assertThrows(IllegalArgumentException.class,
                () -> _tileWallSegments.size(TileWallSegmentDirection.NOT_FOUND));
    }

    @Test
    void testIterator() {
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 12, 34);
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT_2, 56, 78);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, 90, 123456);

        ArrayList<Pair<TileWallSegmentDirection, Pair<TileWallSegment,
                        TileWallSegmentDimensions>>> fromIterator = new ArrayList<>();

        _tileWallSegments.forEach(fromIterator::add);

        boolean[] segmentFound = new boolean[3];
        fromIterator.forEach(pair -> {
            TileWallSegmentDirection direction = pair.getItem1();
            if (pair.getItem2().getItem1() == TILE_WALL_SEGMENT) {
                assertFalse(segmentFound[0]);
                assertEquals(TileWallSegmentDirection.NORTH, direction);
                assertEquals(12, pair.getItem2().getItem2().getHeight());
                assertEquals(34, pair.getItem2().getItem2().getZIndex());
                segmentFound[0] = true;
            }
            if (pair.getItem2().getItem1() == TILE_WALL_SEGMENT_2) {
                assertFalse(segmentFound[1]);
                assertEquals(TileWallSegmentDirection.NORTH, direction);
                assertEquals(56, pair.getItem2().getItem2().getHeight());
                assertEquals(78, pair.getItem2().getItem2().getZIndex());
                segmentFound[1] = true;
            }
            if (pair.getItem2().getItem1() == TILE_WALL_SEGMENT_3) {
                assertFalse(segmentFound[2]);
                assertEquals(TileWallSegmentDirection.WEST, direction);
                assertEquals(90, pair.getItem2().getItem2().getHeight());
                assertEquals(123456, pair.getItem2().getItem2().getZIndex());
                segmentFound[2] = true;
            }
        });
    }

    @Test
    void testGetRepresentation() {
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 12, 34);
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT_2, 56, 78);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, 90, 123456);

        Map<TileWallSegmentDirection, Map<TileWallSegment, TileWallSegmentDimensions>>
                representation = _tileWallSegments.representation();

        assertNotNull(representation);
        assertNotNull(representation.getFirstArchetype());
        assertEquals(3, representation.size());
        assertTrue(representation.get(TileWallSegmentDirection.NORTH)
                .containsKey(TILE_WALL_SEGMENT));
        assertTrue(representation.get(TileWallSegmentDirection.NORTH)
                .containsKey(TILE_WALL_SEGMENT_2));
        assertTrue(representation.get(TileWallSegmentDirection.WEST)
                .containsKey(TILE_WALL_SEGMENT_3));
        assertEquals(12,
                representation.get(TileWallSegmentDirection.NORTH).get(TILE_WALL_SEGMENT)
                        .getHeight());
        assertEquals(34,
                representation.get(TileWallSegmentDirection.NORTH).get(TILE_WALL_SEGMENT)
                        .getZIndex());
        assertEquals(56,
                representation.get(TileWallSegmentDirection.NORTH).get(TILE_WALL_SEGMENT_2)
                        .getHeight());
        assertEquals(78,
                representation.get(TileWallSegmentDirection.NORTH).get(TILE_WALL_SEGMENT_2)
                        .getZIndex());
        assertEquals(90,
                representation.get(TileWallSegmentDirection.WEST).get(TILE_WALL_SEGMENT_3)
                        .getHeight());
        assertEquals(123456,
                representation.get(TileWallSegmentDirection.WEST).get(TILE_WALL_SEGMENT_3)
                        .getZIndex());
    }

    @Test
    void testDelete() {
        assertFalse(_tileWallSegments.isDeleted());

        _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0);
        _tileWallSegments.add(TileWallSegmentDirection.NORTHWEST, TILE_WALL_SEGMENT_2, 0);
        _tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, 0);

        _tileWallSegments.delete();

        assertTrue(_tileWallSegments.isDeleted());

        assertTrue(TILE_WALL_SEGMENT.isDeleted());
        assertTrue(TILE_WALL_SEGMENT_2.isDeleted());
        assertTrue(TILE_WALL_SEGMENT_3.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        _tileWallSegments.delete();

        assertThrows(EntityDeletedException.class, () -> _tileWallSegments.getInterfaceName());
        assertThrows(EntityDeletedException.class, () -> _tileWallSegments.representation());
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0));
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0,
                        0));
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegments.setZIndex(TILE_WALL_SEGMENT, 0));
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegments.remove(TILE_WALL_SEGMENT));
        assertThrows(EntityDeletedException.class, () -> _tileWallSegments.size());
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegments.size(TileWallSegmentDirection.NORTH));
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.gameZone().delete();
        TILE.delete();

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.representation());
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0,
                        0));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.setZIndex(TILE_WALL_SEGMENT, 0));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.remove(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> _tileWallSegments.size());
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.size(TileWallSegmentDirection.NORTH));
    }

    @Test
    void testTileWallSegmentInCorrectTileWallSegmentsInvariant() {
        FakeTileWallSegment tileWallSegment = new FakeTileWallSegment();
        _tileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment, 0);

        tileWallSegment._tile = null;

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.contains(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.getDirection(tileWallSegment));

        tileWallSegment._tile = new FakeTile();

        assertThrows(IllegalStateException.class, () -> _tileWallSegments.remove(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegments.contains(tileWallSegment));
        assertThrows(IllegalStateException.class, () ->
                _tileWallSegments.getDirection(tileWallSegment));
    }
}
