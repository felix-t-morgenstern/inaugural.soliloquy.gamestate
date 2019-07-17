package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;

public class CharacterEquipmentSlotsFactoryStub implements CharacterEquipmentSlotsFactory {
    public static final CharacterEquipmentSlots CHARACTER_EQUIPMENT_SLOTS =
            new CharacterEquipmentSlotsStub(null);

    @Override
    public CharacterEquipmentSlots make(Character character) throws IllegalArgumentException {
        return CHARACTER_EQUIPMENT_SLOTS;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
