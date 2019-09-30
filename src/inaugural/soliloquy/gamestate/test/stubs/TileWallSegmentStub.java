package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.gamestate.entities.TileWallSegments;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentStub implements TileWallSegment {
    private boolean _isDeleted;

    public TileWallSegmentDirection _tileWallSegmentDirection;
    public Tile _tile;

    @Override
    public WallSegmentType getWallSegmentType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setWallSegmentType(WallSegmentType wallSegmentType) throws IllegalStateException {

    }

    @Override
    public int getHeight() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setHeight(int i) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public int getZIndex() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setZIndex(int i) throws IllegalStateException {

    }

    @Override
    public Pair<TileWallSegmentDirection, Tile> getTile() {
        if (_tile == null) {
            return null;
        }
        return new PairStub<>(_tileWallSegmentDirection, _tile);
    }

    @Override
    public void assignTileWallSegmentsToTileAfterAddingToTileWallSegments(TileWallSegmentDirection tileWallSegmentDirection, Tile tile) throws IllegalArgumentException, IllegalStateException {
        _tileWallSegmentDirection = tileWallSegmentDirection;
        _tile = tile;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

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
}
