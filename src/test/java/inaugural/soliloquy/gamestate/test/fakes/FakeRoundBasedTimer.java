package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimer;

public abstract class FakeRoundBasedTimer implements RoundBasedTimer {
    private String _id;
    private int _priority;
    @SuppressWarnings("rawtypes")
    private Action _action;

    @SuppressWarnings("rawtypes")
    FakeRoundBasedTimer(String id, Action action, int priority) {
        _id = id;
        _action = action;
        _priority = priority;
    }

    @Override
    public String actionId() {
        return _action.id();
    }

    @Override
    public void run() {
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
