package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.gamestate.factories.ITimerFactory;

import java.util.function.Consumer;

public class TimerFactory implements ITimerFactory {
    private final Consumer<IOneTimeTimer> ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER;
    private final Consumer<IOneTimeTimer> REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER;

    private final Consumer<IRecurringTimer> ADD_RECURRING_TIMER_TO_ROUND_MANAGER;
    private final Consumer<IRecurringTimer> REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER;

    // TODO: Ensure that GamestateModule is assembled in such a way that these consumers are provided to this factory
    public TimerFactory(Consumer<IOneTimeTimer> addOneTimeTimerToRoundManager,
                        Consumer<IOneTimeTimer> removeOneTimeTimerFromRoundManager,
                        Consumer<IRecurringTimer> addRecurringTimerToRoundManager,
                        Consumer<IRecurringTimer> removeRecurringTimerFromRoundManager) {
        // TODO: Test nulls not allowed
        ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER = addOneTimeTimerToRoundManager;
        REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER = removeOneTimeTimerFromRoundManager;

        ADD_RECURRING_TIMER_TO_ROUND_MANAGER = addRecurringTimerToRoundManager;
        REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER = removeRecurringTimerFromRoundManager;
    }

    @Override
    public IOneTimeTimer makeOneTimeTimer(String timerId, IAction<Void> action,
                                          long roundWhenGoesOff)
            throws IllegalArgumentException {
        // TODO: Pass unit tests to ensure that the consumers are in place
        return new OneTimeTimer(timerId, action, roundWhenGoesOff,
                ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER,
                REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER);
    }

    @Override
    public IRecurringTimer makeRecurringTimer(String timerId, IAction<Void> action, int roundModulo,
                                              int roundOffset)
            throws IllegalArgumentException {
        // TODO: Pass unit tests to ensure that the consumers are in place
        return new RecurringTimer(timerId, action, roundModulo, roundOffset,
                ADD_RECURRING_TIMER_TO_ROUND_MANAGER,
                REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER);
    }

    @Override
    public String getInterfaceName() {
        return ITimerFactory.class.getCanonicalName();
    }
}
