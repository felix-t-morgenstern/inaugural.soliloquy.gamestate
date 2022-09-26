package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.entities.timers.RecurringClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.factories.ClockBasedTimerFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import static org.junit.jupiter.api.Assertions.*;

class ClockBasedTimerFactoryImplTests {
    private final String ID = "ID";
    private final long FIRING_TIME = 789789L;
    private final int PERIOD_DURATION = 789;
    private final int PERIOD_MODULO_OFFSET = 456;
    private final long PAUSE_TIME = 123123L;
    @SuppressWarnings("FieldCanBeLocal")
    private final String FIRING_ACTION_ID = "firingActionId";
    private final FakeAction<Long> FIRING_ACTION = new FakeAction<>(FIRING_ACTION_ID);
    private final boolean FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED = true;
    private final long LAST_FIRING_TIMESTAMP = 123123L;
    private final long MOST_RECENT_TIMESTAMP = 456456L;

    private ClockBasedTimerFactory _clockBasedTimerFactory;

    @BeforeEach
    void setUp() {
        _clockBasedTimerFactory = new ClockBasedTimerFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ClockBasedTimerFactory.class.getCanonicalName(),
                _clockBasedTimerFactory.getInterfaceName());
    }

    @Test
    void testMakeOneTime() {
        OneTimeClockBasedTimer oneTimeClockBasedTimer = _clockBasedTimerFactory.make(
                ID,
                FIRING_TIME,
                FIRING_ACTION,
                PAUSE_TIME,
                MOST_RECENT_TIMESTAMP);

        assertNotNull(oneTimeClockBasedTimer);
        assertTrue(oneTimeClockBasedTimer instanceof OneTimeClockBasedTimerImpl);
        assertEquals(ID, oneTimeClockBasedTimer.id());
        assertEquals(FIRING_ACTION_ID, oneTimeClockBasedTimer.actionId());
        assertEquals(FIRING_TIME, oneTimeClockBasedTimer.firingTime());
        assertEquals(PAUSE_TIME, oneTimeClockBasedTimer.pausedTimestamp());
        assertEquals(MOST_RECENT_TIMESTAMP, oneTimeClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    void testMakeOneTimeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(null, FIRING_TIME, FIRING_ACTION, PAUSE_TIME,
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make("", FIRING_TIME, FIRING_ACTION, PAUSE_TIME,
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, FIRING_TIME, FIRING_ACTION, FIRING_TIME,
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, FIRING_TIME, null, FIRING_TIME - 1,
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, FIRING_TIME, FIRING_ACTION, PAUSE_TIME,
                        null));
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, FIRING_TIME, FIRING_ACTION,
                        MOST_RECENT_TIMESTAMP + 1, MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testMakeRecurring() {
        RecurringClockBasedTimer recurringClockBasedTimer = _clockBasedTimerFactory.make(
                ID,
                PERIOD_DURATION,
                PERIOD_MODULO_OFFSET,
                FIRING_ACTION,
                FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                PAUSE_TIME,
                LAST_FIRING_TIMESTAMP,
                MOST_RECENT_TIMESTAMP
        );

        assertNotNull(recurringClockBasedTimer);
        assertTrue(recurringClockBasedTimer instanceof RecurringClockBasedTimerImpl);
        assertEquals(ID, recurringClockBasedTimer.id());
        assertEquals(FIRING_ACTION_ID, recurringClockBasedTimer.actionId());
        assertEquals(PERIOD_DURATION, recurringClockBasedTimer.periodDuration());
        assertEquals(PERIOD_MODULO_OFFSET, recurringClockBasedTimer.periodModuloOffset());
        assertEquals(FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                recurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed());
        assertEquals(PAUSE_TIME, recurringClockBasedTimer.pausedTimestamp());
        assertEquals(LAST_FIRING_TIMESTAMP, recurringClockBasedTimer.lastFiringTimestamp());
        assertEquals(MOST_RECENT_TIMESTAMP, recurringClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    void testMakeRecurringWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(null, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        FIRING_ACTION, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                        PAUSE_TIME, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make("", PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        FIRING_ACTION, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                        PAUSE_TIME, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, 0, 0,
                        FIRING_ACTION, true, null, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, PERIOD_DURATION, -1,
                        FIRING_ACTION, true, null, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, PERIOD_DURATION, PERIOD_DURATION,
                        FIRING_ACTION, true, null, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        null, true, null, LAST_FIRING_TIMESTAMP, MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory.make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        FIRING_ACTION, true, LAST_FIRING_TIMESTAMP - 1, LAST_FIRING_TIMESTAMP,
                        MOST_RECENT_TIMESTAMP));

        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory
                        .make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET, FIRING_ACTION,
                                FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, PAUSE_TIME,
                                LAST_FIRING_TIMESTAMP, null));

        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerFactory
                        .make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET, FIRING_ACTION,
                                FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                                MOST_RECENT_TIMESTAMP + 1, LAST_FIRING_TIMESTAMP,
                                MOST_RECENT_TIMESTAMP));
    }
}
