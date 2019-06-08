package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPair;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterVitalAttribute;
import soliloquy.ruleset.gameconcepts.specs.IVitalAttributeCalculation;
import soliloquy.ruleset.gameentities.specs.IVitalAttributeType;

public class CharacterVitalAttribute extends HasDeletionInvariants
        implements ICharacterVitalAttribute {
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
        enforceDeletionInvariants("vitalAttributeType");
        return VITAL_ATTRIBUTE_TYPE;
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
    public ICharacter character() throws IllegalStateException {
        enforceDeletionInvariants("character");
        return CHARACTER;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        enforceDeletionInvariants("totalValue");
        return _totalValue;
    }

    @Override
    public IMap<String, Integer> modifiers() throws IllegalStateException {
        enforceDeletionInvariants("modifiers");
        return _modifiers;
    }

    @Override
    public void calculateValue() throws IllegalStateException {
        enforceDeletionInvariants("calculateValue");
        IPair<Integer,IMap<String,Integer>> calculatedValueAndModifiers =
                VITAL_ATTRIBUTE_CALCULATION.calculateVitalAttributeMaxValue(
                        CHARACTER, VITAL_ATTRIBUTE_TYPE);
        _totalValue = calculatedValueAndModifiers.getItem1();
        _modifiers = calculatedValueAndModifiers.getItem2();
    }

    @Override
    public String getInterfaceName() throws IllegalStateException {
        enforceDeletionInvariants("getInterfaceName");
        return ICharacterVitalAttribute.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "CharacterVitalAttribute";
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return CHARACTER.isDeleted();
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        _isDeleted = true;
    }
}
