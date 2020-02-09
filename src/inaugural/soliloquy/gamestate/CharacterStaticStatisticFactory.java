package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterStaticStatisticFactory
        implements CharacterEntityOfTypeFactory<CharacterStaticStatisticType,
        CharacterStatistic<CharacterStaticStatisticType>> {
    private final CharacterStatisticCalculation CALCULATION;

    @SuppressWarnings("ConstantConditions")
    public CharacterStaticStatisticFactory(CharacterStatisticCalculation calculation) {
        if (calculation == null) {
            throw new IllegalArgumentException(
                    "CharacterStaticStatisticFactory: calculation cannot be null");
        }
        CALCULATION = calculation;
    }

    @Override
    public CharacterStatistic<CharacterStaticStatisticType> make(Character character,
                                                                 CharacterStaticStatisticType type)
            throws IllegalArgumentException {
        return new CharacterStatisticImpl<>(character, type, CALCULATION);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterStaticStatisticType.class.getCanonicalName() + "," +
                CharacterStatistic.class.getCanonicalName() + "<" +
                CharacterStaticStatisticType.class.getCanonicalName() + ">>";
    }
}
