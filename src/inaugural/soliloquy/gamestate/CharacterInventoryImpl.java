package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;

public class CharacterInventoryImpl implements CharacterInventory {
    // TODO: Implement and test this class (currently implemented as a shell for CharacterInventoryFactory)

    public CharacterInventoryImpl(Character character, CollectionFactory collectionFactory) {

    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public ReadableCollection<Item> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void add(Item iItem) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean remove(Item iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean contains(Item iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }
}
