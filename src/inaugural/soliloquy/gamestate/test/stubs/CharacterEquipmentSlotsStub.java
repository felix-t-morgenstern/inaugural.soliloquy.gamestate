package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.entities.Item;

public class CharacterEquipmentSlotsStub implements CharacterEquipmentSlots {
    public final Character CHARACTER;

    public boolean _isDeleted;

    CharacterEquipmentSlotsStub(Character character) {
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
    public ReadableMap<String, Item> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void addCharacterEquipmentSlot(String s) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean equipmentSlotExists(String s) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public Item removeCharacterEquipmentSlot(String s) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public Item itemInSlot(String s) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public boolean canEquipItemToSlot(String s, Item item) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public Item equipItemToSlot(String s, Item item) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        return null;
    }

    @Override
    public boolean getCanAlterEquipmentInSlot(String s) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public void setCanAlterEquipmentInSlot(String s, boolean b) throws IllegalArgumentException, IllegalStateException {

    }
}
