package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterVitalAttribute;
import soliloquy.specs.ruleset.entities.IVitalAttributeType;
import soliloquy.specs.ruleset.gameconcepts.IVitalAttributeCalculation;

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
    public IReadOnlyMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
        // TODO: Test and implement whether truly read-only
        enforceDeletionInvariants("modifiers");
        return _modifiers.readOnlyRepresentation();
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
