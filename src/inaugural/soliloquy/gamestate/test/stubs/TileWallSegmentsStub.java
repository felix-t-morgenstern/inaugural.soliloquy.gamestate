package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.gamestate.entities.TileWallSegments;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class TileWallSegmentsStub implements TileWallSegments {
    public final HashMap<TileWallSegmentDirection, HashSet<TileWallSegment>> TILE_WALL_SEGMENTS =
            new HashMap<>();
    public final Tile TILE;

    private boolean _isDeleted;

    TileWallSegmentsStub(Tile tile) {
        TILE = tile;
        TILE_WALL_SEGMENTS.put(TileWallSegmentDirection.NORTH, new HashSet<>());
        TILE_WALL_SEGMENTS.put(TileWallSegmentDirection.NORTHWEST, new HashSet<>());
        TILE_WALL_SEGMENTS.put(TileWallSegmentDirection.WEST, new HashSet<>());
    }

    @Override
    public ReadableMap<TileWallSegmentDirection, ReadableCollection<TileWallSegment>> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void add(TileWallSegmentDirection tileWallSegmentDirection,
                    TileWallSegment tileWallSegment)
            throws IllegalArgumentException, IllegalStateException {
        TILE_WALL_SEGMENTS.get(tileWallSegmentDirection).add(tileWallSegment);
        tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(TILE);
    }

    @Override
    public boolean remove(TileWallSegment tileWallSegment) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean contains(TileWallSegment tileWallSegment) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public int size() throws IllegalStateException {
        return 0;
    }

    @Override
    public int size(TileWallSegmentDirection tileWallSegmentDirection) throws IllegalStateException {
        return 0;
    }

    @Override
    public TileWallSegmentDirection getDirection(TileWallSegment tileWallSegment) throws IllegalArgumentException, IllegalStateException {
        if (TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTH).contains(tileWallSegment)) {
            return TileWallSegmentDirection.NORTH;
        } else if (TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.WEST)
                .contains(tileWallSegment)) {
            return TileWallSegmentDirection.WEST;
        } else if (TILE_WALL_SEGMENTS.get(TileWallSegmentDirection.NORTHWEST)
                .contains(tileWallSegment)) {
            return TileWallSegmentDirection.NORTHWEST;
        } else {
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
    public Iterator<ReadablePair<TileWallSegment, TileWallSegmentDirection>> iterator() {
        return null;
    }
}
