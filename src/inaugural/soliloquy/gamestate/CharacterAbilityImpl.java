package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityImpl<TType extends AbilityType> extends HasDeletionInvariants
        implements CharacterAbility<TType> {
    private boolean _isHidden;
    private boolean _isDisabled;

    private final Character CHARACTER;
    private final TType ABILITY_TYPE;

    public CharacterAbilityImpl(boolean isHidden, boolean isDisabled, Character character,
                                TType abilityType){
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
    public TType type() {
        enforceDeletionInvariants("abilityType");
        return ABILITY_TYPE;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return CharacterAbility.class.getCanonicalName() + "<" + ABILITY_TYPE.getInterfaceName() +
                ">";
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
    protected Deletable getContainingObject() {
        return CHARACTER;
    }
}
