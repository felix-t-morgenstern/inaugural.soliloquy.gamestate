package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.sprites.entities.SpriteSet;

public class CharacterStaticStatisticTypeArchetype implements CharacterStaticStatisticType {
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
        return CharacterStaticStatisticType.class.getCanonicalName();
    }
}
