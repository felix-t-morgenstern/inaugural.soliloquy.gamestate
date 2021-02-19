package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterEventsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

public class CharacterEventsFactoryImpl implements CharacterEventsFactory {
    private final ListFactory LIST_FACTORY;
    private final MapFactory MAP_FACTORY;

    public CharacterEventsFactoryImpl(ListFactory listFactory, MapFactory mapFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public CharacterEvents make(Character character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterEventsFactoryImpl.make: character cannot be null");
        }
        return new CharacterEventsImpl(character, LIST_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEventsFactory.class.getCanonicalName();
    }
}
