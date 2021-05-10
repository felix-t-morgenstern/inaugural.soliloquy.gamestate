package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;

public class FakeRecurringTurnBasedTimer extends FakeTurnBasedTimer
        implements RecurringTurnBasedTimer {
    private int _roundModulo;
    private int _roundOffset;

    public FakeRecurringTurnBasedTimer(String id, @SuppressWarnings("rawtypes") Action action,
                                       int roundModulo, int roundOffset, int priority) {
        super(id, action, priority);
        _roundModulo = roundModulo;
        _roundOffset = roundOffset;
    }

    public FakeRecurringTurnBasedTimer() {

    }

    @Override
    public int roundModulo() {
        return _roundModulo;
    }

    @Override
    public int roundOffset() {
        return _roundOffset;
    }
}
