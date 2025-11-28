package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.AbstractLoopingPausableAtTime;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

// TODO: Consider abstracting out shared functionality between Recurring and OneTime
//  ClockBasedTimers
public class RecurringClockBasedTimerImpl extends AbstractLoopingPausableAtTime
        implements RecurringClockBasedTimer {
    private final String ID;
    private final Consumer<Long> FIRING_ACTION;
    private final boolean FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED;

    private long lastFiringTimestamp;

    public RecurringClockBasedTimerImpl(String id, int periodDuration, int periodModuloOffset,
                                        Consumer<Long> firingAction,
                                        boolean fireMultipleTimesForMultiplePeriodsElapsed,
                                        Long pausedTimestamp, long lastFiringTimestamp,
                                        TimestampValidator timestampValidator) {
        super(periodDuration, periodModuloOffset, pausedTimestamp, timestampValidator);
        ID = Check.ifNullOrEmpty(id, "id");
        if (pausedTimestamp != null && lastFiringTimestamp > pausedTimestamp) {
            throw new IllegalArgumentException("RecurringClockBasedTimerImpl: " +
                    "lastFiringTimestamp (" + lastFiringTimestamp + ") cannot be after " +
                    "pausedTimestamp (" + pausedTimestamp + ")");
        }
        FIRING_ACTION = Check.ifNull(firingAction, "firingAction");
        FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED =
                fireMultipleTimesForMultiplePeriodsElapsed;
        this.lastFiringTimestamp = lastFiringTimestamp;
    }

    @Override
    public long lastFiringTimestamp() {
        return lastFiringTimestamp;
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
    public String consumerId() {
        return FIRING_ACTION.id();
    }

    @Override
    public void fire(long timestamp) {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        if (pausedTimestamp != null && timestamp >= pausedTimestamp) {
            throw new UnsupportedOperationException("RecurringClockBasedTimerImpl.fire: " +
                    "timestamp (" + timestamp + ") cannot be greater than current " +
                    "pausedTimestamp (" + pausedTimestamp + ")");
        }
        FIRING_ACTION.accept(lastFiringTimestamp = timestamp);
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }
}
