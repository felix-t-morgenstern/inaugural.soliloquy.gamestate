package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterInventory;
import soliloquy.gamestate.specs.IItem;

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
    public ICollection<IItem> getRepresentation() throws IllegalStateException {
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
