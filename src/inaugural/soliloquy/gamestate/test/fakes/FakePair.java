package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Pair;

public class FakePair<T1, T2> implements Pair<T1, T2> {
    private T1 _item1;
    private T2 _item2;
    private T1 _archetype1;
    private T2 _archetype2;

    public FakePair(T1 item1, T2 item2) {
        _item1 = _archetype1 = item1;
        _item2 = _archetype2 = item2;
    }

    FakePair(T1 item1, T2 item2, T1 archetype1, T2 archetype2) {
        _item1 = item1;
        _item2 = item2;
        _archetype1 = archetype1;
        _archetype2 = archetype2;
    }

    @Override
    public T1 getItem1() {
        return _item1;
    }

    @Override
    public T2 getItem2() {
        return _item2;
    }

    @Override
    public void setItem1(T1 item) throws IllegalArgumentException {
        _item1 = item;
    }

    @Override
    public void setItem2(T2 item) throws IllegalArgumentException {
        _item2 = item;
    }

    @Override
    public Pair<T1, T2> makeClone() {
        return new FakePair<>(_item1, _item2, _archetype1, _archetype2);
    }

    @Override
    public T1 getFirstArchetype() throws IllegalStateException {
        return _archetype1;
    }

    @Override
    public T2 getSecondArchetype() throws IllegalStateException {
        return _archetype2;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
