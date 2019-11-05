package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.sprites.entities.SpriteSet;

public class CharacterStatisticTypeStub implements CharacterStatisticType {
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
