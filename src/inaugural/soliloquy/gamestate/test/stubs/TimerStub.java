package inaugural.soliloquy.gamestate.test.stubs;

import inaugural.soliloquy.gamestate.test.unit.RoundManagerImplTests;
import soliloquy.specs.gamestate.entities.Timer;

public abstract class TimerStub implements Timer {
    private int _priority;

    public boolean _fired;

    @Override
    public void fire() {
        _fired = true;
        RoundManagerImplTests.ROUND_END_ACTIONS_FIRED.add(this);
    }

    @Override
    public int getPriority() {
        return _priority;
    }

    @Override
    public void setPriority(int i) {
        _priority = i;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
