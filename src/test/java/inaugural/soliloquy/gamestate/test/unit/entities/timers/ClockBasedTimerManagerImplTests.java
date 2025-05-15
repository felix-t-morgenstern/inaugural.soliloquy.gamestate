package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.ClockBasedTimerManagerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeClockBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringClockBasedTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.graphics.rendering.FrameExecutor;

import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClockBasedTimerManagerImplTests {
    private final FakeOneTimeClockBasedTimer ONE_TIME_CLOCK_BASED_TIMER =
            new FakeOneTimeClockBasedTimer();
    private final FakeRecurringClockBasedTimer RECURRING_CLOCK_BASED_TIMER =
            new FakeRecurringClockBasedTimer();

    private final long GLOBAL_CLOCK_GLOBAL_TIMESTAMP = 100L;

    @Mock private FrameExecutor mockFrameExecutor;

    private ClockBasedTimerManager clockBasedTimerManager;

    @BeforeEach
    public void setUp() {
        RECURRING_CLOCK_BASED_TIMER.FireMultipleTimesForMultiplePeriodsElapsed = false;
        RECURRING_CLOCK_BASED_TIMER.LastFiringTimestamp = 90;
        RECURRING_CLOCK_BASED_TIMER.PeriodDuration = 10;
        RECURRING_CLOCK_BASED_TIMER.PeriodModuloOffset = 0;

        mockFrameExecutor = mock(FrameExecutor.class);

        clockBasedTimerManager = new ClockBasedTimerManagerImpl(mockFrameExecutor);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                new ClockBasedTimerManagerImpl(null));
    }

    // NB: Many of the following tests use 'any()' instead of 'eq
    // (ONE_TIME_CLOCK_BASED_TIMER::run)' etc., since two references to the same function are
    // neither the same reference, nor even equal, so there is no real argument matcher that can
    // work here. Instead, simply having the firing behavior depend on the properties of the
    // timer in question (one-time or recurring) is used as a proxy to ensure that the right type
    // of timer is being fed to FRAME_EXECUTOR, but that's obviously no guarantee.
    @Test
    public void testFiresOneTimeTimerOnceAndOnlyOnce() {
        clockBasedTimerManager.registerOneTimeTimer(ONE_TIME_CLOCK_BASED_TIMER);

        ONE_TIME_CLOCK_BASED_TIMER.FiringTime = GLOBAL_CLOCK_GLOBAL_TIMESTAMP + 1;

        clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP);

        verify(mockFrameExecutor, never()).registerFrameBlockingEvent(any());

        clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP + 1);

        verify(mockFrameExecutor, once()).registerFrameBlockingEvent(any());

        clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP + 2);

        verify(mockFrameExecutor, once()).registerFrameBlockingEvent(any());
    }

    @Test
    public void testFiresRecurringTimerSingleTimeForMultiplePeriodsElapsed() {
        long globalClockGlobalTimestamp = 100;
        RECURRING_CLOCK_BASED_TIMER.FireMultipleTimesForMultiplePeriodsElapsed = false;
        RECURRING_CLOCK_BASED_TIMER.LastFiringTimestamp = 91;
        RECURRING_CLOCK_BASED_TIMER.PeriodDuration = 10;
        RECURRING_CLOCK_BASED_TIMER.PeriodModuloOffset = 1;

        clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER);

        clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp);

        verify(mockFrameExecutor, never()).registerFrameBlockingEvent(any());

        clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp + 1);

        verify(mockFrameExecutor, once()).registerFrameBlockingEvent(any());

        clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp + 51);

        verify(mockFrameExecutor, times(2)).registerFrameBlockingEvent(any());
    }

    @Test
    public void testFiresRecurringTimerMultipleTimesForMultiplePeriodsElapsed() {
        long globalClockGlobalTimestamp = 100;
        RECURRING_CLOCK_BASED_TIMER.FireMultipleTimesForMultiplePeriodsElapsed = true;
        RECURRING_CLOCK_BASED_TIMER.LastFiringTimestamp = 91;
        RECURRING_CLOCK_BASED_TIMER.PeriodDuration = 10;
        RECURRING_CLOCK_BASED_TIMER.PeriodModuloOffset = 1;

        clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER);

        clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp);

        verify(mockFrameExecutor, never()).registerFrameBlockingEvent(any());

        clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp + 1);

        verify(mockFrameExecutor, once()).registerFrameBlockingEvent(any());

        clockBasedTimerManager.fireTimers(globalClockGlobalTimestamp + 51);

        verify(mockFrameExecutor, times(7)).registerFrameBlockingEvent(any());
    }

    @Test
    public void testDeregisterOneTimeTimer() {
        clockBasedTimerManager.registerOneTimeTimer(ONE_TIME_CLOCK_BASED_TIMER);
        ONE_TIME_CLOCK_BASED_TIMER.FiringTime = GLOBAL_CLOCK_GLOBAL_TIMESTAMP;

        clockBasedTimerManager.deregisterOneTimeTimer(ONE_TIME_CLOCK_BASED_TIMER.id());
        clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP);

        verify(mockFrameExecutor, never()).registerFrameBlockingEvent(any());
    }

    @Test
    public void testDeregisterRecurringTimer() {
        clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER);

        clockBasedTimerManager.deregisterRecurringTimer(RECURRING_CLOCK_BASED_TIMER.id());
        clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP);

        verify(mockFrameExecutor, never()).registerFrameBlockingEvent(any());
    }

    @Test
    public void testRegisterOneTimeTimerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> clockBasedTimerManager.registerOneTimeTimer(null));
    }

    @Test
    public void testRegisterRecurringTimerWithInvalidArgs() {
        RECURRING_CLOCK_BASED_TIMER.PeriodModuloOffset =
                RECURRING_CLOCK_BASED_TIMER.PeriodDuration = 123;

        assertThrows(IllegalArgumentException.class,
                () -> clockBasedTimerManager.registerRecurringTimer(null));
        assertThrows(IllegalArgumentException.class,
                () -> clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER));
    }

    @Test
    public void testDeregisterOneTimeTimerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> clockBasedTimerManager.deregisterOneTimeTimer(null));
    }

    @Test
    public void testDeregisterRecurringTimerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> clockBasedTimerManager.deregisterRecurringTimer(null));
    }

    @Test
    public void testClear() {
        clockBasedTimerManager.registerOneTimeTimer(ONE_TIME_CLOCK_BASED_TIMER);
        clockBasedTimerManager.registerRecurringTimer(RECURRING_CLOCK_BASED_TIMER);

        clockBasedTimerManager.clear();

        assertTrue(clockBasedTimerManager.oneTimeTimersRepresentation().isEmpty());
        assertTrue(clockBasedTimerManager.recurringTimersRepresentation().isEmpty());
    }

    @Test
    public void testFireTimersWithOutdatedTimestamp() {
        clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP);

        assertThrows(IllegalArgumentException.class,
                () -> clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class,
                () -> clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP));

        clockBasedTimerManager.fireTimers(GLOBAL_CLOCK_GLOBAL_TIMESTAMP + 1);
    }

    @Test
    public void testOneTimeTimersRepresentation() {
        FakeOneTimeClockBasedTimer oneTimeClockBasedTimer1 = new FakeOneTimeClockBasedTimer();
        FakeOneTimeClockBasedTimer oneTimeClockBasedTimer2 = new FakeOneTimeClockBasedTimer();
        FakeOneTimeClockBasedTimer oneTimeClockBasedTimer3 = new FakeOneTimeClockBasedTimer();
        clockBasedTimerManager.registerOneTimeTimer(oneTimeClockBasedTimer1);
        clockBasedTimerManager.registerOneTimeTimer(oneTimeClockBasedTimer2);
        clockBasedTimerManager.registerOneTimeTimer(oneTimeClockBasedTimer3);

        var oneTimeTimersRepresentation = clockBasedTimerManager.oneTimeTimersRepresentation();
        var oneTimeTimersRepresentation2 = clockBasedTimerManager.oneTimeTimersRepresentation();

        clockBasedTimerManager.deregisterOneTimeTimer(oneTimeClockBasedTimer1.id());

        assertNotNull(oneTimeTimersRepresentation);
        assertNotSame(oneTimeTimersRepresentation, oneTimeTimersRepresentation2);
        assertEquals(3, oneTimeTimersRepresentation.size());
        assertTrue(oneTimeTimersRepresentation.contains(oneTimeClockBasedTimer1));
        assertTrue(oneTimeTimersRepresentation.contains(oneTimeClockBasedTimer2));
        assertTrue(oneTimeTimersRepresentation.contains(oneTimeClockBasedTimer3));
    }

    @Test
    public void testRecurringTimersRepresentation() {
        FakeRecurringClockBasedTimer recurringClockBasedTimer1 =
                new FakeRecurringClockBasedTimer();
        FakeRecurringClockBasedTimer recurringClockBasedTimer2 =
                new FakeRecurringClockBasedTimer();
        FakeRecurringClockBasedTimer recurringClockBasedTimer3 =
                new FakeRecurringClockBasedTimer();
        recurringClockBasedTimer1.PeriodDuration = 1;
        recurringClockBasedTimer2.PeriodDuration = 1;
        recurringClockBasedTimer3.PeriodDuration = 1;
        clockBasedTimerManager.registerRecurringTimer(recurringClockBasedTimer1);
        clockBasedTimerManager.registerRecurringTimer(recurringClockBasedTimer2);
        clockBasedTimerManager.registerRecurringTimer(recurringClockBasedTimer3);

        var recurringTimersRepresentation = clockBasedTimerManager.recurringTimersRepresentation();
        var recurringTimersRepresentation2 = clockBasedTimerManager.recurringTimersRepresentation();

        clockBasedTimerManager.deregisterRecurringTimer(recurringClockBasedTimer1.id());

        assertNotNull(recurringTimersRepresentation);
        assertNotSame(recurringTimersRepresentation, recurringTimersRepresentation2);
        assertEquals(3, recurringTimersRepresentation.size());
        assertTrue(recurringTimersRepresentation.contains(recurringClockBasedTimer1));
        assertTrue(recurringTimersRepresentation.contains(recurringClockBasedTimer2));
        assertTrue(recurringTimersRepresentation.contains(recurringClockBasedTimer3));
    }
}
