package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

import java.util.function.Predicate;

public class CharacterInventoryFactoryImpl implements CharacterInventoryFactory {
    private final CollectionFactory COLLECTION_FACTORY;
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE;

    @SuppressWarnings("ConstantConditions")
    public CharacterInventoryFactoryImpl(CollectionFactory collectionFactory,
                                         Predicate<Item> itemIsPresentElsewhere) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory: collectionFactory must not be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (itemIsPresentElsewhere == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory: itemIsPresentElsewhere must not be null");
        }
        ITEM_IS_PRESENT_ELSEWHERE = itemIsPresentElsewhere;
    }

    @Override
    public CharacterInventory make(Character character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory.make: character must not be null");
        }
        return new CharacterInventoryImpl(character, COLLECTION_FACTORY,
                ITEM_IS_PRESENT_ELSEWHERE);
    }

    @Override
    public String getInterfaceName() {
        return CharacterInventoryFactory.class.getCanonicalName();
    }
}
