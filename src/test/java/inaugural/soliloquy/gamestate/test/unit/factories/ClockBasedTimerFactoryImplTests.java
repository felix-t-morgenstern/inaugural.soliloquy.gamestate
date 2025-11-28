package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.entities.timers.RecurringClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.factories.ClockBasedTimerFactoryImpl;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static soliloquy.specs.common.entities.Consumer.consumer;

@ExtendWith(MockitoExtension.class)
public class ClockBasedTimerFactoryImplTests {
    private final String ID = randomString();
    private final int PERIOD_DURATION = 789;
    private final int PERIOD_MODULO_OFFSET = 456;
    private final Long MOST_RECENT_TIMESTAMP = randomLong();
    private final Long PAUSE_TIME = randomLongWithInclusiveFloor(MOST_RECENT_TIMESTAMP + 1);;
    private final long FIRING_TIME = randomLongWithInclusiveFloor(PAUSE_TIME + 1);
    @SuppressWarnings("FieldCanBeLocal")
    private final String FIRING_CONSUMER_ID = randomString();
    private final Consumer<Long> FIRING_ACTION = consumer(FIRING_CONSUMER_ID, _ -> {});
    private final boolean FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED = true;
    private final long LAST_FIRING_TIMESTAMP = 123123L;

    @Mock private TimestampValidator mockTimestampValidator;

    private ClockBasedTimerFactory factory;

    @BeforeEach
    public void setUp() {
        lenient().when(mockTimestampValidator.mostRecentTimestamp())
                .thenReturn(MOST_RECENT_TIMESTAMP);

        factory = new ClockBasedTimerFactoryImpl(mockTimestampValidator);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new ClockBasedTimerFactoryImpl(null));
    }

    @Test
    public void testMakeOneTime() {
        var oneTimeClockBasedTimer = factory.make(
                ID,
                FIRING_TIME,
                FIRING_ACTION,
                null);
        oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertNotNull(oneTimeClockBasedTimer);
        assertInstanceOf(OneTimeClockBasedTimerImpl.class, oneTimeClockBasedTimer);
        assertEquals(ID, oneTimeClockBasedTimer.id());
        assertEquals(FIRING_CONSUMER_ID, oneTimeClockBasedTimer.consumerId());
        assertEquals(FIRING_TIME, oneTimeClockBasedTimer.firingTime());
        assertEquals(PAUSE_TIME, oneTimeClockBasedTimer.pausedTimestamp());
        verify(mockTimestampValidator, once()).validateTimestamp(PAUSE_TIME);
    }

    @Test
    public void testMakeOneTimeWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                factory.make(null, FIRING_TIME, FIRING_ACTION, PAUSE_TIME));
        assertThrows(IllegalArgumentException.class, () ->
                factory.make("", FIRING_TIME, FIRING_ACTION, PAUSE_TIME));
        assertThrows(IllegalArgumentException.class, () ->
                factory.make(ID, FIRING_TIME, FIRING_ACTION, FIRING_TIME));
        assertThrows(IllegalArgumentException.class, () ->
                factory.make(ID, FIRING_TIME, null, FIRING_TIME - 1));
    }

    @Test
    public void testMakeRecurring() {
        var recurringClockBasedTimer = factory.make(
                ID,
                PERIOD_DURATION,
                PERIOD_MODULO_OFFSET,
                FIRING_ACTION,
                FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                null,
                LAST_FIRING_TIMESTAMP
        );
        recurringClockBasedTimer.reportPause(PAUSE_TIME);

        assertNotNull(recurringClockBasedTimer);
        assertInstanceOf(RecurringClockBasedTimerImpl.class, recurringClockBasedTimer);
        assertEquals(ID, recurringClockBasedTimer.id());
        assertEquals(FIRING_CONSUMER_ID, recurringClockBasedTimer.consumerId());
        assertEquals(PERIOD_DURATION, recurringClockBasedTimer.periodDuration());
        assertEquals(PERIOD_MODULO_OFFSET, recurringClockBasedTimer.periodModuloOffset());
        assertEquals(FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED,
                recurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed());
        assertEquals(PAUSE_TIME, recurringClockBasedTimer.pausedTimestamp());
        assertEquals(LAST_FIRING_TIMESTAMP, recurringClockBasedTimer.lastFiringTimestamp());
        verify(mockTimestampValidator, once()).validateTimestamp(PAUSE_TIME);
    }

    @Test
    public void testMakeRecurringWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(null, PERIOD_DURATION, PERIOD_MODULO_OFFSET, FIRING_ACTION,
                        FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, PAUSE_TIME,
                        LAST_FIRING_TIMESTAMP));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make("", PERIOD_DURATION, PERIOD_MODULO_OFFSET, FIRING_ACTION,
                        FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, PAUSE_TIME,
                        LAST_FIRING_TIMESTAMP));

        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, 0, 0, FIRING_ACTION, true, null, LAST_FIRING_TIMESTAMP));

        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, PERIOD_DURATION, -1, FIRING_ACTION, true, null,
                        LAST_FIRING_TIMESTAMP));

        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, PERIOD_DURATION, PERIOD_DURATION, FIRING_ACTION, true, null,
                        LAST_FIRING_TIMESTAMP));

        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET, null, true, null,
                        LAST_FIRING_TIMESTAMP));

        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET, FIRING_ACTION, true,
                        LAST_FIRING_TIMESTAMP - 1, LAST_FIRING_TIMESTAMP));

        assertThrows(IllegalArgumentException.class,
                () -> factory.make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET, FIRING_ACTION,
                        FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, MOST_RECENT_TIMESTAMP + 1,
                        LAST_FIRING_TIMESTAMP));
    }
}
