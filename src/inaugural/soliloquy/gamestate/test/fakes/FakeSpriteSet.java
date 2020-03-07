package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.sprites.entities.Sprite;
import soliloquy.specs.sprites.entities.SpriteSet;

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
