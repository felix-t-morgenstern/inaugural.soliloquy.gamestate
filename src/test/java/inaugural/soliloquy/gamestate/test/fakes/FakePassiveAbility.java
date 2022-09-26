package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;

public class FakePassiveAbility implements PassiveAbility {
    public String Id;

    public FakePassiveAbility(String id) {
        Id = id;
    }

    @Override
    public String description(Character character) throws IllegalStateException {
        return null;
    }

    @Override
    public String description(Item item) throws IllegalStateException {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return Id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
