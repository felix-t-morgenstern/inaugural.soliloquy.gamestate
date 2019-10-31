package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.AttributeType;
import soliloquy.specs.sprites.entities.SpriteSet;

public class AttributeTypeStub implements AttributeType {
    @Override
    public boolean charCanAdjust(Character character, int i) throws NullPointerException {
        return false;
    }

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
