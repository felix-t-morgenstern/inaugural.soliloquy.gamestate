package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CharacterInventoryImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

public class CharacterInventoryFactoryImpl implements CharacterInventoryFactory {
    @Override
    public CharacterInventory make(Character character) {
        return new CharacterInventoryImpl(Check.ifNull(character, "character"));
    }

    @Override
    public String getInterfaceName() {
        return CharacterInventoryFactory.class.getCanonicalName();
    }
}
