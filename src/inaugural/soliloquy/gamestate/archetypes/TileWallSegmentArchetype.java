package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentArchetype implements TileWallSegment {
    @Override
    public WallSegmentType getWallSegmentType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setWallSegmentType(WallSegmentType iWallSegmentType) throws IllegalStateException {

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
        return null;
    }

    @Override
    public void assignTileWallSegmentsToTileAfterAddingToTileWallSegments(TileWallSegmentDirection tileWallSegmentDirection, Tile tile) throws IllegalArgumentException, IllegalStateException {

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

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegment.class.getCanonicalName();
    }
}
