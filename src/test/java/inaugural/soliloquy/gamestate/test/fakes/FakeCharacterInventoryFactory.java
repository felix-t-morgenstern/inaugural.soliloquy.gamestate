package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.factories.CharacterInventoryFactory;

public class FakeCharacterInventoryFactory implements CharacterInventoryFactory {
    @Override
    public CharacterInventory make(Character character) {
        return new FakeCharacterInventory(character);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
