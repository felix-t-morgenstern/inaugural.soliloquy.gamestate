package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.ruleset.entities.WallSegmentType;
import soliloquy.specs.sprites.entities.Sprite;

public class FakeWallSegmentType implements WallSegmentType {
    String _id;

    public FakeWallSegmentType() {

    }

    public FakeWallSegmentType(String id) {
        _id = id;
    }

    @Override
    public TileWallSegment generate(Tile tile) {
        return null;
    }

    @Override
    public Sprite sprite() {
        return null;
    }

    @Override
    public boolean blocksWest() {
        return false;
    }

    @Override
    public boolean blocksNorthwest() {
        return false;
    }

    @Override
    public boolean blocksNorth() {
        return false;
    }

    @Override
    public String id() throws IllegalStateException {
        return _id;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
