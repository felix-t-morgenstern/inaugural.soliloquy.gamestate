package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterVariableStatisticFactory
        implements CharacterEntityOfTypeFactory<CharacterVariableStatisticType,
        CharacterVariableStatistic> {
    private final CharacterStatisticCalculation CALCULATION;

    @SuppressWarnings("ConstantConditions")
    public CharacterVariableStatisticFactory(CharacterStatisticCalculation calculation) {
        if (calculation == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticFactory: calculation cannot be null");
        }
        CALCULATION = calculation;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public CharacterVariableStatistic make(Character character,
                                             CharacterVariableStatisticType type)
            throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticFactory.make: character cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterVariableStatisticFactory.make: type cannot be null");
        }
        return new CharacterVariableStatisticImpl(character, type, CALCULATION);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterVariableStatisticType.class.getCanonicalName() + "," +
                CharacterVariableStatistic.class.getCanonicalName() + ">";
    }
}
