package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;

import java.util.Iterator;
import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public class FakeCharacterInventory implements CharacterInventory {
    public static Boolean OVERRIDE_CONTAINS;
    public final Character CHARACTER;
    public final java.util.List<Item> ITEMS = listOf();

    public boolean _isDeleted;

    FakeCharacterInventory(Character character) {
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
    public List<Item> representation() throws IllegalStateException {
        return listOf(ITEMS);
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        ITEMS.add(item);
        item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        boolean itemPresent = ITEMS.contains(item);
        if (itemPresent) {
            item.assignInventoryCharacterAfterAddedToCharacterInventory(null);
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

    @Override
    public Iterator<Item> iterator() {
        return ITEMS.iterator();
    }
}
