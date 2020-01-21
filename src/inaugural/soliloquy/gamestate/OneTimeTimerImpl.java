package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;

import java.util.function.Consumer;

public class OneTimeTimerImpl extends TimerAbstract implements OneTimeTimer {
    private long _roundWhenGoesOff;

    private final Consumer<OneTimeTimer> REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER;

    public OneTimeTimerImpl(String timerId, Action action, long roundWhenGoesOff,
                            Consumer<OneTimeTimer> addRecurringTimerToRoundManager,
                            Consumer<OneTimeTimer> removeOneTimeTimerFromRoundManager) {
        super(timerId, action);
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
    public void fire() {
        super.fire();
        delete();
    }

    @Override
    public String getInterfaceName() {
        return OneTimeTimer.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof OneTimeTimer)) {
            return false;
        }
        OneTimeTimer oneTimeTimer = (OneTimeTimer) o;
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
