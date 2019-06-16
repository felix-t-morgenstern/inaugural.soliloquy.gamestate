package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterStatusEffects;
import soliloquy.specs.gamestate.factories.ICharacterStatusEffectsFactory;

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
