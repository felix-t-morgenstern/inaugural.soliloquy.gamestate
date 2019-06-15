package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.gamestate.specs.ICharacterAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbilityType;

public class CharacterReactiveAbilityArchetype implements ICharacterAbility<IReactiveAbilityType> {
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
    public IReactiveAbilityType abilityType() {
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
        return ICharacterAbility.class.getCanonicalName() + "<" +
                IReactiveAbilityType.class.getCanonicalName() + ">";
    }
}
