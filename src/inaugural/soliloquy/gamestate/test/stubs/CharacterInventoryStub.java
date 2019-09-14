package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;

import java.util.ArrayList;
import java.util.List;

public class CharacterInventoryStub implements CharacterInventory {
    public static Boolean OVERRIDE_CONTAINS;
    public final Character CHARACTER;
    public static List<Item> ITEMS = new ArrayList<>();

    public boolean _isDeleted;

    public CharacterInventoryStub(Character character) {
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
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        ITEMS.add(item);
        item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(CHARACTER);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        boolean itemPresent = ITEMS.contains(item);
        if (itemPresent) {
            item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(null);
        }
        return ITEMS.remove(item);
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        if (OVERRIDE_CONTAINS != null) {
            return OVERRIDE_CONTAINS;
        }
        return ITEMS.contains(item);
    }
}
