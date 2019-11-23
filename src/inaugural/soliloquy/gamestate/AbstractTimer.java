package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Timer;

public abstract class AbstractTimer extends HasDeletionInvariants implements Timer {
    private final Action<Void> ACTION;

    final String ID;

    private int _priority;

    AbstractTimer(String timerId, Action<Void> action) {
        ID = timerId;
        ACTION = action;
    }

    @Override
    public String actionTypeId() {
        return ACTION.id();
    }

    @Override
    public void fire() {
        ACTION.run(null);
    }

    @Override
    public int getPriority() {
        return _priority;
    }

    @Override
    public void setPriority(int priority) {
        _priority = priority;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    protected String containingClassName() {
        return null;
    }

    @Override
    protected Deletable getContainingObject() {
        return null;
    }
}
