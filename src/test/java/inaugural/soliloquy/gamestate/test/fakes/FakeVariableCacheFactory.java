package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;

import java.util.ArrayList;

public class FakeVariableCacheFactory implements VariableCacheFactory {
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
