package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterInventory;
import soliloquy.specs.gamestate.factories.ICharacterInventoryFactory;

public class CharacterInventoryFactory implements ICharacterInventoryFactory {
    private final ICollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterInventoryFactory(ICollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory: collectionFactory must not be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public ICharacterInventory make(ICharacter character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory.make: character must not be null");
        }
        return new CharacterInventory(character, COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ICharacterInventoryFactory.class.getCanonicalName();
    }
}
