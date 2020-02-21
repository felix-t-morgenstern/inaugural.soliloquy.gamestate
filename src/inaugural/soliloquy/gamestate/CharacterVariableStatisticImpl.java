package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterVariableStatisticImpl
        extends AbstractCharacterValueFromModifiers<CharacterVariableStatisticType>
        implements CharacterVariableStatistic {
    private int _currentValue;

    public CharacterVariableStatisticImpl(Character character,
                                          CharacterVariableStatisticType type,
                                          CharacterStatisticCalculation
                                                    vitalAttributeCalculation) {
        super(character, type, vitalAttributeCalculation);
    }

    @Override
    public CharacterVariableStatisticType type() throws IllegalStateException {
        enforceDeletionInvariants("type");
        return ENTITY_TYPE;
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
    public String getInterfaceName() throws IllegalStateException {
        enforceDeletionInvariants("getInterfaceName");
        return CharacterVariableStatistic.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "CharacterVariableStatistic";
    }
}
