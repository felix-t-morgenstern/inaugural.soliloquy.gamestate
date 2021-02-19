package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterInventoryImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

public class CharacterInventoryFactoryImpl implements CharacterInventoryFactory {
    private final ListFactory LIST_FACTORY;

    public CharacterInventoryFactoryImpl(ListFactory listFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    @Override
    public CharacterInventory make(Character character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory.make: character must not be null");
        }
        return new CharacterInventoryImpl(character, LIST_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return CharacterInventoryFactory.class.getCanonicalName();
    }
}
