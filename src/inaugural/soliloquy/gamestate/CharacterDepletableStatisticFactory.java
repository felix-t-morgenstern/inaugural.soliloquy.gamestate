package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterDepletableStatisticFactory
        implements CharacterEntityOfTypeFactory<CharacterDepletableStatisticType,
        CharacterDepletableStatistic> {
    private final CharacterStatisticCalculation CALCULATION;

    @SuppressWarnings("ConstantConditions")
    public CharacterDepletableStatisticFactory(CharacterStatisticCalculation calculation) {
        if (calculation == null) {
            throw new IllegalArgumentException(
                    "CharacterDepletableStatisticFactory: calculation cannot be null");
        }
        CALCULATION = calculation;
    }

    @Override
    public CharacterDepletableStatistic make(Character character,
                                             CharacterDepletableStatisticType type)
            throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterDepletableStatisticFactory.make: character cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterDepletableStatisticFactory.make: type cannot be null");
        }
        return new CharacterDepletableStatisticImpl(character, type, CALCULATION);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                CharacterDepletableStatisticType.class.getCanonicalName() + "," +
                CharacterDepletableStatistic.class.getCanonicalName() + ">";
    }
}
