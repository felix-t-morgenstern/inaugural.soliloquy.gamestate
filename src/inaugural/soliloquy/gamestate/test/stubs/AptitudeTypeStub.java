package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.ruleset.entities.AptitudeType;
import soliloquy.specs.sprites.entities.SpriteSet;

public class AptitudeTypeStub implements AptitudeType {
    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String s) {

    }

    @Override
    public SpriteSet getSpriteSet() {
        return null;
    }

    @Override
    public void setSpriteSet(SpriteSet spriteSet) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
