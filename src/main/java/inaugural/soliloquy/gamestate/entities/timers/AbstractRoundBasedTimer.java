package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.gamestate.entities.HasDeletionInvariants;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimer;

import java.util.function.Consumer;

public abstract class AbstractRoundBasedTimer<T extends RoundBasedTimer>
        extends HasDeletionInvariants
        implements RoundBasedTimer {
    private final Consumer<T> REMOVE_TIMER_FROM_MANAGER;

    @SuppressWarnings("rawtypes")
    private final Action ACTION;
    private final String ID;
    private final int PRIORITY;

    AbstractRoundBasedTimer(String timerId, @SuppressWarnings("rawtypes") Action action,
                            int priority, Consumer<T> addTimerToManager,
                            Consumer<T> removeTimerFromManager) {
        ID = Check.ifNullOrEmpty(timerId, "timerId");
        ACTION = Check.ifNull(action, "action");
        PRIORITY = priority;
        //noinspection unchecked
        Check.ifNull(addTimerToManager, "addTimerToManager").accept((T) this);
        REMOVE_TIMER_FROM_MANAGER = Check.ifNull(removeTimerFromManager, "removeTimerFromManager");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        enforceDeletionInvariants();
        ACTION.accept(null);
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
    public String actionId() {
        return ACTION.id();
    }

    @Override
    public void delete() throws IllegalStateException {
        //noinspection unchecked
        REMOVE_TIMER_FROM_MANAGER.accept((T) this);
        super.delete();
    }

    @Override
    protected String containingClassName() {
        return null;
    }

    @Override
    protected Deletable getContainingObject() {
        return null;
    }

    @SuppressWarnings({"SpellCheckingInspection"})
    protected boolean equalz(T o) {
        return ID.equals(o.id()) &&
                actionId().equals(o.actionId()) &&
                PRIORITY == o.priority();
    }
}
