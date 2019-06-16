package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.IPersistentVariableCache;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IMap;

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
    public ICollection<String> getNamesRepresentation() {
        return null;
    }

    @Override
    public IMap<String, Object> getVariablesRepresentation() {
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
