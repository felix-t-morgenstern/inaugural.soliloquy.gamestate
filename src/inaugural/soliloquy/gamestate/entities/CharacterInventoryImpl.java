package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class CharacterInventoryImpl extends CanTellIfItemIsPresentElsewhere
        implements CharacterInventory {
    private final Character CHARACTER;
    private final HashSet<Item> INVENTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterInventoryImpl(Character character) {
        CHARACTER = Check.ifNull(character, "character");
        INVENTORY = new HashSet<>();
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected Deletable getContainingObject() {
        return CHARACTER;
    }

    @Override
    public List<Item> representation() throws IllegalStateException {
        enforceDeletionInvariants();
        return new ArrayList<>(INVENTORY);
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        throwOnNullOrDeletedItem("add", item);
        if (itemIsPresentElsewhere(item)) {
            throw new IllegalArgumentException(
                    "CharacterInventoryImpl.add: item is present elsewhere");
        }
        INVENTORY.add(item);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        throwOnNullOrDeletedItem("remove", item);
        return INVENTORY.remove(item);
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        throwOnNullOrDeletedItem("contains", item);
        return INVENTORY.contains(item);
    }

    private void throwOnNullOrDeletedItem(String methodName, Item item) {
        if (item == null) {
            throw new IllegalArgumentException("CharacterInventoryImpl." + methodName +
                    ": item cannot be null");
        }
        if (item.isDeleted()) {
            throw new IllegalArgumentException("CharacterInventoryImpl." + methodName +
                    ": item cannot be deleted");
        }
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        INVENTORY.forEach(Item::delete);
    }

    @Override
    public String getInterfaceName() {
        return CharacterInventory.class.getCanonicalName();
    }

    @Override
    public Iterator<Item> iterator() {
        return INVENTORY.iterator();
    }
}
