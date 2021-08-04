package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;

import java.util.Collection;

public class FakeListFactory implements ListFactory {
    @Override
    public <T> List<T> make(T archetype) throws IllegalArgumentException {
        return new FakeList<>(archetype);
    }

    @Override
    public <T> List<T> make(T[] items, T archetype) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> List<V> make(Collection<V> collection, V archetype)
            throws IllegalArgumentException {
        return new FakeList<>(collection, archetype);
    }

    @Override
    public String getInterfaceName() {
        throw new UnsupportedOperationException();
    }
}
