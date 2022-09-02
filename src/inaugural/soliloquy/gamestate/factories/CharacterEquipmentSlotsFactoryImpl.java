package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterEquipmentSlotsImpl;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;

public class CharacterEquipmentSlotsFactoryImpl implements CharacterEquipmentSlotsFactory {
    public CharacterEquipmentSlotsFactoryImpl() {
    }

    @Override
    public CharacterEquipmentSlots make(Character character) throws IllegalArgumentException {
        return new CharacterEquipmentSlotsImpl(character);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEquipmentSlotsFactory.class.getCanonicalName();
    }
}
