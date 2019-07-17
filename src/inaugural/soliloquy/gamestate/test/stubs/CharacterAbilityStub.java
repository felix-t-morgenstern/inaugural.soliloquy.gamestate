package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityStub<TAbilityType extends AbilityType> implements CharacterAbility {
    public boolean _isDeleted;

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
    public TAbilityType abilityType() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }
}
