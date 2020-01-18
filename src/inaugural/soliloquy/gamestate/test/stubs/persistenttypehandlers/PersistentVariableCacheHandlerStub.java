package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.infrastructure.VariableCache;

public class PersistentVariableCacheHandlerStub
        extends PersistentValueTypeHandlerStub<VariableCache> {
    @Override
    public String typeName() {
        return "VariableCache";
    }

    @Override
    protected VariableCache generateInstance() {
        return new VariableCacheStub();
    }
}
