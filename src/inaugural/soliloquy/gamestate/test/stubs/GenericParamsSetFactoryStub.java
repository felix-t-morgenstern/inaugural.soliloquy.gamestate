package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.IGenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.IGenericParamsSet;

public class GenericParamsSetFactoryStub implements IGenericParamsSetFactory {
    public static final IGenericParamsSet GENERIC_PARAMS_SET = new GenericParamsSetStub();

    @Override
    public IGenericParamsSet make() {
        return GENERIC_PARAMS_SET;
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }
}
