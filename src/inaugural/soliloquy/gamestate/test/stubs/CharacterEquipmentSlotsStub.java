package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterEquipmentSlots;
import soliloquy.specs.gamestate.entities.IItem;

public class CharacterEquipmentSlotsStub implements ICharacterEquipmentSlots {
    public final ICharacter CHARACTER;

    public boolean _isDeleted;

    CharacterEquipmentSlotsStub(ICharacter character) {
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
    public IMap<String, IItem> getRepresentation() throws IllegalStateException {
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
    public IItem removeCharacterEquipmentSlot(String s) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public IItem itemInSlot(String s) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public boolean canEquipItemToSlot(String s, IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public IItem equipItemToSlot(String s, IItem iItem) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
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
