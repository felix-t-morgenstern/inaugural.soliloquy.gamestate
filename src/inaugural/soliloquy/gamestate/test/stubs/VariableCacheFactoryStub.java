package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;

import java.util.ArrayList;

public class VariableCacheFactoryStub implements VariableCacheFactory {
    public final ArrayList<VariableCache> Created = new ArrayList<>();

    @Override
    public VariableCache make() {
        VariableCache mostRecentlyCreated = new VariableCacheStub();
        Created.add(mostRecentlyCreated);
        return mostRecentlyCreated;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
