package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;

public class GenericParamsSetStub implements GenericParamsSet {
    @Override
    public <T> void addParam(String s, T t) throws IllegalArgumentException {

    }

    @Override
    public <T> void addParamsSet(ReadableMap<String, T> ReadableMap) throws IllegalArgumentException, UnsupportedOperationException {

    }

    @Override
    public <T> T getParam(String s, String s1) {
        return null;
    }

    @Override
    public <T> Map<String, T> getParamsSet(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public <T> boolean paramExists(String s, String s1) {
        return false;
    }

    @Override
    public Collection<String> paramTypes() {
        return null;
    }

    @Override
    public boolean removeParam(String s, String s1) {
        return false;
    }

    @Override
    public GenericParamsSet makeClone() {
        return this;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
