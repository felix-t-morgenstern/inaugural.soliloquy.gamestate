package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.RecurringClockBasedTimerImpl;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.entities.Action.action;

@ExtendWith(MockitoExtension.class)
public class RecurringClockBasedTimerImplTests {
    private final String ID = "ID";
    private final int PERIOD_DURATION = 789;
    private final int PERIOD_MODULO_OFFSET = 456;
    private final String FIRING_ACTION_ID = randomString();
    private final boolean FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED = true;
    private final long LAST_FIRING_TIMESTAMP = 123123L;
    private final Long MOST_RECENT_TIMESTAMP = 456456L;

    @Mock private Action<Long> mockFiringAction;
    @Mock private TimestampValidator mockTimestampValidator;

    private RecurringClockBasedTimer recurringClockBasedTimer;

    @BeforeEach
    public void setUp() {
        lenient().when(mockFiringAction.id()).thenReturn(FIRING_ACTION_ID);

        recurringClockBasedTimer = new RecurringClockBasedTimerImpl(ID, PERIOD_DURATION,
                PERIOD_MODULO_OFFSET, mockFiringAction,
                FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, null, LAST_FIRING_TIMESTAMP,
                mockTimestampValidator);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl(null, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        mockFiringAction, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, null,
                        LAST_FIRING_TIMESTAMP, mockTimestampValidator));
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl("", PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        mockFiringAction, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, null,
                        LAST_FIRING_TIMESTAMP, mockTimestampValidator));

        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl(ID, 0, 0, mockFiringAction, true, null,
                        LAST_FIRING_TIMESTAMP, mockTimestampValidator));

        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl(ID, PERIOD_DURATION, -1, mockFiringAction, true,
                        null, LAST_FIRING_TIMESTAMP, mockTimestampValidator));

        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl(ID, PERIOD_DURATION, PERIOD_DURATION,
                        mockFiringAction, true, null, LAST_FIRING_TIMESTAMP, mockTimestampValidator));

        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        null, true, null, LAST_FIRING_TIMESTAMP, mockTimestampValidator));

        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        mockFiringAction, true, LAST_FIRING_TIMESTAMP - 1, LAST_FIRING_TIMESTAMP,
                        mockTimestampValidator));

        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        mockFiringAction, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                        MOST_RECENT_TIMESTAMP, LAST_FIRING_TIMESTAMP, null));

        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerImpl(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                        mockFiringAction, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                        MOST_RECENT_TIMESTAMP + 1, LAST_FIRING_TIMESTAMP, mockTimestampValidator));
    }

    @Test
    public void testId() {
        assertEquals(ID, recurringClockBasedTimer.id());
    }

    @Test
    public void testPeriodDuration() {
        assertEquals(PERIOD_DURATION, recurringClockBasedTimer.periodDuration());
    }

    @Test
    public void testPeriodModuloOffset() {
        assertEquals(PERIOD_MODULO_OFFSET, recurringClockBasedTimer.periodModuloOffset());
    }

    @Test
    public void testLastFiringTimestamp() {
        assertEquals(LAST_FIRING_TIMESTAMP, recurringClockBasedTimer.lastFiringTimestamp());
    }

    @Test
    public void testActionId() {
        assertEquals(FIRING_ACTION_ID, recurringClockBasedTimer.actionId());
    }

    @Test
    public void testFire() {
        var firingTime = MOST_RECENT_TIMESTAMP + 1;

        recurringClockBasedTimer.fire(firingTime);

        verify(mockFiringAction, once()).accept(firingTime);
        assertEquals(firingTime, recurringClockBasedTimer.lastFiringTimestamp());
    }

    @Test
    public void testFireMultipleTimesForMultiplePeriodsElapsed() {
        assertEquals(FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                recurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed());
    }

    @Test
    public void testFireWhenPaused() {
        long pausedTimestamp = MOST_RECENT_TIMESTAMP + 1;

        recurringClockBasedTimer.reportPause(pausedTimestamp);

        assertThrows(UnsupportedOperationException.class, () ->
                recurringClockBasedTimer.fire(pausedTimestamp));
    }

    @Test
    public void testPeriodModuloUpdatedOnUnpause() {
        long pauseTimestamp = 456456L;
        long timeToUnpause = 111L;

        recurringClockBasedTimer.reportPause(pauseTimestamp);
        recurringClockBasedTimer.reportUnpause(pauseTimestamp + timeToUnpause);

        assertEquals(PERIOD_MODULO_OFFSET - timeToUnpause,
                recurringClockBasedTimer.periodModuloOffset());
    }
}
