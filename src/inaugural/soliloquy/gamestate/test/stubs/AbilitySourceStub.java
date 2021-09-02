package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.abilities.AbilitySource;

public class AbilitySourceStub implements AbilitySource {
    @Override
    public Item item() throws IllegalStateException {
        return null;
    }

    @Override
    public Character character() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
