package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;

public class CharacterReactiveAbilityArchetype implements CharacterAbility<ReactiveAbilityType> {
    @Override
    public boolean getIsHidden() {
        return false;
    }

    @Override
    public void setIsHidden(boolean b) {

    }

    @Override
    public boolean getIsDisabled() {
        return false;
    }

    @Override
    public void setIsDisabled(boolean b) {

    }

    @Override
    public ReactiveAbilityType type() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return CharacterAbility.class.getCanonicalName() + "<" +
                ReactiveAbilityType.class.getCanonicalName() + ">";
    }
}
