package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.factories.TimerFactory;

import java.util.function.Consumer;

public class TimerFactoryImpl implements TimerFactory {
    private final Consumer<OneTimeTimer> ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER;
    private final Consumer<OneTimeTimer> REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER;

    private final Consumer<RecurringTimer> ADD_RECURRING_TIMER_TO_ROUND_MANAGER;
    private final Consumer<RecurringTimer> REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER;

    // TODO: Ensure that GamestateModule is assembled in such a way that these consumers are provided to this factory
    public TimerFactoryImpl(Consumer<OneTimeTimer> addOneTimeTimerToRoundManager,
                            Consumer<OneTimeTimer> removeOneTimeTimerFromRoundManager,
                            Consumer<RecurringTimer> addRecurringTimerToRoundManager,
                            Consumer<RecurringTimer> removeRecurringTimerFromRoundManager) {
        // TODO: Test nulls not allowed
        ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER = addOneTimeTimerToRoundManager;
        REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER = removeOneTimeTimerFromRoundManager;

        ADD_RECURRING_TIMER_TO_ROUND_MANAGER = addRecurringTimerToRoundManager;
        REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER = removeRecurringTimerFromRoundManager;
    }

    @Override
    public OneTimeTimer makeOneTimeTimer(String timerId, Action action, long roundWhenGoesOff)
            throws IllegalArgumentException {
        // TODO: Pass unit tests to ensure that the consumers are in place
        return new OneTimeTimerImpl(timerId, action, roundWhenGoesOff,
                ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER,
                REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER);
    }

    @Override
    public RecurringTimer makeRecurringTimer(String timerId, Action action, int roundModulo,
                                              int roundOffset)
            throws IllegalArgumentException {
        // TODO: Pass unit tests to ensure that the consumers are in place
        return new RecurringTimerImpl(timerId, action, roundModulo, roundOffset,
                ADD_RECURRING_TIMER_TO_ROUND_MANAGER,
                REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER);
    }

    @Override
    public String getInterfaceName() {
        return TimerFactory.class.getCanonicalName();
    }
}
