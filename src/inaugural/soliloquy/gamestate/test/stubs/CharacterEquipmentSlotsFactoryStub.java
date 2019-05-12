package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterEquipmentSlots;
import soliloquy.gamestate.specs.ICharacterEquipmentSlotsFactory;

public class CharacterEquipmentSlotsFactoryStub implements ICharacterEquipmentSlotsFactory {
    @Override
    public ICharacterEquipmentSlots make(ICharacter character) throws IllegalArgumentException {
        return new CharacterEquipmentSlotsStub(character);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
