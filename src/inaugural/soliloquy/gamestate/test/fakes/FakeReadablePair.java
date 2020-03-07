package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.ReadablePair;

public class FakeReadablePair<T1, T2> implements ReadablePair<T1, T2> {
    T1 _item1;
    T2 _item2;

    T1 _archetype1;
    T2 _archetype2;

    public FakeReadablePair(T1 item1, T2 item2) {
        _item1 = item1;
        _item2 = item2;

        _archetype1 = item1;
        _archetype2 = item2;
    }

    FakeReadablePair(T1 item1, T2 item2, T1 archetype1, T2 archetype2) {
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
    public T1 getFirstArchetype() throws IllegalStateException {
        return _archetype1;
    }

    @Override
    public T2 getSecondArchetype() throws IllegalStateException {
        return _archetype2;
    }

    @Override
    public String getInterfaceName() {
        // Stub method, unimplemented
        throw new UnsupportedOperationException();
    }
}
