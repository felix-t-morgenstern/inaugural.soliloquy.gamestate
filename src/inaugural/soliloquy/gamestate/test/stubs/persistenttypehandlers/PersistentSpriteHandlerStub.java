package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.SpriteStub;
import soliloquy.specs.sprites.entities.Sprite;

public class PersistentSpriteHandlerStub extends PersistentValueTypeHandlerStub<Sprite> {
    @Override
    public String typeName() {
        return "Sprite";
    }

    @Override
    protected Sprite generateInstance() {
        return new SpriteStub();
    }
}
