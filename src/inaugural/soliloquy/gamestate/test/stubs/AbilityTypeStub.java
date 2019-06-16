package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.ruleset.entities.abilities.IAbilityType;

public class AbilityTypeStub implements IAbilityType {
    @Override
    public String description(IGenericParamsSet iGenericParamsSet) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
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
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setName(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }
}
