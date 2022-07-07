package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.stubs.SpriteStub;
import soliloquy.specs.graphics.assets.Sprite;

public class FakeSpriteHandler extends FakeTypeHandler<Sprite> {
    @Override
    public String typeName() {
        return "Sprite";
    }

    @Override
    protected Sprite generateInstance() {
        return new SpriteStub();
    }
}
