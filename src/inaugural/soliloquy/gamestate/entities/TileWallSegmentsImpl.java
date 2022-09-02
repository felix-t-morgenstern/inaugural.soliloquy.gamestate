package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TileWallSegmentsImpl implements TileWallSegments {
    private final Tile TILE;
    private final HashMap<TileWallSegmentDirection,
            HashMap<TileWallSegment, TileWallSegmentDimensions>> SEGMENTS;
    @SuppressWarnings("FieldCanBeLocal")
    private final int DEFAULT_Z = 0;

    private boolean _isDeleted;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentsImpl(Tile tile) {
        TILE = Check.ifNull(tile, "tile");

        SEGMENTS = new HashMap<>();
        SEGMENTS.put(TileWallSegmentDirection.NORTH, new HashMap<>());
        SEGMENTS.put(TileWallSegmentDirection.NORTHWEST, new HashMap<>());
        SEGMENTS.put(TileWallSegmentDirection.WEST, new HashMap<>());
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("_representation");
        return TileWallSegments.class.getCanonicalName();
    }

    @Override
    public Map<TileWallSegmentDirection, Map<TileWallSegment, TileWallSegmentDimensions>>
    representation()
            throws IllegalStateException {
        enforceDeletionInvariants("representation");

        HashMap<TileWallSegmentDirection, Map<TileWallSegment, TileWallSegmentDimensions>> map =
                new HashMap<>();

        HashMap<TileWallSegment, TileWallSegmentDimensions> north =
                new HashMap<>(SEGMENTS.get(TileWallSegmentDirection.NORTH));
        HashMap<TileWallSegment, TileWallSegmentDimensions> northwest =
                new HashMap<>(SEGMENTS.get(TileWallSegmentDirection.NORTHWEST));
        HashMap<TileWallSegment, TileWallSegmentDimensions> west =
                new HashMap<>(SEGMENTS.get(TileWallSegmentDirection.WEST));

        map.put(TileWallSegmentDirection.NORTH, north);
        map.put(TileWallSegmentDirection.NORTHWEST, northwest);
        map.put(TileWallSegmentDirection.WEST, west);

        return map;
    }

    @Override
    public void add(TileWallSegmentDirection direction, TileWallSegment segment, int height)
            throws IllegalArgumentException, IllegalStateException {
        add(direction, segment, height, DEFAULT_Z);
    }

    @Override
    public void add(TileWallSegmentDirection direction, TileWallSegment segment, int height, int z)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("add");
        Check.ifNull(direction, "direction");
        if (direction == TileWallSegmentDirection.UNKNOWN) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: direction cannot be UNKNOWN");
        }
        if (direction == TileWallSegmentDirection.NOT_FOUND) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: direction cannot be NOT_FOUND");
        }
        Check.ifNull(segment, "segment");
        if (segment.tile() != null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: segment is already present on a Tile");
        }
        SEGMENTS.get(direction).put(segment, makeDimensions(height, z));
        segment.assignTileAfterAddedToTileEntitiesOfType(TILE);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Integer getZIndex(TileWallSegment segment)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getZIndex");
        if (segment == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: segment must be non-null");
        }
        return SEGMENTS.get(TileWallSegmentDirection.NORTH).containsKey(segment) ?
                SEGMENTS.get(TileWallSegmentDirection.NORTH).get(segment).getZIndex()
                : SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).containsKey(segment) ?
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).get(segment).getZIndex()
                : SEGMENTS.get(TileWallSegmentDirection.WEST).containsKey(segment) ?
                SEGMENTS.get(TileWallSegmentDirection.WEST).get(segment).getZIndex()
                : null;
    }

    @Override
    public void setZIndex(TileWallSegment segment, int z)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getZIndex");
        if (segment == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: segment must be non-null");
        }
        TileWallSegmentDimensions dimens;
        if ((dimens = SEGMENTS.get(TileWallSegmentDirection.NORTH).get(segment))
                != null) {
            SEGMENTS.get(TileWallSegmentDirection.NORTH).put(segment,
                    makeDimensions(dimens.getHeight(), z));
        }
        else if ((dimens = SEGMENTS.get(TileWallSegmentDirection.NORTH).get(segment))
                != null) {
            SEGMENTS.get(TileWallSegmentDirection.NORTH).put(segment,
                    makeDimensions(dimens.getHeight(), z));
        }
        else if ((dimens = SEGMENTS.get(TileWallSegmentDirection.NORTH).get(segment))
                != null) {
            SEGMENTS.get(TileWallSegmentDirection.NORTH).put(segment,
                    makeDimensions(dimens.getHeight(), z));
        }
        else {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.setZIndex: segment is not present in this class");
        }
    }

    @Override
    public Integer getHeight(TileWallSegment segment)
            throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public void setHeight(TileWallSegment segment, int height)
            throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean remove(TileWallSegment tileWallSegment)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("remove");
        enforceAssignmentInvariant("remove", tileWallSegment);
        if (tileWallSegment == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: tileWallSegment must be non-null");
        }
        return SEGMENTS.get(TileWallSegmentDirection.NORTH).remove(tileWallSegment)
                != null ||
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).remove(tileWallSegment)
                        != null ||
                SEGMENTS.get(TileWallSegmentDirection.WEST).remove(tileWallSegment)
                        != null;
    }

    @Override
    public boolean contains(TileWallSegment tileWallSegment)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("contains");
        enforceAssignmentInvariant("contains", tileWallSegment);
        if (tileWallSegment == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: tileWallSegment must be non-null");
        }
        return SEGMENTS.get(TileWallSegmentDirection.NORTH).containsKey(tileWallSegment)
                ||
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).containsKey(tileWallSegment)
                ||
                SEGMENTS.get(TileWallSegmentDirection.WEST).containsKey(tileWallSegment);
    }

    @Override
    public int size() throws IllegalStateException {
        enforceDeletionInvariants("size");
        return SEGMENTS.get(TileWallSegmentDirection.NORTH).size() +
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).size() +
                SEGMENTS.get(TileWallSegmentDirection.WEST).size();
    }

    @Override
    public int size(TileWallSegmentDirection direction) throws IllegalStateException {
        enforceDeletionInvariants("size");
        if (direction == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.size: direction cannot be null");
        }
        if (direction == TileWallSegmentDirection.UNKNOWN) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.size: direction cannot be UNKNOWN");
        }
        if (direction == TileWallSegmentDirection.NOT_FOUND) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.size: direction cannot be NOT_FOUND");
        }
        return SEGMENTS.get(direction).size();
    }

    @Override
    public TileWallSegmentDirection getDirection(TileWallSegment tileWallSegment)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getDirection");
        enforceAssignmentInvariant("getDirection", tileWallSegment);
        if (SEGMENTS.get(TileWallSegmentDirection.NORTH).containsKey(tileWallSegment)) {
            return TileWallSegmentDirection.NORTH;
        }
        if (SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).containsKey(tileWallSegment)) {
            return TileWallSegmentDirection.NORTHWEST;
        }
        if (SEGMENTS.get(TileWallSegmentDirection.WEST).containsKey(tileWallSegment)) {
            return TileWallSegmentDirection.WEST;
        }
        return TileWallSegmentDirection.NOT_FOUND;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        SEGMENTS.keySet().forEach(dir -> SEGMENTS.get(dir).keySet()
                .forEach(Deletable::delete));
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    private void enforceDeletionInvariants(String methodName) {
        if (_isDeleted) {
            throw new EntityDeletedException("TileWallSegmentsImpl." + methodName +
                    ": tile is deleted");
        }
        if (TILE.isDeleted()) {
            throw new IllegalStateException("TileWallSegmentsImpl." + methodName +
                    ": tile is deleted");
        }
    }

    private void enforceAssignmentInvariant(String methodName, TileWallSegment tileWallSegment) {
        if ((SEGMENTS.get(TileWallSegmentDirection.NORTH).containsKey(tileWallSegment) ||
                SEGMENTS.get(TileWallSegmentDirection.NORTHWEST)
                        .containsKey(tileWallSegment) ||
                SEGMENTS.get(TileWallSegmentDirection.WEST).containsKey(tileWallSegment))
                && tileWallSegment.tile() != TILE) {
            throw new IllegalStateException("TileWallSegmentsImpl." + methodName +
                    ": tileWallSegment was expected in this object's Tile, but instead has no " +
                    "Tile");
        }
    }

    private static TileWallSegmentDimensions makeDimensions(int height, int z) {
        return new TileWallSegmentDimensions() {
            @Override
            public int getZIndex() {
                return z;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public String getInterfaceName() {
                return TileWallSegmentDimensions.class.getCanonicalName();
            }
        };
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
