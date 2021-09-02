package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.ruleset.entities.abilities.Ability;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

public class FakeReactiveAbility implements ReactiveAbility {
    public final String ID;

    public FakeReactiveAbility(String id) {
        ID = id;
    }

    @Override
    public Integer priority() {
        return null;
    }

    @Override
    public String eventHook() {
        return null;
    }

    @Override
    public boolean willReact(Ability ability) {
        return false;
    }

    @Override
    public boolean react(boolean b, Character character, Ability ability) {
        return false;
    }

    @Override
    public boolean react(boolean b, Item item, Ability ability) {
        return false;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
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

    @Override
    public String description(Character character) throws IllegalStateException {
        return null;
    }

    @Override
    public String description(Item item) throws IllegalStateException {
        return null;
    }
}
