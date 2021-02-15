package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;

import java.util.ArrayList;

public class FakeList<V> extends ArrayList<V> implements List<V> {
    private V _archetype;

    public FakeList() {

    }

    public FakeList(V archetype) {
        _archetype = archetype;
    }

    public FakeList(java.util.Collection<V> items, V archetype) {
        super(items);
        _archetype = archetype;
    }

    @Override
    public List<V> makeClone() {
        return new FakeList<>(this, _archetype);
    }

    @Override
    public V getArchetype() {
        return _archetype;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
