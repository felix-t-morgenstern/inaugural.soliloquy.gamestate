package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.factories.CharacterStatusEffectsFactory;

public class CharacterStatusEffectsFactoryStub implements CharacterStatusEffectsFactory {
    @Override
    public CharacterStatusEffects make(Character character) throws IllegalArgumentException {
        return new CharacterStatusEffectsStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
