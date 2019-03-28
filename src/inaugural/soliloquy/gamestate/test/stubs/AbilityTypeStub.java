package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.IGameZone;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilityType;

public class AbilityTypeStub implements IAbilityType {
    @Override
    public String description(IGenericParamsSet iGenericParamsSet) throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IGameZone getGameZone() throws IllegalStateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IGenericParamsSet params() throws IllegalStateException {
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
    public void read(String s, boolean b) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String write() throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }
}
