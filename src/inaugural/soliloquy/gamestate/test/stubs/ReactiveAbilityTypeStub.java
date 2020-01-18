package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.entities.abilities.IncomingAbilityDescription;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;

public class ReactiveAbilityTypeStub implements ReactiveAbilityType {
    public final String ID;

    public ReactiveAbilityTypeStub(String id) {
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
    public boolean willReact(IncomingAbilityDescription incomingAbilityDescription) {
        return false;
    }

    @Override
    public boolean react(boolean b, AbilitySource abilitySource, IncomingAbilityDescription incomingAbilityDescription) {
        return false;
    }

    @Override
    public String description(VariableCache variableCache) throws IllegalStateException {
        return null;
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
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
