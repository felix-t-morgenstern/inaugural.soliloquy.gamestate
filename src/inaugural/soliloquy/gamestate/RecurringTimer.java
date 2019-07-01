package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.gamestate.entities.IRecurringTimer;

import java.util.function.Consumer;

public class RecurringTimer extends Timer implements IRecurringTimer {
    private int _roundModulo;
    private int _roundOffset;

    private final Consumer<IRecurringTimer> REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER;

    public RecurringTimer(String timerId, IAction<Void> action, int roundModulo, int roundOffset,
                          Consumer<IRecurringTimer> addRecurringTimerToRoundManager,
                          Consumer<IRecurringTimer> removeRecurringTimerFromRoundManager) {
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
        return IRecurringTimer.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof IRecurringTimer)) {
            return false;
        }
        IRecurringTimer recurringTimer = (IRecurringTimer) o;
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
