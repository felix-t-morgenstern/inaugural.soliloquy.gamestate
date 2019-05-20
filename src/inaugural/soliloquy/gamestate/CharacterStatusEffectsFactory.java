package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterStatusEffects;
import soliloquy.gamestate.specs.ICharacterStatusEffectsFactory;
import soliloquy.ruleset.gameconcepts.specs.IResistanceCalculation;
import soliloquy.ruleset.gameentities.specs.IStatusEffectType;

public class CharacterStatusEffectsFactory implements ICharacterStatusEffectsFactory {
    private final IMap<String, IStatusEffectType> STATUS_EFFECT_TYPES;
    private final IMapFactory MAP_FACTORY;
    private final IResistanceCalculation RESISTANCE_CALCULATION;

    public CharacterStatusEffectsFactory(IMap<String, IStatusEffectType> statusEffectTypes,
                                         IMapFactory mapFactory,
                                         IResistanceCalculation resistanceCalculation) {
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
    public ICharacterStatusEffects make(ICharacter character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterStatusEffectsFactory.make: character must be non-null");
        }
        return new CharacterStatusEffects(
                character,
                STATUS_EFFECT_TYPES,
                MAP_FACTORY,
                RESISTANCE_CALCULATION);
    }

    @Override
    public String getInterfaceName() {
        return ICharacterStatusEffectsFactory.class.getCanonicalName();
    }
}
