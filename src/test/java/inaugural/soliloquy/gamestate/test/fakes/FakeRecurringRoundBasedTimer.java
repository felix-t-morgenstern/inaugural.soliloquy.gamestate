package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;

public class FakeRecurringRoundBasedTimer extends FakeRoundBasedTimer
        implements RecurringRoundBasedTimer {
    private int _roundModulo;
    private int _roundOffset;

    public FakeRecurringRoundBasedTimer(String id, @SuppressWarnings("rawtypes") Consumer action,
                                        int roundModulo, int roundOffset, int priority) {
        super(id, action, priority);
        _roundModulo = roundModulo;
        _roundOffset = roundOffset;
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
