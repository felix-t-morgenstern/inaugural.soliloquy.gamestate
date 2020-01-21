package inaugural.soliloquy.gamestate.test.stubs;

import inaugural.soliloquy.gamestate.test.unit.RoundManagerImplTests;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.Timer;

public abstract class TimerStub implements Timer {
    private String _id;
    private int _priority;
    private Action _action;

    public boolean _fired;

    TimerStub(String id, Action action) {
        _id = id;
        _action = action;
    }

    TimerStub() {

    }

    @Override
    public Action action() {
        return _action;
    }

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
        return _id;
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
