package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterEquipmentSlots;
import soliloquy.gamestate.specs.ICharacterEquipmentSlotsFactory;

public class CharacterEquipmentSlotsFactoryStub implements ICharacterEquipmentSlotsFactory {
    public static final ICharacterEquipmentSlots CHARACTER_EQUIPMENT_SLOTS =
            new CharacterEquipmentSlotsStub(null);

    @Override
    public ICharacterEquipmentSlots make(ICharacter character) throws IllegalArgumentException {
        return CHARACTER_EQUIPMENT_SLOTS;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
