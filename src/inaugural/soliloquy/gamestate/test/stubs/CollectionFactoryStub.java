package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;

public class CollectionFactoryStub implements CollectionFactory {
    @Override
    public <T> Collection<T> make(T archetype) throws IllegalArgumentException {
        return new CollectionStub<T>(archetype);
    }

    @Override
    public <T> Collection<T> make(T[] items, T archetype) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }
}
