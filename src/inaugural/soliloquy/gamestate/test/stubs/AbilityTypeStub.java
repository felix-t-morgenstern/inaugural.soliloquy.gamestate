package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class AbilityTypeStub implements AbilityType {
    @Override
    public String description(GenericParamsSet genericParamsSet) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }
}
