package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;

public class CollectionFactoryStub implements ICollectionFactory {
    @Override
    public <T> ICollection<T> make(T archetype) throws IllegalArgumentException {
        return new CollectionStub<T>(archetype);
    }

    @Override
    public <T> ICollection<T> make(T[] items, T archetype) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }
}
