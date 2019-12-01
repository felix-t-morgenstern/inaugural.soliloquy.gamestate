package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

public class CharacterEventsFactoryStub implements CharacterEventsFactory {
    @Override
    public CharacterEvents make(Character character) throws IllegalArgumentException {
        return new CharacterEventsStub(character);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
