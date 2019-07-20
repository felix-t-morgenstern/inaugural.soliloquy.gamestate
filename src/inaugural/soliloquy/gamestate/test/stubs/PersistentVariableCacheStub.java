package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.PersistentVariableCache;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;

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
    public ReadableCollection<String> namesRepresentation() {
        return null;
    }

    @Override
    public ReadableMap<String, Object> variablesRepresentation() {
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
