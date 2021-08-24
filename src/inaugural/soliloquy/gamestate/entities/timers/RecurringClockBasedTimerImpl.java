package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.AbstractLoopingPausableAtTime;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

// TODO: Consider abstracting out shared functionality between Recurring and OneTime ClockBasedTimers
public class RecurringClockBasedTimerImpl extends AbstractLoopingPausableAtTime
        implements RecurringClockBasedTimer {
    private final Action<Long> FIRING_ACTION;
    private final boolean FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED;

    private long _lastFiringTimestamp;

    public RecurringClockBasedTimerImpl(int periodDuration, int periodModuloOffset,
                                        Action<Long> firingAction,
                                        boolean fireMultipleTimesForMultiplePeriodsElapsed,
                                        Long pausedTimestamp, long lastFiringTimestamp,
                                        Long mostRecentTimestamp) {
        super(periodDuration, periodModuloOffset, pausedTimestamp, mostRecentTimestamp);
        if (pausedTimestamp != null && lastFiringTimestamp > pausedTimestamp) {
            throw new IllegalArgumentException("RecurringClockBasedTimerImpl: " +
                    "lastFiringTimestamp (" + lastFiringTimestamp + ") cannot be after " +
                    "pausedTimestamp (" + pausedTimestamp + ")");
        }
        FIRING_ACTION = Check.ifNull(firingAction, "firingAction");
        FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED =
                fireMultipleTimesForMultiplePeriodsElapsed;
        _lastFiringTimestamp = lastFiringTimestamp;
    }

    @Override
    public long lastFiringTimestamp() {
        return _lastFiringTimestamp;
    }

    @Override
    public boolean fireMultipleTimesForMultiplePeriodsElapsed() {
        return FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED;
    }

    @Override
    public int periodDuration() {
        return PERIOD_DURATION;
    }

    @Override
    public String getInterfaceName() {
        return RecurringClockBasedTimer.class.getCanonicalName();
    }

    @Override
    public String actionId() {
        return FIRING_ACTION.id();
    }

    @Override
    public void fire(long timestamp) {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        if (_pausedTimestamp != null && timestamp >= _pausedTimestamp) {
            throw new UnsupportedOperationException("RecurringClockBasedTimerImpl.fire: " +
                    "timestamp (" + timestamp + ") cannot be greater than current " +
                    "pausedTimestamp (" + _pausedTimestamp + ")");
        }
        FIRING_ACTION.run(_lastFiringTimestamp = timestamp);
    }

    @Override
    public Long mostRecentTimestamp() {
        return TIMESTAMP_VALIDATOR.mostRecentTimestamp();
    }
}
