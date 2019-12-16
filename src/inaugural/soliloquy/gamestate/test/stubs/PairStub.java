package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadablePair;

public class PairStub<T1, T2> extends ReadablePairStub<T1,T2> implements Pair<T1, T2> {
    PairStub(T1 item1, T2 item2) {
        super(item1, item2);
    }

    PairStub(T1 item1, T2 item2, T1 archetype1, T2 archetype2) {
        super(item1, item2, archetype1, archetype2);
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
    public ReadablePair<T1, T2> representation() {
        return new ReadablePairStub<>(_item1, _item2, _archetype1, _archetype2);
    }
}
