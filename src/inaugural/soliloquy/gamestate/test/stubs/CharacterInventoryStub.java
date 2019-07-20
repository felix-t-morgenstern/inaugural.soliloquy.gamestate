package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;

public class CharacterInventoryStub implements CharacterInventory {
    public final Character CHARACTER;

    public boolean _isDeleted;

    CharacterInventoryStub(Character character) {
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
