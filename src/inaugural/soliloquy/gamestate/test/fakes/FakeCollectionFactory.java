package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;

public class FakeCollectionFactory implements CollectionFactory {
    @Override
    public <T> Collection<T> make(T archetype) throws IllegalArgumentException {
        return new FakeCollection<T>(archetype);
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
