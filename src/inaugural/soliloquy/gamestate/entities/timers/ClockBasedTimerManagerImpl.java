package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.graphics.rendering.FrameExecutor;

import java.util.ArrayList;

public class ClockBasedTimerManagerImpl implements ClockBasedTimerManager {
    private final FrameExecutor FRAME_EXECUTOR;
    private final ArrayList<OneTimeClockBasedTimer> ONE_TIME_CLOCK_BASED_TIMERS;
    private final ArrayList<RecurringClockBasedTimer> RECURRING_CLOCK_BASED_TIMERS;

    private Long _mostRecentFireTimersTimestamp;

    public ClockBasedTimerManagerImpl(FrameExecutor frameExecutor) {
        FRAME_EXECUTOR = Check.ifNull(frameExecutor, "frameExecutor");
        ONE_TIME_CLOCK_BASED_TIMERS = new ArrayList<>();
        RECURRING_CLOCK_BASED_TIMERS = new ArrayList<>();
    }

    @Override
    public void registerOneTimeTimer(OneTimeClockBasedTimer oneTimeClockBasedTimer)
            throws IllegalArgumentException {
        ONE_TIME_CLOCK_BASED_TIMERS.add(
                Check.ifNull(oneTimeClockBasedTimer, "oneTimeClockBasedTimer"));
    }

    @Override
    public void registerRecurringTimer(RecurringClockBasedTimer recurringClockBasedTimer)
            throws IllegalArgumentException {
        Check.ifNull(recurringClockBasedTimer, "recurringClockBasedTimer");
        if (recurringClockBasedTimer.firingTimeModuloOffset() >=
                recurringClockBasedTimer.firingTimePeriod()) {
            throw new IllegalArgumentException(
                    "ClockBasedTimerManagerImpl.registerRecurringTimer: firingTimeModuloOffset " +
                            "cannot be greater than or equal to firingTimePeriod");
        }
        RECURRING_CLOCK_BASED_TIMERS.add(recurringClockBasedTimer);
    }

    @Override
    public void deregisterOneTimeTimer(OneTimeClockBasedTimer oneTimeClockBasedTimer)
            throws IllegalArgumentException {
        ONE_TIME_CLOCK_BASED_TIMERS.remove(
                Check.ifNull(oneTimeClockBasedTimer, "oneTimeClockBasedTimer"));
    }

    @Override
    public void deregisterRecurringTimer(RecurringClockBasedTimer recurringClockBasedTimer)
            throws IllegalArgumentException {
        RECURRING_CLOCK_BASED_TIMERS.remove(
                Check.ifNull(recurringClockBasedTimer, "recurringClockBasedTimer"));
    }

    @Override
    public void fireTimers(long timestamp) {
        if (_mostRecentFireTimersTimestamp != null) {
            if (timestamp <= _mostRecentFireTimersTimestamp) {
                throw new IllegalArgumentException("ClockBasedTimerManagerImpl.fireTimers: " +
                        "this method cannot be invoked at the same or a prior timestamp");
            }
        }
        _mostRecentFireTimersTimestamp = timestamp;

        // NB: This list is created to avoid a ConcurrentModificationException
        ArrayList<OneTimeClockBasedTimer> oneTimeTurnBasedTimersToRemove = new ArrayList<>();
        ONE_TIME_CLOCK_BASED_TIMERS.forEach(oneTimeClockBasedTimer -> {
            if (oneTimeClockBasedTimer.firingTime() <= timestamp) {
                FRAME_EXECUTOR.registerFrameBlockingEvent(oneTimeClockBasedTimer::fire);
                oneTimeTurnBasedTimersToRemove.add(oneTimeClockBasedTimer);
            }
        });
        ONE_TIME_CLOCK_BASED_TIMERS.removeAll(oneTimeTurnBasedTimersToRemove);
        RECURRING_CLOCK_BASED_TIMERS.forEach(recurringClockBasedTimer -> {
            long offsetAdjustedTimestamp =
                    timestamp - recurringClockBasedTimer.firingTimeModuloOffset();
            long offsetAdjustedLastFiringTime = recurringClockBasedTimer.lastFiringTimestamp()
                    - recurringClockBasedTimer.firingTimeModuloOffset();
            if (recurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed()) {
                int numberOfTimesToFire =
                        (int)(offsetAdjustedTimestamp - offsetAdjustedLastFiringTime)
                                / recurringClockBasedTimer.firingTimePeriod();
                while(numberOfTimesToFire-- > 0) {
                    FRAME_EXECUTOR.registerFrameBlockingEvent(recurringClockBasedTimer::fire);
                }
            }
            else {
                if (offsetAdjustedTimestamp - offsetAdjustedLastFiringTime >=
                        recurringClockBasedTimer.firingTimePeriod()) {
                    FRAME_EXECUTOR.registerFrameBlockingEvent(recurringClockBasedTimer::fire);
                }
            }
        });
    }

    @Override
    public String getInterfaceName() {
        return ClockBasedTimerManager.class.getCanonicalName();
    }
}
