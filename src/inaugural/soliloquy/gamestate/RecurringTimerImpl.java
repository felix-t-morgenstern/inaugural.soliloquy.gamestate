package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.RecurringTimer;

import java.util.function.Consumer;

public class RecurringTimerImpl extends AbstractTimer implements RecurringTimer {
    private int _roundModulo;
    private int _roundOffset;

    private final Consumer<RecurringTimer> REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER;

    public RecurringTimerImpl(String timerId, Action<Void> action, int roundModulo, int roundOffset,
                              Consumer<RecurringTimer> addRecurringTimerToRoundManager,
                              Consumer<RecurringTimer> removeRecurringTimerFromRoundManager) {
        super(timerId, action);
        _roundModulo = roundModulo;
        _roundOffset = roundOffset;
        REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER = removeRecurringTimerFromRoundManager;
        addRecurringTimerToRoundManager.accept(this);
    }

    @Override
    public int getRoundModulo() {
        return _roundModulo;
    }

    @Override
    public void setRoundModulo(int roundModulo) throws IllegalArgumentException {
        _roundModulo = roundModulo;
    }

    @Override
    public int getRoundOffset() {
        return _roundOffset;
    }

    @Override
    public void setRoundOffset(int roundOffset) throws IllegalArgumentException {
        _roundOffset = roundOffset;
    }

    @Override
    public String getInterfaceName() {
        return RecurringTimer.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof RecurringTimer)) {
            return false;
        }
        RecurringTimer recurringTimer = (RecurringTimer) o;
        return ID.equals(recurringTimer.id());
    }

    @Override
    protected String className() {
        return "RecurringTimer";
    }

    @Override
    public void delete() throws IllegalStateException {
        REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER.accept(this);
        super.delete();
    }
}