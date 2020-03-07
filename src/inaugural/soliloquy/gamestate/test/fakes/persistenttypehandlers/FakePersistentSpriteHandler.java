package inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.SpriteStub;
import soliloquy.specs.sprites.entities.Sprite;

public class FakePersistentSpriteHandler extends FakePersistentValueTypeHandler<Sprite> {
    @Override
    public String typeName() {
        return "Sprite";
    }

    @Override
    protected Sprite generateInstance() {
        return new SpriteStub();
    }
}
