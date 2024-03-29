package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterStatusEffectsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;

public class CharacterStatusEffectsFactoryImpl implements CharacterStatusEffectsFactory {
    @Override
    public CharacterStatusEffects make(Character character) throws IllegalArgumentException {
        return new CharacterStatusEffectsImpl(Check.ifNull(character, "character"));
    }

    @Override
    public String getInterfaceName() {
        return CharacterStatusEffectsFactory.class.getCanonicalName();
    }
}
