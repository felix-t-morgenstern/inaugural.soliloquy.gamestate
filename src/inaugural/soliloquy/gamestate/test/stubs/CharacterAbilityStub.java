package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.ICharacterAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilityType;

public class CharacterAbilityStub<TAbilityType extends IAbilityType> implements ICharacterAbility {
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
