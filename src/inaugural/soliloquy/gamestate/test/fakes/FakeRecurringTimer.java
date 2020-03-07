package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.RecurringTimer;

public class FakeRecurringTimer extends FakeTimer implements RecurringTimer {
    private int _roundModulo;
    private int _roundOffset;

    public FakeRecurringTimer(String id, Action action) {
        super(id, action);
    }

    public FakeRecurringTimer() {

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
