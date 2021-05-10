package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.TurnBasedTimer;

public abstract class FakeTurnBasedTimer implements TurnBasedTimer {
    private String _id;
    private int _priority;
    @SuppressWarnings("rawtypes")
    private Action _action;

    public boolean _fired;

    @SuppressWarnings("rawtypes")
    FakeTurnBasedTimer(String id, Action action, int priority) {
        _id = id;
        _action = action;
        _priority = priority;
    }

    FakeTurnBasedTimer() {

    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action action() {
        return _action;
    }

    @Override
    public void fire() {
        _fired = true;
    }

    @Override
    public int priority() {
        return _priority;
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
