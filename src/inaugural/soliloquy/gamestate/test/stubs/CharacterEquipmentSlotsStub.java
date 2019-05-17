package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterEquipmentSlot;
import soliloquy.gamestate.specs.ICharacterEquipmentSlots;

public class CharacterEquipmentSlotsStub implements ICharacterEquipmentSlots {
    public final ICharacter CHARACTER;

    public CharacterEquipmentSlotsStub(ICharacter character) {
        CHARACTER = character;
    }

    @Override
    public ICollection<ICharacterEquipmentSlot> getCharacterEquipmentSlotsRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void addCharacterEquipmentSlot(ICharacterEquipmentSlot iCharacterEquipmentSlot) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean removeCharacterEquipmentSlot(ICharacterEquipmentSlot iCharacterEquipmentSlot) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean containsCharacterEquipmentSlot(ICharacterEquipmentSlot iCharacterEquipmentSlot) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }
}
