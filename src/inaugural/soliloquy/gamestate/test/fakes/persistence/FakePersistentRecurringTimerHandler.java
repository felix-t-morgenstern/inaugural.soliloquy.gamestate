package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;

public class FakePersistentRecurringTimerHandler
        extends FakePersistentValueTypeHandler<RecurringTimer> {
    @Override
    public String typeName() {
        return "RecurringTimer";
    }

    @Override
    protected RecurringTimer generateInstance() {
        return new FakeRecurringTimer();
    }
}
