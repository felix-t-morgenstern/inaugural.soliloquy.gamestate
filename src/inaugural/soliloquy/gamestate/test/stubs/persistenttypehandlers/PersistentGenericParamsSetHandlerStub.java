package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.GenericParamsSetStub;
import soliloquy.specs.common.infrastructure.GenericParamsSet;

public class PersistentGenericParamsSetHandlerStub
        extends PersistentValueTypeHandlerStub<GenericParamsSet> {
    @Override
    public String typeName() {
        return "GenericParamsSet";
    }

    @Override
    protected GenericParamsSet generateInstance() {
        return new GenericParamsSetStub();
    }
}
