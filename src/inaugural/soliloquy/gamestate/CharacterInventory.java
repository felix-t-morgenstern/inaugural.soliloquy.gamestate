package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.infrastructure.IReadOnlyCollection;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterInventory;
import soliloquy.specs.gamestate.entities.IItem;

public class CharacterInventory implements ICharacterInventory {
    // TODO: Implement and test this class (currently implemented as a shell for CharacterInventoryFactory)

    public CharacterInventory(ICharacter character, ICollectionFactory collectionFactory) {

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
    public IReadOnlyCollection<IItem> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void add(IItem iItem) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean remove(IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean contains(IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }
}
