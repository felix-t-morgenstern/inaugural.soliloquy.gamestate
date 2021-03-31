package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Timer;

public abstract class TimerAbstract extends HasDeletionInvariants implements Timer {
    private final Action ACTION;

    final String ID;

    private int _priority;

    TimerAbstract(String timerId, Action action) {
        ID = timerId;
        ACTION = action;
    }

    @Override
    public Action action() {
        return ACTION;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void fire() {
        enforceDeletionInvariants();
        ACTION.run(null);
    }

    @Override
    public int getPriority() {
        enforceDeletionInvariants();
        return _priority;
    }

    @Override
    public void setPriority(int priority) {
        enforceDeletionInvariants();
        _priority = priority;
    }

    @Override
    public String id() throws IllegalStateException {
        enforceDeletionInvariants();
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
