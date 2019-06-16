package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterInventory;
import soliloquy.specs.gamestate.entities.IItem;

public class CharacterInventoryStub implements ICharacterInventory {
    public final ICharacter CHARACTER;

    public boolean _isDeleted;

    CharacterInventoryStub(ICharacter character) {
        CHARACTER = character;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
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
