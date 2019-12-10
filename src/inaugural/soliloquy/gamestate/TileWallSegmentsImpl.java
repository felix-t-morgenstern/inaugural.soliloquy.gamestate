package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.TileWallSegmentArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.gamestate.entities.*;

import java.util.HashMap;
import java.util.HashSet;

public class TileWallSegmentsImpl implements TileWallSegments {
    private final Tile TILE;
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final HashMap<TileWallSegmentDirection, HashSet<TileWallSegment>> TILE_WALL_SEGMENTS;
    private final TileWallSegment ARCHETYPE = new TileWallSegmentArchetype();

    private boolean _isDeleted;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentsImpl(Tile tile, CollectionFactory collectionFactory,
                                MapFactory mapFactory) {
        if (tile == null) {
            throw new IllegalArgumentException("TileWallSegments: tile must be non-null");
        }
        TILE = tile;
        if (collectionFactory == null) {
            throw new IllegalArgumentException("TileWallSegments: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException("TileWallSegments: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;

        TILE_WALL_SEGMENTS = new HashMap<>();
        TILE_WALL_SEGMENTS.put(TileWallSegmentDirection.NORTH, new HashSet<>());
        TILE_WALL_SEGMENTS.put(TileWallSegmentDirection.NORTHWEST, new HashSet<>());
        TILE_WALL_SEGMENTS.put(TileWallSegmentDirection.WEST, new HashSet<>());
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("_representation");
        return TileWallSegments.class.getCanonicalName();
    }

    @Override
    public ReadableMap<TileWallSegmentDirection, ReadableCollection<TileWallSegment>> representation()
            throws IllegalStateException {
        enforceDeletionInvariants("_representation");
        Map<TileWallSegmentDirection, ReadableCollection<TileWallSegment>> map =
                MAP_FACTORY.make(TileWallSegmentDirection.UNKNOWN,
                        COLLECTION_FACTORY.make(ARCHETYPE));

        Collection<TileWallSegment> north = COLLECTION_FACTORY.make(ARCHETYPE);
        Collection<TileWallSegment> northwest = COLLECTION_FACTORY.make(ARCHETYPE);
        Collection<TileWallSegment> west = COLLECTION_FACTORY.make(ARCHETYPE);

        TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTH).forEach(north::add);
        TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).forEach(northwest::add);
        TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.WEST).forEach(west::add);

        map.put(TileWallSegmentDirection.NORTH, north.readOnlyRepresentation());
        map.put(TileWallSegmentDirection.NORTHWEST, northwest.readOnlyRepresentation());
        map.put(TileWallSegmentDirection.WEST, west.readOnlyRepresentation());

        return map.readOnlyRepresentation();
    }

    @Override
    public void add(TileWallSegmentDirection tileWallSegmentDirection,
                    TileWallSegment tileWallSegment)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("add");
        if (tileWallSegmentDirection == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: tileWallSegmentDirection must be non-null");
        }
        if (tileWallSegmentDirection == TileWallSegmentDirection.UNKNOWN) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: tileWallSegmentDirection cannot be UNKNOWN");
        }
        if (tileWallSegmentDirection == TileWallSegmentDirection.NOT_FOUND) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: tileWallSegmentDirection cannot be NOT_FOUND");
        }
        if (tileWallSegment == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: tileWallSegment must be non-null");
        }
        if (tileWallSegment.getTile() != null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsImpl.add: tileWallSegment is already present on a Tile");
        }
        TILE_WALL_SEGMENTS.get(tileWallSegmentDirection).add(tileWallSegment);
        tileWallSegment.assignTileWallSegmentsToTileAfterAddingToTileWallSegments(
                tileWallSegmentDirection, TILE);
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
        return TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTH).remove(tileWallSegment) ||
                TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).remove(tileWallSegment) ||
                TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.WEST).remove(tileWallSegment);
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
        return TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTH).contains(tileWallSegment) ||
            TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).contains(tileWallSegment) ||
            TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.WEST).contains(tileWallSegment);
    }

    @Override
    public TileWallSegmentDirection getDirection(TileWallSegment tileWallSegment)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getDirection");
        enforceAssignmentInvariant("getDirection", tileWallSegment);
        if (TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTH).contains(tileWallSegment)) {
            return TileWallSegmentDirection.NORTH;
        }
        if (TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTHWEST).contains(tileWallSegment)) {
            return TileWallSegmentDirection.NORTHWEST;
        }
        if (TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.WEST).contains(tileWallSegment)) {
            return TileWallSegmentDirection.WEST;
        }
        return TileWallSegmentDirection.NOT_FOUND;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        TILE_WALL_SEGMENTS.keySet().forEach(dir -> TILE_WALL_SEGMENTS.get(dir)
                .forEach(Deletable::delete));
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    private void enforceDeletionInvariants(String methodName) {
        if (_isDeleted) {
            throw new IllegalStateException("TileWallSegmentsImpl." + methodName +
                    ": tile is deleted");
        }
        if (TILE.isDeleted()) {
            throw new IllegalStateException("TileWallSegmentsImpl." + methodName +
                    ": tile is deleted");
        }
    }

    private void enforceAssignmentInvariant(String methodName, TileWallSegment tileWallSegment) {
        TileWallSegmentDirection tileWallSegmentDirection = null;
        if (TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTH).contains(tileWallSegment)) {
            tileWallSegmentDirection = TileWallSegmentDirection.NORTH;
        } else if (TILE_WALL_SEGMENTS
                .get(TileWallSegmentDirection.NORTHWEST).contains(tileWallSegment)) {
            tileWallSegmentDirection = TileWallSegmentDirection.NORTHWEST;
        } else if (TILE_WALL_SEGMENTS
                .get(TileWallSegmentDirection.WEST).contains(tileWallSegment)) {
            tileWallSegmentDirection = TileWallSegmentDirection.WEST;
        }

        if (tileWallSegmentDirection != null) {
            Pair<TileWallSegmentDirection, Tile> tileWallSegmentTileAndDirection =
                    tileWallSegment.getTile();
            if (tileWallSegmentTileAndDirection == null) {
                throw new IllegalStateException("TileWallSegmentsImpl." + methodName +
                        ": tileWallSegment was expected in this object's Tile, but instead has " +
                        "no Tile");
            }
            if (tileWallSegmentTileAndDirection.getItem2() != TILE) {
                throw new IllegalStateException("TileWallSegmentsImpl." + methodName +
                        ": tileWallSegment was expected in this object's Tile, but instead has " +
                        "a different Tile");
            }
            if (tileWallSegmentTileAndDirection.getItem1() != tileWallSegmentDirection) {
                throw new IllegalStateException("TileWallSegmentsImpl." + methodName +
                        ": tileWallSegment was expected on this object's Tile, but in a " +
                        "different direction");
            }
        }
    }
}
