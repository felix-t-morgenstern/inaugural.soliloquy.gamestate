package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadOnlyMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVitalAttribute;
import soliloquy.specs.ruleset.entities.VitalAttributeType;
import soliloquy.specs.ruleset.gameconcepts.VitalAttributeCalculation;

public class CharacterVitalAttributeImpl extends HasDeletionInvariants
        implements CharacterVitalAttribute {
    private final Character CHARACTER;
    private final VitalAttributeType VITAL_ATTRIBUTE_TYPE;
    private final VitalAttributeCalculation VITAL_ATTRIBUTE_CALCULATION;

    private int _currentValue;
    private int _totalValue;
    private Map<String,Integer> _modifiers;

    public CharacterVitalAttributeImpl(Character character, VitalAttributeType vitalAttributeType,
                                       VitalAttributeCalculation vitalAttributeCalculation) {
        CHARACTER = character;
        VITAL_ATTRIBUTE_TYPE = vitalAttributeType;
        VITAL_ATTRIBUTE_CALCULATION = vitalAttributeCalculation;
    }

    @Override
    public VitalAttributeType vitalAttributeType() throws IllegalStateException {
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
    public Character character() throws IllegalStateException {
        enforceDeletionInvariants("character");
        return CHARACTER;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        enforceDeletionInvariants("totalValue");
        return _totalValue;
    }

    @Override
    public ReadOnlyMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
        // TODO: Test and implement whether truly read-only
        enforceDeletionInvariants("modifiers");
        return _modifiers.readOnlyRepresentation();
    }

    @Override
    public void calculateValue() throws IllegalStateException {
        enforceDeletionInvariants("calculateValue");
        Pair<Integer,Map<String,Integer>> calculatedValueAndModifiers =
                VITAL_ATTRIBUTE_CALCULATION.calculateVitalAttributeMaxValue(
                        CHARACTER, VITAL_ATTRIBUTE_TYPE);
        _totalValue = calculatedValueAndModifiers.getItem1();
        _modifiers = calculatedValueAndModifiers.getItem2();
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
