package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.sprites.specs.ISprite;
import soliloquy.sprites.specs.ISpriteSet;

public class SpriteSetStub implements ISpriteSet {
    @Override
    public ISprite getSprite(String s) {
        return null;
    }

    @Override
    public ISprite getSprite(String s, String s1) {
        return null;
    }

    @Override
    public IMap<String, Integer> graphicalTransformations() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
