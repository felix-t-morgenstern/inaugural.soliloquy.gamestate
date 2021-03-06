package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class AbilityTypeStub implements AbilityType {
    @Override
    public String description(VariableCache variableCache) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDeleted() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        return AbilityType.class.getCanonicalName();
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }
}
