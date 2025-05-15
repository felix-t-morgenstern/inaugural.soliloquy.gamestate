package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeRoundBasedTimerImpl;
import inaugural.soliloquy.gamestate.entities.timers.RecurringRoundBasedTimerImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

public class RoundBasedTimerFactoryImpl implements RoundBasedTimerFactory {
    private final RoundBasedTimerManager ROUND_BASED_TIMER_MANAGER;

    public RoundBasedTimerFactoryImpl(RoundBasedTimerManager roundBasedTimerManager) {
        ROUND_BASED_TIMER_MANAGER = Check.ifNull(roundBasedTimerManager, "roundBasedTimerManager");
    }

    @Override
    public OneTimeRoundBasedTimer makeOneTimeTimer(String timerId, Action action,
                                                   int roundWhenGoesOff, int priority)
            throws IllegalArgumentException {
        return new OneTimeRoundBasedTimerImpl(timerId, action, roundWhenGoesOff, priority,
                ROUND_BASED_TIMER_MANAGER::registerOneTimeRoundBasedTimer,
                ROUND_BASED_TIMER_MANAGER::deregisterOneTimeRoundBasedTimer);
    }

    @Override
    public RecurringRoundBasedTimer makeRecurringTimer(String timerId, Action action,
                                                       int roundModulo, int roundOffset,
                                                       int priority)
            throws IllegalArgumentException {
        return new RecurringRoundBasedTimerImpl(timerId, action, roundModulo, roundOffset,
                priority, ROUND_BASED_TIMER_MANAGER::registerRecurringRoundBasedTimer,
                ROUND_BASED_TIMER_MANAGER::deregisterRecurringRoundBasedTimer);
    }
}
