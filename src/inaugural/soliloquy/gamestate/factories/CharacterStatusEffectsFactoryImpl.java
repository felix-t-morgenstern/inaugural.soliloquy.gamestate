package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterStatusEffectsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;
import soliloquy.specs.ruleset.gameconcepts.StatusEffectResistanceCalculation;

public class CharacterStatusEffectsFactoryImpl implements CharacterStatusEffectsFactory {
    private final StatusEffectResistanceCalculation RESISTANCE_CALCULATION;

    @SuppressWarnings("ConstantConditions")
    public CharacterStatusEffectsFactoryImpl(StatusEffectResistanceCalculation
                                                     resistanceCalculation) {
        RESISTANCE_CALCULATION = Check.ifNull(resistanceCalculation, "resistanceCalculation");
    }

    @Override
    public CharacterStatusEffects make(Character character) throws IllegalArgumentException {
        return new CharacterStatusEffectsImpl(Check.ifNull(character, "character"),
                RESISTANCE_CALCULATION);
    }

    @Override
    public String getInterfaceName() {
        return CharacterStatusEffectsFactory.class.getCanonicalName();
    }
}
