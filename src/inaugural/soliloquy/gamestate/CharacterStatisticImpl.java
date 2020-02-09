package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.ruleset.entities.CharacterStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterStatisticImpl<TCharacterStatisticType extends CharacterStatisticType>
        extends AbstractCharacterValueFromModifiers<TCharacterStatisticType>
        implements CharacterStatistic<TCharacterStatisticType> {
    @SuppressWarnings("ConstantConditions")
    public CharacterStatisticImpl(Character character, TCharacterStatisticType type,
                                  CharacterStatisticCalculation characterStatisticCalculation) {
        super(character, type, characterStatisticCalculation);
    }

    @Override
    protected String className() {
        return "CharacterStatisticImpl";
    }

    @Override
    public TCharacterStatisticType type() {
        enforceDeletionInvariants("type");
        return ENTITY_TYPE;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return soliloquy.specs.gamestate.entities.CharacterStatistic.class.getCanonicalName();
    }
}
