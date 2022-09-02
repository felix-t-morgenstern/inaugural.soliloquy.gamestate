package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FakeTileWallSegments implements TileWallSegments {
    public final HashMap<TileWallSegmentDirection, HashMap<TileWallSegment,
            TileWallSegmentDimensions>> SEGMENTS = new HashMap<>();
    public final Tile TILE;

    private boolean _isDeleted;

    FakeTileWallSegments(Tile tile) {
        TILE = tile;
        SEGMENTS.put(TileWallSegmentDirection.NORTH, new HashMap<>());
        SEGMENTS.put(TileWallSegmentDirection.NORTHWEST, new HashMap<>());
        SEGMENTS.put(TileWallSegmentDirection.WEST, new HashMap<>());
    }

    @Override
    public Map<TileWallSegmentDirection, Map<TileWallSegment, TileWallSegmentDimensions>> representation()
            throws IllegalStateException {
        return null;
    }

    @Override
    public void add(TileWallSegmentDirection direction, TileWallSegment segment, int height)
            throws IllegalArgumentException, IllegalStateException {
        add(direction, segment, height, 0);
    }

    @Override
    public void add(TileWallSegmentDirection direction, TileWallSegment segment, int height, int z)
            throws IllegalArgumentException, IllegalStateException {
        SEGMENTS.get(direction).put(segment, makeDimensions(height, z));
        segment.assignTileAfterAddedToTileEntitiesOfType(TILE);
    }

    private TileWallSegmentDimensions makeDimensions(int height, int z) {
        return new TileWallSegmentDimensions() {

            @Override
            public String getInterfaceName() {
                return null;
            }

            @Override
            public int getZIndex() {
                return z;
            }

            @Override
            public int getHeight() {
                return height;
            }
        };
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Integer getZIndex(TileWallSegment segment)
            throws IllegalArgumentException, IllegalStateException {
        return SEGMENTS.get(TileWallSegmentDirection.NORTH).containsKey(segment) ?
                SEGMENTS.get(TileWallSegmentDirection.NORTH).get(segment).getZIndex() :
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).containsKey(segment) ?
                        SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).get(segment).getZIndex() :
                        SEGMENTS.get(TileWallSegmentDirection.WEST).containsKey(segment) ?
                                SEGMENTS.get(TileWallSegmentDirection.WEST).get(segment)
                                        .getZIndex() :
                                null;
    }

    @Override
    public void setZIndex(TileWallSegment segment, int i)
            throws IllegalArgumentException, IllegalStateException {

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Integer getHeight(TileWallSegment segment)
            throws IllegalArgumentException, IllegalStateException {
        return SEGMENTS.get(TileWallSegmentDirection.NORTH).containsKey(segment) ?
                SEGMENTS.get(TileWallSegmentDirection.NORTH).get(segment).getHeight() :
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).containsKey(segment) ?
                        SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).get(segment).getHeight() :
                        SEGMENTS.get(TileWallSegmentDirection.WEST).containsKey(segment) ?
                                SEGMENTS.get(TileWallSegmentDirection.WEST).get(segment)
                                        .getHeight() :
                                null;
    }

    @Override
    public void setHeight(TileWallSegment segment, int i)
            throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean remove(TileWallSegment tileWallSegment)
            throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean contains(TileWallSegment segment)
            throws IllegalArgumentException, IllegalStateException {
        return SEGMENTS.get(TileWallSegmentDirection.NORTH).containsKey(segment) ||
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).containsKey(segment) ||
                SEGMENTS.get(TileWallSegmentDirection.WEST).containsKey(segment);
    }

    @Override
    public int size() throws IllegalStateException {
        return SEGMENTS.get(TileWallSegmentDirection.NORTH).size() +
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).size() +
                SEGMENTS.get(TileWallSegmentDirection.WEST).size();
    }

    @Override
    public int size(TileWallSegmentDirection tileWallSegmentDirection)
            throws IllegalStateException {
        return 0;
    }

    @Override
    public TileWallSegmentDirection getDirection(TileWallSegment segment)
            throws IllegalArgumentException, IllegalStateException {
        if (SEGMENTS.get(TileWallSegmentDirection.NORTH).containsKey(segment)) {
            return TileWallSegmentDirection.NORTH;
        }
        else if (SEGMENTS.get(TileWallSegmentDirection.WEST)
                .containsKey(segment)) {
            return TileWallSegmentDirection.WEST;
        }
        else if (SEGMENTS.get(TileWallSegmentDirection.NORTHWEST)
                .containsKey(segment)) {
            return TileWallSegmentDirection.NORTHWEST;
        }
        else {
            return TileWallSegmentDirection.NOT_FOUND;
        }
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public Iterator<Pair<TileWallSegmentDirection, Pair<TileWallSegment,
                TileWallSegmentDimensions>>>
    iterator() {
        Iterator<TileWallSegment> northSegments =
                SEGMENTS.get(TileWallSegmentDirection.NORTH).keySet().iterator();
        Iterator<TileWallSegment> northwestSegments =
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).keySet().iterator();
        Iterator<TileWallSegment> westSegments =
                SEGMENTS.get(TileWallSegmentDirection.WEST).keySet().iterator();

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return northSegments.hasNext() || northwestSegments.hasNext() ||
                        westSegments.hasNext();
            }

            @Override
            public Pair<TileWallSegmentDirection, Pair<TileWallSegment, TileWallSegmentDimensions>>
            next() {
                TileWallSegment segment;
                return northSegments.hasNext() ?
                        new Pair<>(TileWallSegmentDirection.NORTH,
                                new Pair<>(segment = northSegments.next(),
                                        SEGMENTS.get(TileWallSegmentDirection.NORTH).get(segment)))
                        : northwestSegments.hasNext() ?
                        new Pair<>(TileWallSegmentDirection.NORTHWEST,
                                new Pair<>(segment = northwestSegments.next(),
                                        SEGMENTS.get(TileWallSegmentDirection.NORTHWEST)
                                                .get(segment)))
                        : new Pair<>(TileWallSegmentDirection.WEST,
                        new Pair<>(segment = westSegments.next(),
                                SEGMENTS.get(TileWallSegmentDirection.WEST).get(segment)));
            }
        };
    }
}
