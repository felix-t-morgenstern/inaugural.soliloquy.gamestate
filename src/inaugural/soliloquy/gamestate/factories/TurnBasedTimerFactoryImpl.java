package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeTurnBasedTimerImpl;
import inaugural.soliloquy.gamestate.entities.timers.RecurringTurnBasedTimerImpl;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;
import soliloquy.specs.gamestate.factories.TurnBasedTimerFactory;

import java.util.function.Consumer;

public class TurnBasedTimerFactoryImpl implements TurnBasedTimerFactory {
    private final Consumer<OneTimeTurnBasedTimer> ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER;
    private final Consumer<OneTimeTurnBasedTimer> REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER;

    private final Consumer<RecurringTurnBasedTimer> ADD_RECURRING_TIMER_TO_ROUND_MANAGER;
    private final Consumer<RecurringTurnBasedTimer> REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER;

    // TODO: Ensure that GamestateModule is assembled in such a way that these consumers are provided to this factory
    public TurnBasedTimerFactoryImpl(Consumer<OneTimeTurnBasedTimer>
                                             addOneTimeTimerToRoundManager,
                                     Consumer<OneTimeTurnBasedTimer>
                                             removeOneTimeTimerFromRoundManager,
                                     Consumer<RecurringTurnBasedTimer>
                                             addRecurringTimerToRoundManager,
                                     Consumer<RecurringTurnBasedTimer>
                                             removeRecurringTimerFromRoundManager) {
        // TODO: Test nulls not allowed
        ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER = addOneTimeTimerToRoundManager;
        REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER = removeOneTimeTimerFromRoundManager;

        ADD_RECURRING_TIMER_TO_ROUND_MANAGER = addRecurringTimerToRoundManager;
        REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER = removeRecurringTimerFromRoundManager;
    }

    @Override
    public OneTimeTurnBasedTimer makeOneTimeTimer(String timerId, Action action,
                                                  long roundWhenGoesOff, int priority)
            throws IllegalArgumentException {
        // TODO: Pass unit tests to ensure that the consumers are in place
        return new OneTimeTurnBasedTimerImpl(timerId, action, roundWhenGoesOff, priority,
                ADD_ONE_TIME_TIMER_TO_ROUND_MANAGER,
                REMOVE_ONE_TIME_TIMER_FROM_ROUND_MANAGER);
    }

    @Override
    public RecurringTurnBasedTimer makeRecurringTimer(String timerId, Action action,
                                                      int roundModulo, int roundOffset,
                                                      int priority)
            throws IllegalArgumentException {
        // TODO: Pass unit tests to ensure that the consumers are in place
        return new RecurringTurnBasedTimerImpl(timerId, action, roundModulo, roundOffset, priority,
                ADD_RECURRING_TIMER_TO_ROUND_MANAGER,
                REMOVE_RECURRING_TIMER_FROM_ROUND_MANAGER);
    }

    @Override
    public String getInterfaceName() {
        return TurnBasedTimerFactory.class.getCanonicalName();
    }
}
