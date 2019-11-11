package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.GenericParamsSetStub;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public class PersistentGenericParamsSetHandlerStub
        implements PersistentValueTypeHandler<GenericParamsSet> {
    public String readValue;
    public static final GenericParamsSet READ_RESULT = new GenericParamsSetStub();

    public GenericParamsSet readGenericParamsSet;
    public static final String WRITE_RESULT = "PersistentGenericParamsSetHandlerStub write result";

    @Override
    public GenericParamsSet read(String data) throws IllegalArgumentException {
        readValue = data;
        return READ_RESULT;
    }

    @Override
    public String write(GenericParamsSet input) {
        readGenericParamsSet = input;
        return WRITE_RESULT;
    }

    @Override
    public GenericParamsSet getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
