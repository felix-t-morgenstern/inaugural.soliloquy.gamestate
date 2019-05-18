package inaugural.soliloquy.gamestate;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterEquipmentSlots;
import soliloquy.gamestate.specs.ICharacterEquipmentSlotsFactory;

public class CharacterEquipmentSlotsFactory implements ICharacterEquipmentSlotsFactory {
    @Override
    public ICharacterEquipmentSlots make(ICharacter character) throws IllegalArgumentException {
        return new CharacterEquipmentSlots(character);
    }

    @Override
    public String getInterfaceName() {
        return ICharacterEquipmentSlotsFactory.class.getCanonicalName();
    }
}
