package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;

public class VariableCacheStub implements VariableCache {
    public VariableCacheStub _cloneResult;

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
    public List<String> namesRepresentation() {
        return null;
    }

    @Override
    public Map<String, Object> variablesRepresentation() {
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
        return VariableCache.class.getCanonicalName();
    }

    @Override
    public VariableCache makeClone() {
        return _cloneResult = new VariableCacheStub();
    }
}
