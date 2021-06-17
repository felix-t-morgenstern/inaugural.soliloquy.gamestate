package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.entities.timers.RecurringClockBasedTimerImpl;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

public class ClockBasedTimerFactoryImpl implements ClockBasedTimerFactory {
    @Override
    public OneTimeClockBasedTimer make(long firingTimestamp, Action<Long> firingAction,
                                       Long pausedTimestamp)
            throws IllegalArgumentException {
        return new OneTimeClockBasedTimerImpl(firingTimestamp, firingAction, pausedTimestamp);
    }

    @Override
    public RecurringClockBasedTimer make(int periodDuration, int periodModuloOffset,
                                         Action<Long> firingAction,
                                         boolean fireMultipleTimesForMultiplePeriodsElapsed,
                                         Long pausedTimestamp, long lastFiringTimestamp)
            throws IllegalArgumentException {
        return new RecurringClockBasedTimerImpl(periodDuration, periodModuloOffset, firingAction,
                fireMultipleTimesForMultiplePeriodsElapsed, pausedTimestamp, lastFiringTimestamp);
    }

    @Override
    public String getInterfaceName() {
        return ClockBasedTimerFactory.class.getCanonicalName();
    }
}
