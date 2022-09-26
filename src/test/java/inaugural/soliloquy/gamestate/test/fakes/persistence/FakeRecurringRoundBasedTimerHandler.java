package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;

public class FakeRecurringRoundBasedTimerHandler
        extends FakeTypeHandler<RecurringRoundBasedTimer> {
    @Override
    public String typeName() {
        return "RecurringTimer";
    }

    @Override
    protected RecurringRoundBasedTimer generateInstance() {
        return new FakeRecurringRoundBasedTimer();
    }
}
