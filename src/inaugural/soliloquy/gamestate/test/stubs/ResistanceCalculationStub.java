package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.ruleset.gameconcepts.specs.IResistanceCalculation;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilitySource;
import soliloquy.ruleset.gameentities.specs.IElement;
import soliloquy.ruleset.gameentities.specs.IStatusEffectType;
import soliloquy.ruleset.gameentities.specs.IVitalAttributeType;

public class ResistanceCalculationStub implements IResistanceCalculation {
    public ICharacter _character;
    public IStatusEffectType _statusEffectType;
    public IVitalAttributeType _vitalAttributeType;
    public int _baseAmount;
    public boolean _stopAtZero;
    public IElement _element;
    public IAbilitySource _abilitySource;

    public static final int STATUS_EFFECT_TYPE_RESULT = 123;
    public static final int VITAL_ATTRIBUTE_TYPE_RESULT = 456;

    @Override
    public int calculateEffectiveChange(ICharacter character, IStatusEffectType statusEffectType,
                                        int baseAmount, boolean stopAtZero, IElement element,
                                        IAbilitySource abilitySource)
            throws IllegalStateException, IllegalArgumentException {
        _character = character;
        _statusEffectType = statusEffectType;
        _baseAmount = baseAmount;
        _stopAtZero = stopAtZero;
        _element = element;
        _abilitySource = abilitySource;
        return STATUS_EFFECT_TYPE_RESULT;
    }

    @Override
    public int calculateEffectiveChange(ICharacter character, IVitalAttributeType vitalAttributeType,
                                        int baseAmount, boolean stopAtZero, IElement element,
                                        IAbilitySource abilitySource)
            throws IllegalStateException, IllegalArgumentException {
        _character = character;
        _vitalAttributeType = vitalAttributeType;
        _baseAmount = baseAmount;
        _stopAtZero = stopAtZero;
        _element = element;
        _abilitySource = abilitySource;
        return VITAL_ATTRIBUTE_TYPE_RESULT;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
