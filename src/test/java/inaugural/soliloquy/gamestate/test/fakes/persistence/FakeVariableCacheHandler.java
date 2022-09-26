package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.infrastructure.VariableCache;

public class FakeVariableCacheHandler
        extends FakeTypeHandler<VariableCache> {
    @Override
    public String typeName() {
        return "VariableCache";
    }

    @Override
    protected VariableCache generateInstance() {
        return new VariableCacheStub();
    }
}
