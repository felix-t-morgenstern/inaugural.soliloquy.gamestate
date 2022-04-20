package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.ClockBasedTimerManagerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeFrameExecutor;
import inaugural.soliloquy.gamestate.test.fakes.FakeListFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeClockBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringClockBasedTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

import static org.junit.jupiter.api.Assertions.*;

class ClockBasedTimerManagerImplTests {
    private final FakeFrameExecutor FRAME_EXECUTOR = new FakeFrameExecutor();
    private final FakeListFactory LIST_FACTORY = new FakeListFactory();
    private final FakeOneTimeClockBasedTimer ONE_TIME_CLOCK_BASED_TIMER =
            new FakeOneTimeClockBasedTimer();
    private final FakeRecurringClockBasedTimer RECURRING_CLOCK_BASED_TIMER =
            new FakeRecurringClockBasedTimer();

    private final long GLOBAL_CLOCK_GLOBAL_TIMESTAMP = 100L;
    private final long FRAME_EXECUTOR_GLOBAL_TIMESTAMP = 123123L;

    private ClockBasedTimerManager _clockBasedTimerManager;

    @BeforeEach
    void setUp() {
        FRAME_EXECUTOR.GlobalTimestamp = FRAME_EXECUTOR_GLOBAL_TIMESTAMP;

        _clockBasedTimerManager = new ClockBasedTimerManagerImpl(FRAME_EXECUTOR, LIST_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new ClockBasedTimerManagerImpl(null, LIST_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new ClockBasedTimerManagerImpl(FRAME_EXECUTOR, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ClockBasedTimerManager.class.getCanonicalName(),
                _clockBasedTimerManager.getInterfaceName());
    }

    @Test
    void testFiresOneTimeTimerOnceAndOnlyOnce() {
        _clockBasedTimerManager.registerOneTimeTimer(ONE_TIME_CLOCK_BASED_TIMER);

        ONE_TIME_CLOCK_BASED_TIMER.FiringTime = GLOBAL_CLOCK_GLOBAL_TIMESTAMP + 1;

        _clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP);
        FRAME_EXECUTOR.execute();

        assertEquals(0, ONE_TIME_CLOCK_BASED_TIMER.FiredTimes.size());

        _clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP + 1);
        FRAME_EXECUTOR.execute();

        assertEquals(1, ONE_TIME_CLOCK_BASED_TIMER.FiredTimes.size());
        assertEquals(FRAME_EXECUTOR_GLOBAL_TIMESTAMP,
                ONE_TIME_CLOCK_BASED_TIMER.FiredTimes.get(0));

        FRAME_EXECUTOR.RegisteredFrameBlockingEvents.clear();

        _clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP + 2);
        FRAME_EXECUTOR.execute();

        assertEquals(1, ONE_TIME_CLOCK_BASED_TIMER.FiredTimes.size());
    }

    @Test
    void testFiresRecurringTimerSingleTimeForMultiplePeriodsElapsed() {
        long globalClockGlobalTimestamp = 100;
        RECURRING_CLOCK_BASED_TIMER.FireMultipleTimesForMultiplePeriodsElapsed = false;
        RECURRING_CLOCK_BASED_TIMER.LastFiringTimestamp = 91;
        RECURRING_CLOCK_BASED_TIMER.PeriodDuration = 10;
        RECURRING_CLOCK_BASED_TIMER.PeriodModuloOffset = 1;

        _clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER);

        _clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp);
        FRAME_EXECUTOR.execute();

        assertEquals(0, RECURRING_CLOCK_BASED_TIMER.FiredTimes.size());

        _clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp + 1);
        FRAME_EXECUTOR.execute();

        assertEquals(1, RECURRING_CLOCK_BASED_TIMER.FiredTimes.size());
        assertEquals(FRAME_EXECUTOR_GLOBAL_TIMESTAMP,
                RECURRING_CLOCK_BASED_TIMER.FiredTimes.get(0));

        RECURRING_CLOCK_BASED_TIMER.FiredTimes.clear();
        FRAME_EXECUTOR.RegisteredFrameBlockingEvents.clear();

        _clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp + 51);
        FRAME_EXECUTOR.execute();

        assertEquals(1, RECURRING_CLOCK_BASED_TIMER.FiredTimes.size());
        assertEquals(FRAME_EXECUTOR_GLOBAL_TIMESTAMP,
                RECURRING_CLOCK_BASED_TIMER.FiredTimes.get(0));
    }

    @Test
    void testFiresRecurringTimerMultipleTimesForMultiplePeriodsElapsed() {
        long globalClockGlobalTimestamp = 100;
        RECURRING_CLOCK_BASED_TIMER.FireMultipleTimesForMultiplePeriodsElapsed = true;
        RECURRING_CLOCK_BASED_TIMER.LastFiringTimestamp = 91;
        RECURRING_CLOCK_BASED_TIMER.PeriodDuration = 10;
        RECURRING_CLOCK_BASED_TIMER.PeriodModuloOffset = 1;

        _clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER);

        _clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp);
        FRAME_EXECUTOR.execute();

        assertEquals(0, RECURRING_CLOCK_BASED_TIMER.FiredTimes.size());

        _clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp + 1);
        FRAME_EXECUTOR.execute();

        assertEquals(1, RECURRING_CLOCK_BASED_TIMER.FiredTimes.size());
        assertEquals(FRAME_EXECUTOR_GLOBAL_TIMESTAMP,
                RECURRING_CLOCK_BASED_TIMER.FiredTimes.get(0));

        RECURRING_CLOCK_BASED_TIMER.FiredTimes.clear();
        FRAME_EXECUTOR.RegisteredFrameBlockingEvents.clear();

        _clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp + 51);
        FRAME_EXECUTOR.execute();

        assertEquals(6, RECURRING_CLOCK_BASED_TIMER.FiredTimes.size());
        RECURRING_CLOCK_BASED_TIMER.FiredTimes.forEach(firedTime ->
                assertEquals(FRAME_EXECUTOR_GLOBAL_TIMESTAMP, firedTime));
    }

    @Test
    void testDeregisterOneTimeTimer() {
        _clockBasedTimerManager.registerOneTimeTimer(ONE_TIME_CLOCK_BASED_TIMER);
        ONE_TIME_CLOCK_BASED_TIMER.FiringTime = GLOBAL_CLOCK_GLOBAL_TIMESTAMP;

        _clockBasedTimerManager.deregisterOneTimeTimer(ONE_TIME_CLOCK_BASED_TIMER.id());

        _clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP);
        FRAME_EXECUTOR.execute();

        assertEquals(0, ONE_TIME_CLOCK_BASED_TIMER.FiredTimes.size());
    }

    @Test
    void testDeregisterRecurringTimer() {
        long globalClockGlobalTimestamp = 100;
        RECURRING_CLOCK_BASED_TIMER.FireMultipleTimesForMultiplePeriodsElapsed = false;
        RECURRING_CLOCK_BASED_TIMER.LastFiringTimestamp = 90;
        RECURRING_CLOCK_BASED_TIMER.PeriodDuration = 10;
        RECURRING_CLOCK_BASED_TIMER.PeriodModuloOffset = 0;
        _clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER);

        _clockBasedTimerManager.deregisterRecurringTimer(RECURRING_CLOCK_BASED_TIMER.id());

        _clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp);
        FRAME_EXECUTOR.execute();

        assertEquals(0, RECURRING_CLOCK_BASED_TIMER.FiredTimes.size());
    }

    @Test
    void testRegisterOneTimeTimerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _clockBasedTimerManager.registerOneTimeTimer(null));
    }

    @Test
    void testRegisterRecurringTimerWithInvalidParams() {
        RECURRING_CLOCK_BASED_TIMER.PeriodModuloOffset =
                RECURRING_CLOCK_BASED_TIMER.PeriodDuration = 123;

        assertThrows(IllegalArgumentException.class,
                () -> _clockBasedTimerManager.registerRecurringTimer(null));
        assertThrows(IllegalArgumentException.class,
                () -> _clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER));
    }

    @Test
    void testDeregisterOneTimeTimerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _clockBasedTimerManager.deregisterOneTimeTimer(null));
    }

    @Test
    void testDeregisterRecurringTimerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _clockBasedTimerManager.deregisterRecurringTimer(null));
    }

    @Test
    void testFireTimersWithOutdatedTimestamp() {
        _clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP);

        assertThrows(IllegalArgumentException.class,
                () -> _clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class,
                () -> _clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP));

        _clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP + 1);
    }

    @Test
    void testOneTimeTimersRepresentation() {
        FakeOneTimeClockBasedTimer oneTimeClockBasedTimer1 = new FakeOneTimeClockBasedTimer();
        FakeOneTimeClockBasedTimer oneTimeClockBasedTimer2 = new FakeOneTimeClockBasedTimer();
        FakeOneTimeClockBasedTimer oneTimeClockBasedTimer3 = new FakeOneTimeClockBasedTimer();
        _clockBasedTimerManager.registerOneTimeTimer(oneTimeClockBasedTimer1);
        _clockBasedTimerManager.registerOneTimeTimer(oneTimeClockBasedTimer2);
        _clockBasedTimerManager.registerOneTimeTimer(oneTimeClockBasedTimer3);

        List<OneTimeClockBasedTimer> oneTimeTimersRepresentation =
                _clockBasedTimerManager.oneTimeTimersRepresentation();
        List<OneTimeClockBasedTimer> oneTimeTimersRepresentation2 =
                _clockBasedTimerManager.oneTimeTimersRepresentation();

        _clockBasedTimerManager.deregisterOneTimeTimer(oneTimeClockBasedTimer1.id());

        assertNotNull(oneTimeTimersRepresentation);
        assertNotSame(oneTimeTimersRepresentation, oneTimeTimersRepresentation2);
        assertEquals(3, oneTimeTimersRepresentation.size());
        assertTrue(oneTimeTimersRepresentation.contains(oneTimeClockBasedTimer1));
        assertTrue(oneTimeTimersRepresentation.contains(oneTimeClockBasedTimer2));
        assertTrue(oneTimeTimersRepresentation.contains(oneTimeClockBasedTimer3));
        assertNotNull(oneTimeTimersRepresentation.getArchetype());
        assertEquals(OneTimeClockBasedTimer.class.getCanonicalName(),
                oneTimeTimersRepresentation.getArchetype().getInterfaceName());
    }

    @Test
    void testRecurringTimersRepresentation() {
        FakeRecurringClockBasedTimer recurringClockBasedTimer1 =
                new FakeRecurringClockBasedTimer();
        FakeRecurringClockBasedTimer recurringClockBasedTimer2 =
                new FakeRecurringClockBasedTimer();
        FakeRecurringClockBasedTimer recurringClockBasedTimer3 =
                new FakeRecurringClockBasedTimer();
        recurringClockBasedTimer1.PeriodDuration = 1;
        recurringClockBasedTimer2.PeriodDuration = 1;
        recurringClockBasedTimer3.PeriodDuration = 1;
        _clockBasedTimerManager.registerRecurringTimer(recurringClockBasedTimer1);
        _clockBasedTimerManager.registerRecurringTimer(recurringClockBasedTimer2);
        _clockBasedTimerManager.registerRecurringTimer(recurringClockBasedTimer3);

        List<RecurringClockBasedTimer> recurringTimersRepresentation =
                _clockBasedTimerManager.recurringTimersRepresentation();
        List<RecurringClockBasedTimer> recurringTimersRepresentation2 =
                _clockBasedTimerManager.recurringTimersRepresentation();

        _clockBasedTimerManager.deregisterRecurringTimer(recurringClockBasedTimer1.id());

        assertNotNull(recurringTimersRepresentation);
        assertNotSame(recurringTimersRepresentation, recurringTimersRepresentation2);
        assertEquals(3, recurringTimersRepresentation.size());
        assertTrue(recurringTimersRepresentation.contains(recurringClockBasedTimer1));
        assertTrue(recurringTimersRepresentation.contains(recurringClockBasedTimer2));
        assertTrue(recurringTimersRepresentation.contains(recurringClockBasedTimer3));
        assertNotNull(recurringTimersRepresentation.getArchetype());
        assertEquals(RecurringClockBasedTimer.class.getCanonicalName(),
                recurringTimersRepresentation.getArchetype().getInterfaceName());
    }
}
