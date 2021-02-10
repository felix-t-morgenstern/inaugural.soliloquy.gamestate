package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.graphics.assets.AssetSnippet;
import soliloquy.specs.graphics.assets.SpriteSet;

public class FakeSpriteSet implements SpriteSet {
    private final String ID;

    public FakeSpriteSet() {
        ID = null;
    }

    public FakeSpriteSet(String id) {
        ID = id;
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public AssetSnippet getImageAndBoundariesForTypeAndDirection(String s, String s1) throws IllegalArgumentException {
        return null;
    }
}
