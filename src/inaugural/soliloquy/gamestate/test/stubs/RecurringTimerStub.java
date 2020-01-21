package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.RecurringTimer;

public class RecurringTimerStub extends TimerStub implements RecurringTimer {
    private int _roundModulo;
    private int _roundOffset;

    public RecurringTimerStub(String id, Action action) {
        super(id, action);
    }

    public RecurringTimerStub() {

    }

    @Override
    public int getRoundModulo() {
        return _roundModulo;
    }

    @Override
    public void setRoundModulo(int i) throws IllegalArgumentException {
        _roundModulo = i;
    }

    @Override
    public int getRoundOffset() {
        return _roundOffset;
    }

    @Override
    public void setRoundOffset(int i) throws IllegalArgumentException {
        _roundOffset = i;
    }
}
