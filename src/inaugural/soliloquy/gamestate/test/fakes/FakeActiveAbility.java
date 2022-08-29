package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;

public class FakeActiveAbility implements ActiveAbility {
    private final String ID;

    public FakeActiveAbility(String id) {
        ID = id;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public void use(Character character, List<Pair<Character, Tile>> list,
                    VariableCache variableCache) {

    }

    @Override
    public void use(Item item, List<Pair<Character, Tile>> list, VariableCache variableCache) {

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
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }
}
