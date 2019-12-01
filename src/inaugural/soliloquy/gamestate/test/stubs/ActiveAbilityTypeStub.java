package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;

public class ActiveAbilityTypeStub implements ActiveAbilityType {
    private final String ID;

    public ActiveAbilityTypeStub(String id) {
        ID = id;
    }

    @Override
    public void use(AbilitySource abilitySource, Collection<Pair<Character, Tile>> collection, GenericParamsSet genericParamsSet) {

    }

    @Override
    public String description(GenericParamsSet genericParamsSet) throws IllegalStateException {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }
}
