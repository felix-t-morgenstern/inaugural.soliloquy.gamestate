package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.sprites.entities.SpriteSet;

public class CharacterStaticStatisticTypeStub implements CharacterStaticStatisticType {
    private final String ID;

    public CharacterStaticStatisticTypeStub() {
        ID = null;
    }

    public CharacterStaticStatisticTypeStub(String id) {
        ID = id;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
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
