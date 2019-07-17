package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

public class CharacterInventoryFactoryStub implements CharacterInventoryFactory {
    @Override
    public CharacterInventory make(Character character) {
        return new CharacterInventoryStub(character);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
