package inaugural.soliloquy.gamestate;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilityType;

public class CharacterAbility extends HasDeletionInvariants implements ICharacterAbility {
    private boolean _isHidden;
    private boolean _isDisabled;

    private final ICharacter CHARACTER;
    private final IAbilityType ABILITY_TYPE;

    public CharacterAbility(boolean isHidden, boolean isDisabled, ICharacter character,
                            IAbilityType abilityType){
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
    public IAbilityType abilityType() {
        enforceDeletionInvariants("abilityType");
        return ABILITY_TYPE;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return ICharacterAbility.class.getCanonicalName();
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
