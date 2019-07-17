package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityImpl extends HasDeletionInvariants implements CharacterAbility {
    private boolean _isHidden;
    private boolean _isDisabled;

    private final Character CHARACTER;
    private final AbilityType ABILITY_TYPE;

    public CharacterAbilityImpl(boolean isHidden, boolean isDisabled, Character character,
                                AbilityType abilityType){
        _isHidden = isHidden;
        _isDisabled = isDisabled;
        CHARACTER = character;
        ABILITY_TYPE = abilityType;
    }

    @Override
    public boolean getIsHidden() {
        enforceDeletionInvariants("getIsHidden");
        return _isHidden;
    }

    @Override
    public void setIsHidden(boolean isHidden) {
        enforceDeletionInvariants("setIsHidden");
        _isHidden = isHidden;
    }

    @Override
    public boolean getIsDisabled() {
        enforceDeletionInvariants("getIsDisabled");
        return _isDisabled;
    }

    @Override
    public void setIsDisabled(boolean isDisabled) {
        enforceDeletionInvariants("setIsDisabled");
        _isDisabled = isDisabled;
    }

    @Override
    public AbilityType abilityType() {
        enforceDeletionInvariants("abilityType");
        return ABILITY_TYPE;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return CharacterAbility.class.getCanonicalName();
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        _isDeleted = true;
    }

    @Override
    protected String className() {
        return "CharacterAbility";
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return CHARACTER.isDeleted();
    }
}
