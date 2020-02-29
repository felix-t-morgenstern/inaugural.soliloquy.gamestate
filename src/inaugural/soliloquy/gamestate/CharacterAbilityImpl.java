package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityImpl<TType extends AbilityType> extends HasDeletionInvariants
        implements CharacterEntityOfType<TType> {
    private final Character CHARACTER;
    private final TType ABILITY_TYPE;
    private final VariableCache DATA;

    public CharacterAbilityImpl(Character character, TType abilityType, VariableCache data){
        CHARACTER = Check.ifNull(character, "character");
        ABILITY_TYPE = Check.ifNull(abilityType, "abilityType");
        DATA = Check.ifNull(data, "data");
    }

    @Override
    public TType type() {
        enforceDeletionInvariants("abilityType");
        return ABILITY_TYPE;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return DATA;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return CharacterEntityOfType.class.getCanonicalName() + "<" +
                ABILITY_TYPE.getInterfaceName() + ">";
    }

    @Override
    protected String className() {
        return "CharacterEntityOfType";
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
