package inaugural.soliloquy.gamestate;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterInventory;
import soliloquy.gamestate.specs.ICharacterInventoryFactory;

public class CharacterInventoryFactory implements ICharacterInventoryFactory {
    @Override
    public ICharacterInventory make(ICharacter character) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterInventoryFactory: character must not be null");
        }
        return new CharacterInventory(character);
    }

    @Override
    public String getInterfaceName() {
        return ICharacterInventoryFactory.class.getCanonicalName();
    }
}
