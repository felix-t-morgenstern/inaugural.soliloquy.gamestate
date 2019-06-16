package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterInventory;
import soliloquy.specs.gamestate.factories.ICharacterInventoryFactory;

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
