package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;

public class FakePersistentOneTimeTurnBasedTimerHandler
        extends FakePersistentValueTypeHandler<OneTimeTurnBasedTimer> {
    @Override
    public String typeName() {
        return "OneTimeTimer";
    }

    @Override
    protected OneTimeTurnBasedTimer generateInstance() {
        return new FakeOneTimeTurnBasedTimer();
    }
}
