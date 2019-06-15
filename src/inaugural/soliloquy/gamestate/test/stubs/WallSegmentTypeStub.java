package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.ruleset.gameentities.specs.IWallSegmentType;
import soliloquy.sprites.specs.ISprite;

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
