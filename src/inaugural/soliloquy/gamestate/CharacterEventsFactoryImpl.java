package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

public class CharacterEventsFactoryImpl implements CharacterEventsFactory {
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterEventsFactoryImpl(CollectionFactory collectionFactory, MapFactory mapFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEventsFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEventsFactoryImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public CharacterEvents make(Character character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterEventsFactoryImpl.make: character cannot be null");
        }
        return new CharacterEventsImpl(character, COLLECTION_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEventsFactory.class.getCanonicalName();
    }
}
