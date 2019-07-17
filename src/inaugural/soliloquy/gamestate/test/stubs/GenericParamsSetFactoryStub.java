package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;

public class GenericParamsSetFactoryStub implements GenericParamsSetFactory {
    public static final GenericParamsSet GENERIC_PARAMS_SET = new GenericParamsSetStub();

    @Override
    public GenericParamsSet make() {
        return GENERIC_PARAMS_SET;
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }
}
