package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.common.valueobjects.IMap;

public class GenericParamsSetStub implements IGenericParamsSet {

    @Override
    public IGenericParamsSet makeClone() {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void addParam(String name, T value) throws IllegalArgumentException {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void addParamsSet(IMap<String, T> iMap)
            throws IllegalArgumentException, UnsupportedOperationException {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getParam(String paramTypeName, String paramName) {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> IMap<String, T> getParamsSet(String paramTypeName) throws IllegalArgumentException {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> boolean paramExists(String paramTypeName, String paramName) {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public ICollection<String> paramTypes() {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeParam(String paramTypeName, String paramName) {
        // Stub class; no implementation needed
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        // Stub method, unimplemented
        throw new UnsupportedOperationException();
    }
}
