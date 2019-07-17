package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.entities.VitalAttributeType;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.gameconcepts.ResistanceCalculation;

public class ResistanceCalculationStub implements ResistanceCalculation {
    public Character _character;
    public StatusEffectType _statusEffectType;
    public int _baseAmount;
    public boolean _stopAtZero;
    public Element _element;
    public AbilitySource _abilitySource;

    public static final int STATUS_EFFECT_TYPE_RESULT = 123;
    private static final int VITAL_ATTRIBUTE_TYPE_RESULT = 456;

    @Override
    public int calculateEffectiveChange(Character character, StatusEffectType statusEffectType,
                                        int baseAmount, boolean stopAtZero, Element element,
                                        AbilitySource abilitySource)
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
    public int calculateEffectiveChange(Character character, VitalAttributeType vitalAttributeType,
                                        int baseAmount, boolean stopAtZero, Element element,
                                        AbilitySource abilitySource)
            throws IllegalStateException, IllegalArgumentException {
        _character = character;
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
