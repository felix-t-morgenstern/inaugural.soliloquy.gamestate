package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.graphics.rendering.FrameExecutor;

import java.util.ArrayList;

public class ClockBasedTimerManagerImpl implements ClockBasedTimerManager {
    private final FrameExecutor FRAME_EXECUTOR;
    private final ListFactory LIST_FACTORY;
    private final ArrayList<OneTimeClockBasedTimer> ONE_TIME_CLOCK_BASED_TIMERS;
    private final ArrayList<RecurringClockBasedTimer> RECURRING_CLOCK_BASED_TIMERS;

    private static final OneTimeClockBasedTimerArchetype ONE_TIME_CLOCK_BASED_TIMER_ARCHETYPE =
            new OneTimeClockBasedTimerArchetype();
    private static final RecurringClockBasedTimerArchetype RECURRING_CLOCK_BASED_TIMER_ARCHETYPE =
            new RecurringClockBasedTimerArchetype();

    private Long _mostRecentFireTimersTimestamp;

    public ClockBasedTimerManagerImpl(FrameExecutor frameExecutor, ListFactory listFactory) {
        FRAME_EXECUTOR = Check.ifNull(frameExecutor, "frameExecutor");
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
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
        if (recurringClockBasedTimer.periodModuloOffset() >=
                recurringClockBasedTimer.periodDuration()) {
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
                    timestamp - recurringClockBasedTimer.periodModuloOffset();
            long offsetAdjustedLastFiringTime = recurringClockBasedTimer.lastFiringTimestamp()
                    - recurringClockBasedTimer.periodModuloOffset();
            if (recurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed()) {
                int numberOfTimesToFire =
                        (int)(offsetAdjustedTimestamp - offsetAdjustedLastFiringTime)
                                / recurringClockBasedTimer.periodDuration();
                while(numberOfTimesToFire-- > 0) {
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
        return LIST_FACTORY.make(ONE_TIME_CLOCK_BASED_TIMERS,
                ONE_TIME_CLOCK_BASED_TIMER_ARCHETYPE);
    }

    @Override
    public List<RecurringClockBasedTimer> recurringTimersRepresentation() {
        return LIST_FACTORY.make(RECURRING_CLOCK_BASED_TIMERS,
                RECURRING_CLOCK_BASED_TIMER_ARCHETYPE);
    }

    @Override
    public String getInterfaceName() {
        return ClockBasedTimerManager.class.getCanonicalName();
    }

    private static class OneTimeClockBasedTimerArchetype implements OneTimeClockBasedTimer {
        @Override
        public long firingTime() {
            return 0;
        }

        @Override
        public String actionId() {
            return null;
        }

        @Override
        public void fire(long l) throws UnsupportedOperationException {

        }

        @Override
        public void reportPause(long l) throws IllegalArgumentException {

        }

        @Override
        public void reportUnpause(long l) throws IllegalArgumentException {

        }

        @Override
        public Long pausedTimestamp() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return OneTimeClockBasedTimer.class.getCanonicalName();
        }

        @Override
        public Long mostRecentTimestamp() {
            return null;
        }

        @Override
        public String id() throws IllegalStateException {
            return null;
        }
    }

    private static class RecurringClockBasedTimerArchetype implements RecurringClockBasedTimer {

        @Override
        public long lastFiringTimestamp() {
            return 0;
        }

        @Override
        public boolean fireMultipleTimesForMultiplePeriodsElapsed() {
            return false;
        }

        @Override
        public int periodDuration() {
            return 0;
        }

        @Override
        public int periodModuloOffset() {
            return 0;
        }

        @Override
        public String actionId() {
            return null;
        }

        @Override
        public void fire(long l) throws UnsupportedOperationException {

        }

        @Override
        public void reportPause(long l) throws IllegalArgumentException {

        }

        @Override
        public void reportUnpause(long l) throws IllegalArgumentException {

        }

        @Override
        public Long pausedTimestamp() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return RecurringClockBasedTimer.class.getCanonicalName();
        }

        @Override
        public Long mostRecentTimestamp() {
            return null;
        }

        @Override
        public String id() throws IllegalStateException {
            return null;
        }
    }
}
