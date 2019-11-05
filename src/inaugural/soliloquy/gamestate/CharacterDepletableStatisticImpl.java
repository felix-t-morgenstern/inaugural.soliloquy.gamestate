package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterDepletableStatisticImpl
        extends AbstractCharacterValueFromModifiers<CharacterDepletableStatisticType>
        implements CharacterDepletableStatistic {
    private int _currentValue;

    public CharacterDepletableStatisticImpl(Character character,
                                            CharacterDepletableStatisticType type,
                                            CharacterStatisticCalculation
                                                    vitalAttributeCalculation) {
        super(character, type, vitalAttributeCalculation);
    }

    @Override
    public CharacterDepletableStatisticType type() throws IllegalStateException {
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
        return CharacterDepletableStatistic.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "CharacterVitalAttribute";
    }
}
