package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterInventory;
import soliloquy.gamestate.specs.ICharacterInventoryFactory;

public class CharacterInventoryFactoryStub implements ICharacterInventoryFactory {
    @Override
    public ICharacterInventory make(ICharacter character) {
        return new CharacterInventoryStub(character);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
