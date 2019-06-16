package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.gamestate.entities.ITileWallSegment;
import soliloquy.specs.ruleset.entities.IWallSegmentType;

public class TileWallSegmentStub implements ITileWallSegment {
    private boolean _isDeleted;

    @Override
    public IWallSegmentType getWallSegmentType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setWallSegmentType(IWallSegmentType iWallSegmentType) throws IllegalStateException {

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
    public IGenericParamsSet data() throws IllegalStateException {
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
