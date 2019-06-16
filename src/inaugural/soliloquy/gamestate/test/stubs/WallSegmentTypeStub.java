package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.ruleset.entities.IWallSegmentType;
import soliloquy.specs.sprites.entities.ISprite;

public class WallSegmentTypeStub implements IWallSegmentType {
    @Override
    public ISprite getSprite() {
        return null;
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
