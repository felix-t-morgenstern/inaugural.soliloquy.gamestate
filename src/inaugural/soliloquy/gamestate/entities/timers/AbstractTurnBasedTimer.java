package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.gamestate.entities.HasDeletionInvariants;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.timers.TurnBasedTimer;

public abstract class AbstractTurnBasedTimer extends HasDeletionInvariants implements TurnBasedTimer {
    @SuppressWarnings("rawtypes")
    private final Action ACTION;

    final String ID;

    private final int PRIORITY;

    AbstractTurnBasedTimer(String timerId, @SuppressWarnings("rawtypes") Action action,
                           int priority) {
        ID = timerId;
        ACTION = action;
        PRIORITY = priority;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action action() {
        return ACTION;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        enforceDeletionInvariants();
        ACTION.run(null);
    }

    @Override
    public int priority() {
        enforceDeletionInvariants();
        return PRIORITY;
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
