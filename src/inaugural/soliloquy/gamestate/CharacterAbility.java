package inaugural.soliloquy.gamestate;

import soliloquy.gamestate.specs.ICharacterAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilityType;

public class CharacterAbility implements ICharacterAbility {
    private boolean _isHidden;
    private boolean _isDisabled;

    private final IAbilityType ABILITY_TYPE;

    public CharacterAbility(boolean isHidden, boolean isDisabled, IAbilityType abilityType){
        _isHidden = isHidden;
        _isDisabled = isDisabled;
        ABILITY_TYPE = abilityType;
    }

    @Override
    public boolean getIsHidden() {
        return _isHidden;
    }

    @Override
    public void setIsHidden(boolean isHidden) {
        _isHidden = isHidden;
    }

    @Override
    public boolean getIsDisabled() {
        return _isDisabled;
    }

    @Override
    public void setIsDisabled(boolean isDisabled) {
        _isDisabled = isDisabled;
    }

    @Override
    public IAbilityType abilityType() {
        return ABILITY_TYPE;
    }

    @Override
    public String getInterfaceName() {
        return ICharacterAbility.class.getCanonicalName();
    }
}
