package inaugural.soliloquy.gamestate.entities.timers;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;

import java.util.function.Consumer;

public class OneTimeTurnBasedTimerImpl extends AbstractTurnBasedTimer
        implements OneTimeTurnBasedTimer {
    private final long ROUND_WHEN_GOES_OFF;

    private final Consumer<OneTimeTurnBasedTimer> REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER;

    public OneTimeTurnBasedTimerImpl(String timerId, @SuppressWarnings("rawtypes") Action action,
                                     long roundWhenGoesOff, int priority,
                                     Consumer<OneTimeTurnBasedTimer>
                                             addRecurringTimerToRoundManager,
                                     Consumer<OneTimeTurnBasedTimer>
                                             removeOneTimeTimerFromRoundManager) {
        super(timerId, action, priority);
        ROUND_WHEN_GOES_OFF = roundWhenGoesOff;
        REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER = removeOneTimeTimerFromRoundManager;
        addRecurringTimerToRoundManager.accept(this);
    }

    @Override
    public long roundWhenGoesOff() {
        enforceDeletionInvariants();
        return ROUND_WHEN_GOES_OFF;
    }

    @Override
    public void run() {
        super.run();
        delete();
    }

    @Override
    public String getInterfaceName() {
        return OneTimeTurnBasedTimer.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof OneTimeTurnBasedTimer)) {
            return false;
        }
        OneTimeTurnBasedTimer oneTimeTimer = (OneTimeTurnBasedTimer) o;
        return oneTimeTimer.id().equals(ID);
    }

    @Override
    public void delete() throws IllegalStateException {
        REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER.accept(this);
        super.delete();
    }
}
