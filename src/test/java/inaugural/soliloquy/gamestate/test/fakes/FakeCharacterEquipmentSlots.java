package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.entities.Item;

import java.util.HashMap;
import java.util.Map;

public class FakeCharacterEquipmentSlots implements CharacterEquipmentSlots {
    public final Character CHARACTER;

    public static Map<String, Item> EQUIPMENT_SLOTS = new HashMap<>();
    public static Item ITEM_IN_SLOT_RESULT_OVERRIDE = null;

    public boolean _isDeleted;

    FakeCharacterEquipmentSlots(Character character) {
        CHARACTER = character;
    }

    @Override
    public String getInterfaceName() {
        return null;
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
    public Map<String, Item> representation() throws IllegalStateException {
        return new HashMap<>(EQUIPMENT_SLOTS);
    }

    @Override
    public void addCharacterEquipmentSlot(String s)
            throws IllegalArgumentException, IllegalStateException {
        EQUIPMENT_SLOTS.put(s, null);
    }

    @Override
    public boolean equipmentSlotExists(String s)
            throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public Item removeCharacterEquipmentSlot(String s)
            throws IllegalArgumentException, IllegalStateException {
        Item item = EQUIPMENT_SLOTS.remove(s);
        if (item != null) {
            item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(null, null);
        }
        return item;
    }

    @Override
    public Item itemInSlot(String s) throws IllegalArgumentException, IllegalStateException {
        if (ITEM_IN_SLOT_RESULT_OVERRIDE != null) {
            return ITEM_IN_SLOT_RESULT_OVERRIDE;
        }
        return EQUIPMENT_SLOTS.get(s);
    }

    @Override
    public boolean canEquipItemToSlot(String s, Item item)
            throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public Item equipItemToSlot(String s, Item item)
            throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        Item originalItem = EQUIPMENT_SLOTS.get(s);
        if (originalItem != null) {
            originalItem.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(null, null);
        }
        EQUIPMENT_SLOTS.put(s, item);
        if (item != null) {
            item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(CHARACTER, s);
        }
        return originalItem;
    }

    @Override
    public boolean getCanAlterEquipmentInSlot(String s)
            throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public void setCanAlterEquipmentInSlot(String s, boolean b)
            throws IllegalArgumentException, IllegalStateException {

    }
}
