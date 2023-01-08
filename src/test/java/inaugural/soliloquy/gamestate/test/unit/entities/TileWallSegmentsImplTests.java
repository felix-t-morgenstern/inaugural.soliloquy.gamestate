package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileWallSegmentsImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileWallSegment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.ArrayList;
import java.util.Map;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentsImplTests {
    private final Tile TILE = new FakeTile();
    private final TileWallSegment TILE_WALL_SEGMENT = new FakeTileWallSegment();
    private final TileWallSegment TILE_WALL_SEGMENT_2 = new FakeTileWallSegment();
    private final TileWallSegment TILE_WALL_SEGMENT_3 = new FakeTileWallSegment();
    int HEIGHT_1 = randomInt();
    int HEIGHT_2 = randomInt();
    int HEIGHT_3 = randomInt();
    int Z_1 = randomInt();
    int Z_2 = randomInt();
    int Z_3 = randomInt();
    @SuppressWarnings("FieldCanBeLocal")
    private final int DEFAULT_Z = 0;

    private TileWallSegments tileWallSegments;

    @BeforeEach
    void setUp() {
        tileWallSegments = new TileWallSegmentsImpl(TILE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegmentsImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegments.class.getCanonicalName(),
                tileWallSegments.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        assertFalse(tileWallSegments.contains(TILE_WALL_SEGMENT));
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, randomInt());

        assertTrue(tileWallSegments.contains(TILE_WALL_SEGMENT));
    }

    @Test
    void testAddAndGetZIndex() {
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, Z_1);

        assertEquals(DEFAULT_Z, tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
    }

    @Test
    void testAddWithZIndexAndGetZIndex() {
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, randomInt(), Z_1);

        assertEquals(Z_1, tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
    }

    @Test
    void testSetZIndex() {
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, Z_1);

        tileWallSegments.setZIndex(TILE_WALL_SEGMENT, Z_2);

        assertEquals(Z_2, tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
    }

    @Test
    void testGetDirection() {
        assertNull(tileWallSegments.getDirection(TILE_WALL_SEGMENT));

        TileWallSegment northTileWallSegment = new FakeTileWallSegment();
        TileWallSegment northwestTileWallSegment = new FakeTileWallSegment();
        TileWallSegment westTileWallSegment = new FakeTileWallSegment();


        tileWallSegments.add(TileWallSegmentDirection.NORTH, northTileWallSegment, randomInt());
        tileWallSegments.add(TileWallSegmentDirection.NORTHWEST, northwestTileWallSegment,
                randomInt());
        tileWallSegments.add(TileWallSegmentDirection.WEST, westTileWallSegment, randomInt());

        assertEquals(TileWallSegmentDirection.NORTH,
                tileWallSegments.getDirection(northTileWallSegment));
        assertEquals(TileWallSegmentDirection.NORTHWEST,
                tileWallSegments.getDirection(northwestTileWallSegment));
        assertEquals(TileWallSegmentDirection.WEST,
                tileWallSegments.getDirection(westTileWallSegment));
    }

    @Test
    void testAddTileWallSegmentInAnotherTileWallSegments() {
        TileWallSegments previousTileWallSegments = new TileWallSegmentsImpl(new FakeTile());
        TileWallSegment tileWallSegment = new FakeTileWallSegment();
        previousTileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment, randomInt());

        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment,
                        randomInt()));
    }

    @Test
    void testRemove() {
        assertFalse(tileWallSegments.remove(TILE_WALL_SEGMENT));
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, randomInt());

        assertTrue(tileWallSegments.remove(TILE_WALL_SEGMENT));
        assertFalse(tileWallSegments.remove(TILE_WALL_SEGMENT));
    }

    @Test
    void testAddContainsAndRemoveWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.add(null, TILE_WALL_SEGMENT, randomInt()));
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.add(TileWallSegmentDirection.NORTH, null, randomInt()));
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.add(null, TILE_WALL_SEGMENT, randomInt()));
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.add(TileWallSegmentDirection.NORTH, null, randomInt()));
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.contains(null));
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.remove(null));
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.getZIndex(null));
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.setZIndex(null, randomInt()));
        assertThrows(IllegalArgumentException.class,
                () -> tileWallSegments.setZIndex(TILE_WALL_SEGMENT, randomInt()));
    }

    @Test
    void testSize() {
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, randomInt());
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT_2, randomInt());
        tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, randomInt());

        assertEquals(3, tileWallSegments.size());
    }

    @Test
    void testSizeWithinDirection() {
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, randomInt());
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT_2, randomInt());
        tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, randomInt());

        assertEquals(2, tileWallSegments.size(TileWallSegmentDirection.NORTH));
        assertEquals(0, tileWallSegments.size(TileWallSegmentDirection.NORTHWEST));
        assertEquals(1, tileWallSegments.size(TileWallSegmentDirection.WEST));
    }

    @Test
    void testSizeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> tileWallSegments.size(null));
    }

    @Test
    void testIterator() {
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, HEIGHT_1, Z_1);
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT_2, HEIGHT_2, Z_2);
        tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, HEIGHT_3, Z_3);

        ArrayList<Pair<TileWallSegmentDirection, Pair<TileWallSegment,
                TileWallSegmentDimensions>>> fromIterator = new ArrayList<>();

        tileWallSegments.forEach(fromIterator::add);

        boolean[] segmentFound = new boolean[3];
        fromIterator.forEach(pair -> {
            TileWallSegmentDirection direction = pair.getItem1();
            if (pair.getItem2().getItem1() == TILE_WALL_SEGMENT) {
                assertFalse(segmentFound[0]);
                assertEquals(TileWallSegmentDirection.NORTH, direction);
                assertEquals(HEIGHT_1, pair.getItem2().getItem2().getHeight());
                assertEquals(Z_1, pair.getItem2().getItem2().getZIndex());
                segmentFound[0] = true;
            }
            if (pair.getItem2().getItem1() == TILE_WALL_SEGMENT_2) {
                assertFalse(segmentFound[1]);
                assertEquals(TileWallSegmentDirection.NORTH, direction);
                assertEquals(HEIGHT_2, pair.getItem2().getItem2().getHeight());
                assertEquals(Z_2, pair.getItem2().getItem2().getZIndex());
                segmentFound[1] = true;
            }
            if (pair.getItem2().getItem1() == TILE_WALL_SEGMENT_3) {
                assertFalse(segmentFound[2]);
                assertEquals(TileWallSegmentDirection.WEST, direction);
                assertEquals(HEIGHT_3, pair.getItem2().getItem2().getHeight());
                assertEquals(Z_3, pair.getItem2().getItem2().getZIndex());
                segmentFound[2] = true;
            }
        });
    }

    @Test
    void testGetRepresentation() {
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, HEIGHT_1, Z_1);
        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT_2, HEIGHT_2, Z_2);
        tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, HEIGHT_3, Z_3);

        Map<TileWallSegmentDirection, Map<TileWallSegment, TileWallSegmentDimensions>>
                representation = tileWallSegments.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.get(TileWallSegmentDirection.NORTH)
                .containsKey(TILE_WALL_SEGMENT));
        assertTrue(representation.get(TileWallSegmentDirection.NORTH)
                .containsKey(TILE_WALL_SEGMENT_2));
        assertTrue(representation.get(TileWallSegmentDirection.WEST)
                .containsKey(TILE_WALL_SEGMENT_3));
        assertEquals(HEIGHT_1,
                representation.get(TileWallSegmentDirection.NORTH).get(TILE_WALL_SEGMENT)
                        .getHeight());
        assertEquals(Z_1,
                representation.get(TileWallSegmentDirection.NORTH).get(TILE_WALL_SEGMENT)
                        .getZIndex());
        assertEquals(HEIGHT_2,
                representation.get(TileWallSegmentDirection.NORTH).get(TILE_WALL_SEGMENT_2)
                        .getHeight());
        assertEquals(Z_2,
                representation.get(TileWallSegmentDirection.NORTH).get(TILE_WALL_SEGMENT_2)
                        .getZIndex());
        assertEquals(HEIGHT_3,
                representation.get(TileWallSegmentDirection.WEST).get(TILE_WALL_SEGMENT_3)
                        .getHeight());
        assertEquals(Z_3,
                representation.get(TileWallSegmentDirection.WEST).get(TILE_WALL_SEGMENT_3)
                        .getZIndex());
    }

    @Test
    void testDelete() {
        assertFalse(tileWallSegments.isDeleted());

        tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT, 0);
        tileWallSegments.add(TileWallSegmentDirection.NORTHWEST, TILE_WALL_SEGMENT_2, 0);
        tileWallSegments.add(TileWallSegmentDirection.WEST, TILE_WALL_SEGMENT_3, 0);

        tileWallSegments.delete();

        assertTrue(tileWallSegments.isDeleted());

        assertTrue(TILE_WALL_SEGMENT.isDeleted());
        assertTrue(TILE_WALL_SEGMENT_2.isDeleted());
        assertTrue(TILE_WALL_SEGMENT_3.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        tileWallSegments.delete();

        assertThrows(EntityDeletedException.class, () -> tileWallSegments.getInterfaceName());
        assertThrows(EntityDeletedException.class, () -> tileWallSegments.representation());
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT,
                        randomInt()));
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT,
                        randomInt(), randomInt()));
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegments.setZIndex(TILE_WALL_SEGMENT, randomInt()));
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegments.remove(TILE_WALL_SEGMENT));
        assertThrows(EntityDeletedException.class, () -> tileWallSegments.size());
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegments.size(TileWallSegmentDirection.NORTH));
    }

    @Test
    void testTileDeletedInvariant() {
        TILE.gameZone().delete();
        TILE.delete();

        assertThrows(IllegalStateException.class, () -> tileWallSegments.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> tileWallSegments.representation());
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT,
                        randomInt()));
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.add(TileWallSegmentDirection.NORTH, TILE_WALL_SEGMENT,
                        randomInt(), randomInt()));
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.getZIndex(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.setZIndex(TILE_WALL_SEGMENT, randomInt()));
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.contains(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.remove(TILE_WALL_SEGMENT));
        assertThrows(IllegalStateException.class, () -> tileWallSegments.size());
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.size(TileWallSegmentDirection.NORTH));
    }

    @Test
    void testTileWallSegmentInCorrectTileWallSegmentsInvariant() {
        FakeTileWallSegment tileWallSegment = new FakeTileWallSegment();
        tileWallSegments.add(TileWallSegmentDirection.NORTH, tileWallSegment, randomInt());

        tileWallSegment._tile = null;

        assertThrows(IllegalStateException.class, () -> tileWallSegments.remove(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.contains(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.getDirection(tileWallSegment));

        tileWallSegment._tile = new FakeTile();

        assertThrows(IllegalStateException.class, () -> tileWallSegments.remove(tileWallSegment));
        assertThrows(IllegalStateException.class,
                () -> tileWallSegments.contains(tileWallSegment));
        assertThrows(IllegalStateException.class, () ->
                tileWallSegments.getDirection(tileWallSegment));
    }
}
