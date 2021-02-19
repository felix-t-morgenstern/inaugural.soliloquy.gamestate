package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterEquipmentSlotsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;

public class CharacterEquipmentSlotsFactoryImpl implements CharacterEquipmentSlotsFactory {
    private final PairFactory PAIR_FACTORY;
    private final MapFactory MAP_FACTORY;

    public CharacterEquipmentSlotsFactoryImpl(PairFactory pairFactory, MapFactory mapFactory) {
        PAIR_FACTORY = Check.ifNull(pairFactory, "pairFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public CharacterEquipmentSlots make(Character character) throws IllegalArgumentException {
        return new CharacterEquipmentSlotsImpl(
                Check.ifNull(character, "character"),
                PAIR_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEquipmentSlotsFactory.class.getCanonicalName();
    }
}
