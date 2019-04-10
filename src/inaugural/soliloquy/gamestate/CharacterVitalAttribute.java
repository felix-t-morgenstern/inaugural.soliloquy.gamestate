package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPair;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterVitalAttribute;
import soliloquy.ruleset.gameconcepts.specs.IVitalAttributeCalculation;
import soliloquy.ruleset.gameentities.specs.IVitalAttributeType;

public class CharacterVitalAttribute implements ICharacterVitalAttribute {
    private final ICharacter CHARACTER;
    private final IVitalAttributeType VITAL_ATTRIBUTE_TYPE;
    private final IVitalAttributeCalculation VITAL_ATTRIBUTE_CALCULATION;

    private int _currentValue;
    private int _totalValue;
    private IMap<String,Integer> _modifiers;

    public CharacterVitalAttribute(ICharacter character, IVitalAttributeType vitalAttributeType,
                                   IVitalAttributeCalculation vitalAttributeCalculation) {
        CHARACTER = character;
        VITAL_ATTRIBUTE_TYPE = vitalAttributeType;
        VITAL_ATTRIBUTE_CALCULATION = vitalAttributeCalculation;
    }

    @Override
    public IVitalAttributeType vitalAttributeType() throws IllegalStateException {
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterVitalAttribute.vitalAttributeType: character is deleted");
        }
        return VITAL_ATTRIBUTE_TYPE;
    }

    @Override
    public int getCurrentValue() throws IllegalStateException {
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterVitalAttribute.getCurrentValue: character is deleted");
        }
        return _currentValue;
    }

    @Override
    public void setCurrentValue(int currentValue) throws IllegalStateException, IllegalArgumentException {
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterVitalAttribute.setCurrentValue: character is deleted");
        }
        _currentValue = currentValue;
    }

    @Override
    public ICharacter character() throws IllegalStateException {
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterVitalAttribute.character: character is deleted");
        }
        return CHARACTER;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterVitalAttribute.totalValue: character is deleted");
        }
        return _totalValue;
    }

    @Override
    public IMap<String, Integer> modifiers() throws IllegalStateException {
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterVitalAttribute.modifiers: character is deleted");
        }
        return _modifiers;
    }

    @Override
    public void calculateValue() throws IllegalStateException {
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterVitalAttribute.calculateValue: character is deleted");
        }
        IPair<Integer,IMap<String,Integer>> calculatedValueAndModifiers =
                VITAL_ATTRIBUTE_CALCULATION.calculateVitalAttributeMaxValue(
                        CHARACTER, VITAL_ATTRIBUTE_TYPE);
        _totalValue = calculatedValueAndModifiers.getItem1();
        _modifiers = calculatedValueAndModifiers.getItem2();
    }

    @Override
    public String getInterfaceName() throws IllegalStateException {
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException(
                    "CharacterVitalAttribute.getInterfaceName: character is deleted");
        }
        return "soliloquy.gamestate.specs.ICharacterVitalAttribute";
    }
}
