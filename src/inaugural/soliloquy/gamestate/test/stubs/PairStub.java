package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IPair;

public class PairStub<K,V> implements IPair<K,V> {
    private K _item1;
    private V _item2;

    private K _archetype1;
    private V _archetype2;

    public PairStub(K item1, V item2) {
        _item1 = item1;
        _item2 = item2;
        _archetype1 = item1;
        _archetype2 = item2;
    }

    public PairStub(K item1, V item2, K archetype1, V archetype2) {
        _item1 = item1;
        _item2 = item2;
        _archetype1 = archetype1;
        _archetype2 = archetype2;
    }

    @Override
    public K getFirstArchetype() throws IllegalStateException {
        return _archetype1;
    }

    @Override
    public V getSecondArchetype() throws IllegalStateException {
        return _archetype2;
    }

    @Override
    public String getInterfaceName() {
        // Stub method, unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public K getItem1() {
        return _item1;
    }

    @Override
    public void setItem1(K item) throws IllegalArgumentException {
        _item1 = item;
    }

    @Override
    public V getItem2() {
        return _item2;
    }

    @Override
    public void setItem2(V item) throws IllegalArgumentException {
        _item2 = item;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }
}
