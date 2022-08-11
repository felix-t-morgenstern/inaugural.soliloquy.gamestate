package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterEquipmentSlotsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;

public class CharacterEquipmentSlotsFactoryImpl implements CharacterEquipmentSlotsFactory {
    private final MapFactory MAP_FACTORY;

    public CharacterEquipmentSlotsFactoryImpl(MapFactory mapFactory) {
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public CharacterEquipmentSlots make(Character character) throws IllegalArgumentException {
        return new CharacterEquipmentSlotsImpl(
                Check.ifNull(character, "character"), MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEquipmentSlotsFactory.class.getCanonicalName();
    }
}
