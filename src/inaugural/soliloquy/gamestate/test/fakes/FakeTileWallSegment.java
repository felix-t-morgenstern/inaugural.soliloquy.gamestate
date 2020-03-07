package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class FakeTileWallSegment implements TileWallSegment {
    private WallSegmentType _type;
    private boolean _isDeleted;
    private VariableCache _data;

    public Tile _tile;

    public FakeTileWallSegment() {

    }

    FakeTileWallSegment(VariableCache data) {
        _data = data;
    }

    @Override
    public WallSegmentType getType() throws IllegalStateException {
        return _type;
    }

    @Override
    public void setType(WallSegmentType type) throws IllegalStateException {
        _type = type;
    }

    @Override
    public Tile tile() {
        if (_tile == null) {
            return null;
        }
        return _tile;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile) throws IllegalArgumentException, IllegalStateException {
        _tile = tile;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
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
