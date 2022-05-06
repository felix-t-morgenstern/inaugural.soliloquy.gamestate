package inaugural.soliloquy.gamestate.entities.timers;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;

import java.util.function.Consumer;

public class OneTimeRoundBasedTimerImpl extends AbstractRoundBasedTimer<OneTimeRoundBasedTimer>
        implements OneTimeRoundBasedTimer {
    private final long ROUND_WHEN_GOES_OFF;

    public OneTimeRoundBasedTimerImpl(String timerId, @SuppressWarnings("rawtypes") Action action,
                                      long roundWhenGoesOff, int priority,
                                      Consumer<OneTimeRoundBasedTimer>
                                             addRecurringTimerToRoundManager,
                                      Consumer<OneTimeRoundBasedTimer>
                                             removeOneTimeTimerFromRoundManager) {
        super(timerId, action, priority, addRecurringTimerToRoundManager,
                removeOneTimeTimerFromRoundManager);
        ROUND_WHEN_GOES_OFF = roundWhenGoesOff;
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
        return OneTimeRoundBasedTimer.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof OneTimeRoundBasedTimer)) {
            return false;
        }
        OneTimeRoundBasedTimer oneTimeTimer = (OneTimeRoundBasedTimer) o;
        return ROUND_WHEN_GOES_OFF == oneTimeTimer.roundWhenGoesOff() &&
                equalz(oneTimeTimer);
    }
}
