package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.entities.timers.RecurringClockBasedTimerImpl;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

public class ClockBasedTimerFactoryImpl implements ClockBasedTimerFactory {
    @Override
    public OneTimeClockBasedTimer make(String id, long firingTimestamp, Action<Long> firingAction,
                                       Long pausedTimestamp, Long mostRecentTimestamp)
            throws IllegalArgumentException {
        return new OneTimeClockBasedTimerImpl(id, firingTimestamp, firingAction, pausedTimestamp,
                mostRecentTimestamp);
    }

    @Override
    public RecurringClockBasedTimer make(String id, int periodDuration, int periodModuloOffset,
                                         Action<Long> firingAction,
                                         boolean fireMultipleTimesForMultiplePeriodsElapsed,
                                         Long pausedTimestamp, long lastFiringTimestamp,
                                         Long mostRecentTimestamp)
            throws IllegalArgumentException {
        return new RecurringClockBasedTimerImpl(id, periodDuration, periodModuloOffset,
                firingAction, fireMultipleTimesForMultiplePeriodsElapsed, pausedTimestamp,
                lastFiringTimestamp, mostRecentTimestamp);
    }

    @Override
    public String getInterfaceName() {
        return ClockBasedTimerFactory.class.getCanonicalName();
    }
}
