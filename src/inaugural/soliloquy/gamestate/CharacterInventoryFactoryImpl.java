package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

public class CharacterInventoryFactoryImpl implements CharacterInventoryFactory {
    private final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterInventoryFactoryImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory: collectionFactory must not be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public CharacterInventory make(Character character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory.make: character must not be null");
        }
        return new CharacterInventoryImpl(character, COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterInventoryFactory.class.getCanonicalName();
    }
}
