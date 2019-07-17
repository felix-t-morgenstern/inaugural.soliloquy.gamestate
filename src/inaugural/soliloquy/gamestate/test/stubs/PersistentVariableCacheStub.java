package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.PersistentVariableCache;
import soliloquy.specs.common.infrastructure.ReadOnlyCollection;
import soliloquy.specs.common.infrastructure.ReadOnlyMap;

public class PersistentVariableCacheStub implements PersistentVariableCache {
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
    public ReadOnlyCollection<String> namesRepresentation() {
        return null;
    }

    @Override
    public ReadOnlyMap<String, Object> variablesRepresentation() {
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
