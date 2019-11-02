package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

public class RegistryFactoryStub implements RegistryFactory {
    @Override
    public <T extends HasId> Registry<T> make(T t) {
        return new RegistryStub<>();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
