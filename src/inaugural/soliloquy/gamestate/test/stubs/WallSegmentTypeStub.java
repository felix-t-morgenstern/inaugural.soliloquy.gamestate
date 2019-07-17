package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.ruleset.entities.WallSegmentType;
import soliloquy.specs.sprites.entities.Sprite;

public class WallSegmentTypeStub implements WallSegmentType {
    @Override
    public Sprite getSprite() {
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
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
