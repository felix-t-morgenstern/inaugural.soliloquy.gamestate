package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;

import java.util.function.Consumer;

public class OneTimeTimer extends Timer implements IOneTimeTimer {
    private long _roundWhenGoesOff;

    private final Consumer<IOneTimeTimer> REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER;

    public OneTimeTimer(String timerId, IAction<Void> timerActionId, long roundWhenGoesOff,
                        Consumer<IOneTimeTimer> addRecurringTimerToRoundManager,
                        Consumer<IOneTimeTimer> removeOneTimeTimerFromRoundManager) {
        super(timerId, timerActionId);
        _roundWhenGoesOff = roundWhenGoesOff;
        REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER = removeOneTimeTimerFromRoundManager;
        addRecurringTimerToRoundManager.accept(this);
    }

    @Override
    public long getRoundWhenGoesOff() {
        return _roundWhenGoesOff;
    }

    @Override
    public void setRoundWhenGoesOff(long roundWhenGoesOff) throws IllegalArgumentException {
        _roundWhenGoesOff = roundWhenGoesOff;
    }

    @Override
    public String getInterfaceName() {
        return IOneTimeTimer.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof IOneTimeTimer)) {
            return false;
        }
        IOneTimeTimer oneTimeTimer = (IOneTimeTimer) o;
        return oneTimeTimer.id().equals(ID);
    }

    @Override
    protected String className() {
        return "OneTimeTimer";
    }

    @Override
    public void delete() throws IllegalStateException {
        REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER.accept(this);
        super.delete();
    }
}
