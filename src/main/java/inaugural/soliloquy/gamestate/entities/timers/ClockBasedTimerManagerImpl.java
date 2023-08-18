package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.graphics.rendering.FrameExecutor;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class ClockBasedTimerManagerImpl implements ClockBasedTimerManager {
    private final FrameExecutor FRAME_EXECUTOR;
    private final Map<String, OneTimeClockBasedTimer> ONE_TIME_CLOCK_BASED_TIMERS;
    private final Map<String, RecurringClockBasedTimer> RECURRING_CLOCK_BASED_TIMERS;

    private Long mostRecentFireTimersTimestamp;

    public ClockBasedTimerManagerImpl(FrameExecutor frameExecutor) {
        FRAME_EXECUTOR = Check.ifNull(frameExecutor, "frameExecutor");
        ONE_TIME_CLOCK_BASED_TIMERS = mapOf();
        RECURRING_CLOCK_BASED_TIMERS = mapOf();
    }

    @Override
    public void registerOneTimeTimer(OneTimeClockBasedTimer oneTimeClockBasedTimer)
            throws IllegalArgumentException {
        Check.ifNull(oneTimeClockBasedTimer, "oneTimeClockBasedTimer");
        ONE_TIME_CLOCK_BASED_TIMERS.put(oneTimeClockBasedTimer.id(), oneTimeClockBasedTimer);
    }

    @Override
    public void registerRecurringTimer(RecurringClockBasedTimer recurringClockBasedTimer)
            throws IllegalArgumentException {
        Check.ifNull(recurringClockBasedTimer, "recurringClockBasedTimer");
        if (recurringClockBasedTimer.periodModuloOffset() >=
                recurringClockBasedTimer.periodDuration()) {
            throw new IllegalArgumentException(
                    "ClockBasedTimerManagerImpl.registerRecurringTimer: firingTimeModuloOffset " +
                            "cannot be greater than or equal to firingTimePeriod");
        }
        RECURRING_CLOCK_BASED_TIMERS.put(recurringClockBasedTimer.id(), recurringClockBasedTimer);
    }

    @Override
    public void deregisterOneTimeTimer(String id)
            throws IllegalArgumentException {
        ONE_TIME_CLOCK_BASED_TIMERS.remove(
                Check.ifNull(id, "id"));
    }

    @Override
    public void deregisterRecurringTimer(String id)
            throws IllegalArgumentException {
        RECURRING_CLOCK_BASED_TIMERS.remove(Check.ifNull(id, "id"));
    }

    @Override
    public void clear() {
        ONE_TIME_CLOCK_BASED_TIMERS.clear();
        RECURRING_CLOCK_BASED_TIMERS.clear();
    }

    @Override
    public void fireTimers(long timestamp) {
        if (mostRecentFireTimersTimestamp != null) {
            if (timestamp <= mostRecentFireTimersTimestamp) {
                throw new IllegalArgumentException("ClockBasedTimerManagerImpl.fireTimers: " +
                        "this method cannot be invoked at the same or a prior timestamp");
            }
        }
        mostRecentFireTimersTimestamp = timestamp;

        // NB: This list is created to avoid a ConcurrentModificationException
        List<String> oneTimeClockBasedTimersToRemove = listOf();
        ONE_TIME_CLOCK_BASED_TIMERS.values().forEach(oneTimeClockBasedTimer -> {
            if (oneTimeClockBasedTimer.firingTime() <= timestamp) {
                FRAME_EXECUTOR.registerFrameBlockingEvent(oneTimeClockBasedTimer::fire);
                oneTimeClockBasedTimersToRemove.add(oneTimeClockBasedTimer.id());
            }
        });
        oneTimeClockBasedTimersToRemove.forEach(ONE_TIME_CLOCK_BASED_TIMERS::remove);
        RECURRING_CLOCK_BASED_TIMERS.values().forEach(recurringClockBasedTimer -> {
            var offsetAdjustedTimestamp =
                    timestamp - recurringClockBasedTimer.periodModuloOffset();
            var offsetAdjustedLastFiringTime = recurringClockBasedTimer.lastFiringTimestamp()
                    - recurringClockBasedTimer.periodModuloOffset();
            if (recurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed()) {
                var numberOfTimesToFire =
                        (int) (offsetAdjustedTimestamp - offsetAdjustedLastFiringTime)
                                / recurringClockBasedTimer.periodDuration();
                while (numberOfTimesToFire-- > 0) {
                    FRAME_EXECUTOR.registerFrameBlockingEvent(recurringClockBasedTimer::fire);
                }
            }
            else {
                if (offsetAdjustedTimestamp - offsetAdjustedLastFiringTime >=
                        recurringClockBasedTimer.periodDuration()) {
                    FRAME_EXECUTOR.registerFrameBlockingEvent(recurringClockBasedTimer::fire);
                }
            }
        });
    }

    @Override
    public List<OneTimeClockBasedTimer> oneTimeTimersRepresentation() {
        return listOf(ONE_TIME_CLOCK_BASED_TIMERS.values());
    }

    @Override
    public List<RecurringClockBasedTimer> recurringTimersRepresentation() {
        return listOf(RECURRING_CLOCK_BASED_TIMERS.values());
    }

    @Override
    public String getInterfaceName() {
        return ClockBasedTimerManager.class.getCanonicalName();
    }
}
