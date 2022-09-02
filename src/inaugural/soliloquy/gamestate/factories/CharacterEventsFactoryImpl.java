package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterEventsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

public class CharacterEventsFactoryImpl implements CharacterEventsFactory {
    public CharacterEventsFactoryImpl() {
    }

    @Override
    public CharacterEvents make(Character character) throws IllegalArgumentException {
        return new CharacterEventsImpl(Check.ifNull(character, "character"));
    }

    @Override
    public String getInterfaceName() {
        return CharacterEventsFactory.class.getCanonicalName();
    }
}
