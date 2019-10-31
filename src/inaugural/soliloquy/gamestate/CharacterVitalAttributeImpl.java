package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVitalAttribute;
import soliloquy.specs.ruleset.entities.VitalAttributeType;
import soliloquy.specs.ruleset.gameconcepts.CharacterStatisticCalculation;

public class CharacterVitalAttributeImpl extends CharacterStatistic<VitalAttributeType>
        implements CharacterVitalAttribute {
    private int _currentValue;

    public CharacterVitalAttributeImpl(Character character, VitalAttributeType vitalAttributeType,
                                       CharacterStatisticCalculation<VitalAttributeType>
                                               vitalAttributeCalculation) {
        super(character, vitalAttributeType, vitalAttributeCalculation);
    }

    @Override
    public VitalAttributeType vitalAttributeType() throws IllegalStateException {
        enforceDeletionInvariants("vitalAttributeType");
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
        return CharacterVitalAttribute.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "CharacterVitalAttribute";
    }
}
