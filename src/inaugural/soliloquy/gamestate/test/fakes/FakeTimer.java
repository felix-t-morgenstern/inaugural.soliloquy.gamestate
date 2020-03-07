package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.Timer;

public abstract class FakeTimer implements Timer {
    private String _id;
    private int _priority;
    @SuppressWarnings("rawtypes")
    private Action _action;

    public boolean _fired;

    @SuppressWarnings("rawtypes")
    FakeTimer(String id, Action action) {
        _id = id;
        _action = action;
    }

    FakeTimer() {

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
