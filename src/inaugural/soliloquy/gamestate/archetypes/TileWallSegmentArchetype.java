package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.ITileWallSegment;
import soliloquy.ruleset.gameentities.specs.IWallSegmentType;

public class TileWallSegmentArchetype implements ITileWallSegment {
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

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return ITileWallSegment.class.getCanonicalName();
    }
}
