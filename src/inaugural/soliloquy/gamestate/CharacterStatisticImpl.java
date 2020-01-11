package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterStatisticImpl
        extends AbstractCharacterValueFromModifiers<CharacterStatisticType>
        implements soliloquy.specs.gamestate.entities.CharacterStatistic {
    @SuppressWarnings("ConstantConditions")
    public CharacterStatisticImpl(Character character, CharacterStatisticType attributeType,
                                  CharacterStatisticCalculation characterStatisticCalculation) {
        super(character, attributeType, characterStatisticCalculation);
    }

    @Override
    protected String className() {
        return "CharacterStatisticImpl";
    }

    @Override
    public CharacterStatisticType type() {
        enforceDeletionInvariants("type");
        return ENTITY_TYPE;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return soliloquy.specs.gamestate.entities.CharacterStatistic.class.getCanonicalName();
    }
}
