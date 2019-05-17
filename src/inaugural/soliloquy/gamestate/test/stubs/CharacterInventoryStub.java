package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterInventory;
import soliloquy.gamestate.specs.IItem;

public class CharacterInventoryStub implements ICharacterInventory {
    public final ICharacter CHARACTER;

    public CharacterInventoryStub(ICharacter character) {
        CHARACTER = character;
    }

    @Override
    public ICollection<IItem> getInventoryRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void addItemToInventory(IItem iItem) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean removeItemFromInventory(IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean containsItem(IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
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
}
