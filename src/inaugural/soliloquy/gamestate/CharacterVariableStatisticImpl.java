package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterVariableStatisticImpl
        extends AbstractCharacterStatistic<CharacterVariableStatisticType>
        implements CharacterVariableStatistic {
    private int _currentValue;

    public CharacterVariableStatisticImpl(Character character,
                                          CharacterVariableStatisticType type,
                                          VariableCache data,
                                          CharacterStatisticCalculation
                                                    vitalAttributeCalculation) {
        super(character, type, data, vitalAttributeCalculation);
    }

    @Override
    public int getCurrentValue() throws IllegalStateException {
        enforceDeletionInvariants("getCurrentValue");
        return _currentValue;
    }

    @Override
    public void setCurrentValue(int currentValue)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariants("setCurrentValue");
        _currentValue = currentValue;
    }

    @Override
    protected String className() {
        return "CharacterVariableStatistic";
    }
}
