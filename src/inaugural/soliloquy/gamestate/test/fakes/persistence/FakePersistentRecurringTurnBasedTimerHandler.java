package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringTurnBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;

public class FakePersistentRecurringTurnBasedTimerHandler
        extends FakePersistentValueTypeHandler<RecurringTurnBasedTimer> {
    @Override
    public String typeName() {
        return "RecurringTimer";
    }

    @Override
    protected RecurringTurnBasedTimer generateInstance() {
        return new FakeRecurringTurnBasedTimer();
    }
}
