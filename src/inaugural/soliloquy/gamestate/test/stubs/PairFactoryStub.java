package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.IPairFactory;
import soliloquy.specs.common.valueobjects.IPair;

public class PairFactoryStub implements IPairFactory {
    @Override
    public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2) throws IllegalArgumentException {
        return new PairStub<>(item1, item2);
    }

    @Override
    public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2, T1 archetype1, T2 archetype2)
            throws IllegalArgumentException {
        return new PairStub<>(item1, item2, archetype1, archetype2);
    }

    @Override
    public String getInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }
}
