package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.RecurringClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

import static org.junit.jupiter.api.Assertions.*;

class RecurringClockBasedTimerImplTests {
    private final int PERIOD_DURATION = 789;
    private final int PERIOD_MODULO_OFFSET = 456;
    private final String FIRING_ACTION_ID = "firingActionId";
    private final FakeAction<Long> FIRING_ACTION = new FakeAction<>(FIRING_ACTION_ID);
    private final boolean FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED = true;
    private final long LAST_FIRING_TIMESTAMP = 123123L;
    private final long MOST_RECENT_TIMESTAMP = 456456L;

    private RecurringClockBasedTimer _recurringClockBasedTimer;

    @BeforeEach
    void setUp() {
        _recurringClockBasedTimer = new RecurringClockBasedTimerImpl(PERIOD_DURATION,
                PERIOD_MODULO_OFFSET, FIRING_ACTION,
                FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, null, LAST_FIRING_TIMESTAMP,
                MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringClockBasedTimerImpl(0, 0,
                        FIRING_ACTION, true, null, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                new RecurringClockBasedTimerImpl(PERIOD_DURATION, -1,
                        FIRING_ACTION, true, null, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                new RecurringClockBasedTimerImpl(PERIOD_DURATION, PERIOD_DURATION,
                        FIRING_ACTION, true, null, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                new RecurringClockBasedTimerImpl(PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        null, true, null, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                new RecurringClockBasedTimerImpl(PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        FIRING_ACTION, true, LAST_FIRING_TIMESTAMP - 1, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                new RecurringClockBasedTimerImpl(PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        FIRING_ACTION, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                        MOST_RECENT_TIMESTAMP, LAST_FIRING_TIMESTAMP, null));

        assertThrows(IllegalArgumentException.class, () ->
                new RecurringClockBasedTimerImpl(PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        FIRING_ACTION, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                        MOST_RECENT_TIMESTAMP + 1, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RecurringClockBasedTimer.class.getCanonicalName(),
                _recurringClockBasedTimer.getInterfaceName());
    }

    @Test
    void testPeriodDuration() {
        assertEquals(PERIOD_DURATION, _recurringClockBasedTimer.periodDuration());
    }

    @Test
    void testPeriodModuloOffset() {
        assertEquals(PERIOD_MODULO_OFFSET, _recurringClockBasedTimer.periodModuloOffset());
    }

    @Test
    void testLastFiringTimestamp() {
        assertEquals(LAST_FIRING_TIMESTAMP, _recurringClockBasedTimer.lastFiringTimestamp());
    }

    @Test
    void testActionId() {
        assertEquals(FIRING_ACTION_ID, _recurringClockBasedTimer.actionId());
    }

    @Test
    void testFire() {
        long firingTime = MOST_RECENT_TIMESTAMP + 1;

        _recurringClockBasedTimer.fire(firingTime);

        assertEquals(firingTime, FIRING_ACTION._mostRecentInput);
        assertTrue(FIRING_ACTION._actionRun);
        assertEquals(firingTime, _recurringClockBasedTimer.lastFiringTimestamp());
    }

    @Test
    void testFireMultipleTimesForMultiplePeriodsElapsed() {
        assertEquals(FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                _recurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed());
    }

    @Test
    void testFireWhenPaused() {
        long pausedTimestamp = MOST_RECENT_TIMESTAMP + 1;

        _recurringClockBasedTimer.reportPause(pausedTimestamp);

        assertThrows(UnsupportedOperationException.class, () ->
                _recurringClockBasedTimer.fire(pausedTimestamp));
    }

    @Test
    void testPeriodModuloUpdatedOnUnpause() {
        long pauseTimestamp = 456456L;
        long timeToUnpause = 111L;

        _recurringClockBasedTimer.reportPause(pauseTimestamp);
        _recurringClockBasedTimer.reportUnpause(pauseTimestamp + timeToUnpause);

        assertEquals(PERIOD_MODULO_OFFSET - timeToUnpause,
                _recurringClockBasedTimer.periodModuloOffset());
    }

    @Test
    void testMostRecentTimestamp() {
        assertEquals(MOST_RECENT_TIMESTAMP, _recurringClockBasedTimer.mostRecentTimestamp());
    }
}
