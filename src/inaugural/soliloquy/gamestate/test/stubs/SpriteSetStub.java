package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.sprites.entities.Sprite;
import soliloquy.specs.sprites.entities.SpriteSet;

public class SpriteSetStub implements SpriteSet {
    private final String ID;

    public SpriteSetStub() {
        ID = null;
    }

    public SpriteSetStub(String id) {
        ID = id;
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public Sprite getSprite(String s) {
        return null;
    }

    @Override
    public Sprite getSprite(String s, String s1) {
        return null;
    }

    @Override
    public Map<String, Integer> graphicalTransformations() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
