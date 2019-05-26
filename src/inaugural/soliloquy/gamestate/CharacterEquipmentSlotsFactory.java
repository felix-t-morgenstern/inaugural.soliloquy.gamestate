package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPairFactory;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterEquipmentSlots;
import soliloquy.gamestate.specs.ICharacterEquipmentSlotsFactory;

public class CharacterEquipmentSlotsFactory implements ICharacterEquipmentSlotsFactory {
    private final IPairFactory PAIR_FACTORY;
    private final IMapFactory MAP_FACTORY;

    public CharacterEquipmentSlotsFactory(IPairFactory pairFactory, IMapFactory mapFactory) {
        if (pairFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlotsFactory: pairFactory must not be null");
        }
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlotsFactory: mapFactory must not be null");
        }
        PAIR_FACTORY = pairFactory;
        MAP_FACTORY = mapFactory;
    }

    @Override
    public ICharacterEquipmentSlots make(ICharacter character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlotsFactory.make: character must not be null");
        }
        return new CharacterEquipmentSlots(character, PAIR_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ICharacterEquipmentSlotsFactory.class.getCanonicalName();
    }
}
