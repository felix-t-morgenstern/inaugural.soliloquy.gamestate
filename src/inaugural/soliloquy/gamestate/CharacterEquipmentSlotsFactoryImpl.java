package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.CharacterEquipmentSlotsFactory;

import java.util.function.Predicate;

public class CharacterEquipmentSlotsFactoryImpl implements CharacterEquipmentSlotsFactory {
    private final PairFactory PAIR_FACTORY;
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public CharacterEquipmentSlotsFactoryImpl(PairFactory pairFactory, MapFactory mapFactory) {
        if (pairFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlotsFactory: pairFactory must not be null");
        }
        PAIR_FACTORY = pairFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlotsFactory: mapFactory must not be null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public CharacterEquipmentSlots make(Character character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlotsFactory.make: character must not be null");
        }
        return new CharacterEquipmentSlotsImpl(character, PAIR_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEquipmentSlotsFactory.class.getCanonicalName();
    }
}
