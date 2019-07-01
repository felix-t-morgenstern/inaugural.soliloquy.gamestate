package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.sprites.entities.ISprite;
import soliloquy.specs.sprites.entities.ISpriteSet;

public class SpriteSetStub implements ISpriteSet {
    @Override
    public String spriteSetTypeId() {
        return null;
    }

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
