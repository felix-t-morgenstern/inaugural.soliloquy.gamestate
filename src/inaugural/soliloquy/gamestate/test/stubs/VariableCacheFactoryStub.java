package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;

public class VariableCacheFactoryStub implements VariableCacheFactory {
    public VariableCache _mostRecentlyCreated;

    @Override
    public VariableCache make() {
        return _mostRecentlyCreated = new VariableCacheStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
