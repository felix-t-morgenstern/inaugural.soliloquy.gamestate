package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterStatusEffects;
import soliloquy.gamestate.specs.ICharacterStatusEffectsFactory;

public class CharacterStatusEffectsFactoryStub implements ICharacterStatusEffectsFactory {
    @Override
    public ICharacterStatusEffects make(ICharacter iCharacter) throws IllegalArgumentException {
        return new CharacterStatusEffectsStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
