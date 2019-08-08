package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.gameconcepts.StatusEffectResistanceCalculation;

public class CharacterStatusEffectsFactoryImpl implements CharacterStatusEffectsFactory {
    private final Map<String, StatusEffectType> STATUS_EFFECT_TYPES;
    private final MapFactory MAP_FACTORY;
    private final StatusEffectResistanceCalculation RESISTANCE_CALCULATION;

    @SuppressWarnings("ConstantConditions")
    public CharacterStatusEffectsFactoryImpl(Map<String, StatusEffectType> statusEffectTypes,
                                             MapFactory mapFactory,
                                             StatusEffectResistanceCalculation resistanceCalculation) {
        if (statusEffectTypes == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffectsFactory: statusEffectTypes must be non-null");
        }
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffectsFactory: mapFactory must be non-null");
        }
        if (resistanceCalculation == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffectsFactory: resistanceCalculation must be non-null");
        }
        STATUS_EFFECT_TYPES = statusEffectTypes;
        MAP_FACTORY = mapFactory;
        RESISTANCE_CALCULATION = resistanceCalculation;
    }

    @Override
    public CharacterStatusEffects make(Character character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffectsFactory.make: character must be non-null");
        }
        return new CharacterStatusEffectsImpl(
                character,
                STATUS_EFFECT_TYPES,
                MAP_FACTORY,
                RESISTANCE_CALCULATION);
    }

    @Override
    public String getInterfaceName() {
        return CharacterStatusEffectsFactory.class.getCanonicalName();
    }
}
