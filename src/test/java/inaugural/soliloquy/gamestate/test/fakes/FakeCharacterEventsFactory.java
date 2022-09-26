package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

public class FakeCharacterEventsFactory implements CharacterEventsFactory {
    @Override
    public CharacterEvents make(Character character) throws IllegalArgumentException {
        return new FakeCharacterEvents();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
