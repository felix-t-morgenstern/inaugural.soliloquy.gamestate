package inaugural.soliloquy.gamestate.entities.timers;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;

import java.util.function.Consumer;

public class OneTimeRoundBasedTimerImpl extends AbstractRoundBasedTimer<OneTimeRoundBasedTimer>
        implements OneTimeRoundBasedTimer {
    private final int ROUND_WHEN_GOES_OFF;

    public OneTimeRoundBasedTimerImpl(String timerId, @SuppressWarnings("rawtypes") Action action,
                                      int roundWhenGoesOff, int priority,
                                      Consumer<OneTimeRoundBasedTimer>
                                              addRecurringTimerToRoundManager,
                                      Consumer<OneTimeRoundBasedTimer>
                                              removeOneTimeTimerFromRoundManager) {
        super(timerId, action, priority, addRecurringTimerToRoundManager,
                removeOneTimeTimerFromRoundManager);
        ROUND_WHEN_GOES_OFF = roundWhenGoesOff;
    }

    @Override
    public int roundWhenGoesOff() {
        enforceDeletionInvariants();
        return ROUND_WHEN_GOES_OFF;
    }

    @Override
    public void run() {
        super.run();
        delete();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof OneTimeRoundBasedTimer oneTimeTimer)) {
            return false;
        }
        return ROUND_WHEN_GOES_OFF == oneTimeTimer.roundWhenGoesOff() &&
                equalz(oneTimeTimer);
    }
}
