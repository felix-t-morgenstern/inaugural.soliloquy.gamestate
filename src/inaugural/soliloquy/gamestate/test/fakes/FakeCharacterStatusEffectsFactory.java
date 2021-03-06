package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;

public class FakeCharacterStatusEffectsFactory implements CharacterStatusEffectsFactory {
    @Override
    public CharacterStatusEffects make(Character character) throws IllegalArgumentException {
        return new FakeCharacterStatusEffects();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
