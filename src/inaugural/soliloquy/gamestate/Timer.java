package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.gamestate.entities.IRoundManager;
import soliloquy.specs.gamestate.entities.ITimer;

public abstract class Timer extends HasDeletionInvariants implements ITimer {
    private final IAction<Void> ACTION;

    final String ID;

    private int _priority;

    Timer(String timerId, IAction<Void> action) {
        ID = timerId;
        ACTION = action;
    }

    @Override
    public IAction<Void> action() {
        return ACTION;
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
    protected boolean containingObjectIsDeleted() {
        return false;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }
}
