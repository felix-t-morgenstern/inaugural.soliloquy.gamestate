package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;

public class CharacterActiveAbilityArchetype implements CharacterAbility<ActiveAbilityType> {
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
    public ActiveAbilityType abilityType() {
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
                ActiveAbilityType.class.getCanonicalName() + ">";
    }
}
