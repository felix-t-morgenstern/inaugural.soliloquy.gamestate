package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.entities.timers.RecurringClockBasedTimerImpl;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

public class ClockBasedTimerFactoryImpl implements ClockBasedTimerFactory {
    private final TimestampValidator TIMESTAMP_VALIDATOR;

    public ClockBasedTimerFactoryImpl(TimestampValidator timestampValidator) {
        TIMESTAMP_VALIDATOR = Check.ifNull(timestampValidator, "timestampValidator");
    }

    @Override
    public OneTimeClockBasedTimer make(String id, long firingTimestamp, Consumer<Long> firingAction,
                                       Long pausedTimestamp)
            throws IllegalArgumentException {
        return new OneTimeClockBasedTimerImpl(id, firingTimestamp, firingAction, pausedTimestamp,
                TIMESTAMP_VALIDATOR);
    }

    @Override
    public RecurringClockBasedTimer make(String id, int periodDuration, int periodModuloOffset,
                                         Consumer<Long> firingAction,
                                         boolean fireMultipleTimesForMultiplePeriodsElapsed,
                                         Long pausedTimestamp, long lastFiringTimestamp)
            throws IllegalArgumentException {
        return new RecurringClockBasedTimerImpl(id, periodDuration, periodModuloOffset,
                firingAction, fireMultipleTimesForMultiplePeriodsElapsed, pausedTimestamp,
                lastFiringTimestamp, TIMESTAMP_VALIDATOR);
    }
}
