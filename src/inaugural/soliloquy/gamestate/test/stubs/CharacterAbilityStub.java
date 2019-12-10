package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityStub<TAbilityType extends AbilityType> implements CharacterAbility {
    public boolean _isDeleted;
    public boolean _isHidden;
    public TAbilityType _type;

    public CharacterAbilityStub() {
    }

    public CharacterAbilityStub(Character character, TAbilityType type) {
        _type = type;
    }

    @Override
    public boolean getIsHidden() {
        return _isHidden;
    }

    @Override
    public void setIsHidden(boolean b) {
        _isHidden = b;
    }

    @Override
    public boolean getIsDisabled() {
        return false;
    }

    @Override
    public void setIsDisabled(boolean b) {

    }

    @Override
    public TAbilityType type() {
        return _type;
    }

    @Override
    public String getInterfaceName() {
        return CharacterAbility.class.getCanonicalName() + "<" + _type.getInterfaceName() + ">";
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
