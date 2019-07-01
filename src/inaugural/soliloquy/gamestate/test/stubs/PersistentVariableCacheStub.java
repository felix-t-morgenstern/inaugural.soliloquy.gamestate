package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.IPersistentVariableCache;
import soliloquy.specs.common.infrastructure.IReadOnlyCollection;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;

public class PersistentVariableCacheStub implements IPersistentVariableCache {
    @Override
    public <T> void setVariable(String s, T t) throws IllegalArgumentException {

    }

    @Override
    public boolean remove(String s) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public IReadOnlyCollection<String> namesRepresentation() {
        return null;
    }

    @Override
    public IReadOnlyMap<String, Object> variablesRepresentation() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public <T> T getVariable(String s) {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
