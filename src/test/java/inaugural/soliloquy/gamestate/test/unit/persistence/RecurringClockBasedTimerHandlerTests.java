package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RecurringClockBasedTimerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;


import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecurringClockBasedTimerHandlerTests {
    private final String ID = randomString();
    private final String CONSUMER_ID = randomString();
    @SuppressWarnings("rawtypes")
    private final Map<String, Consumer> CONSUMERS = mapOf();
    private final int PERIOD_DURATION = randomInt();
    private final int PERIOD_MODULO_OFFSET = randomInt();
    private final boolean FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED = randomBoolean();
    private final Long PAUSED_TIMESTAMP = randomLong();
    private final Long LAST_FIRED_TIMESTAMP = randomLong();

    @SuppressWarnings("rawtypes")
    @Mock private Consumer mockConsumer;
    @Mock private ClockBasedTimerFactory mockClockBasedTimerFactory;
    @Mock private RecurringClockBasedTimer mockRecurringClockBasedTimer;

    private TypeHandler<RecurringClockBasedTimer> handler;

    private final String WRITTEN_VALUE = String.format(
            "{\"id\":\"%s\",\"consumerId\":\"%s\",\"periodDuration\":%d,\"periodModuloOffset\":%d," +
                    "\"fireMultipleTimesPerPeriodElapsed\":%s,\"pausedTimestamp\":%d," +
                    "\"lastFiredTimestamp\":%d}",
            ID, CONSUMER_ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
            FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, PAUSED_TIMESTAMP,
            LAST_FIRED_TIMESTAMP);

    @BeforeEach
    public void setUp() {
        CONSUMERS.put(CONSUMER_ID, mockConsumer);

        lenient().when(mockRecurringClockBasedTimer.id()).thenReturn(ID);
        lenient().when(mockRecurringClockBasedTimer.consumerId()).thenReturn(CONSUMER_ID);
        lenient().when(mockRecurringClockBasedTimer.periodDuration()).thenReturn(PERIOD_DURATION);
        lenient().when(mockRecurringClockBasedTimer.periodModuloOffset())
                .thenReturn(PERIOD_MODULO_OFFSET);
        lenient().when(mockRecurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed())
                .thenReturn(FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED);
        lenient().when(mockRecurringClockBasedTimer.pausedTimestamp()).thenReturn(PAUSED_TIMESTAMP);
        lenient().when(mockRecurringClockBasedTimer.lastFiringTimestamp())
                .thenReturn(LAST_FIRED_TIMESTAMP);

        lenient().when(mockClockBasedTimerFactory.make(anyString(), anyInt(), anyInt(), any(),
                        anyBoolean(), anyLong(), anyLong()))
                .thenReturn(mockRecurringClockBasedTimer);

        handler = new RecurringClockBasedTimerHandler(mockClockBasedTimerFactory, CONSUMERS::get);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerHandler(null, CONSUMERS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringClockBasedTimerHandler(mockClockBasedTimerFactory, null));
    }

    @Test
    public void testWrite() {
        var result = handler.write(mockRecurringClockBasedTimer);

        assertEquals(WRITTEN_VALUE, result);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var result = handler.read(WRITTEN_VALUE);

        assertSame(mockRecurringClockBasedTimer, result);
        //noinspection unchecked
        verify(mockClockBasedTimerFactory).make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                mockConsumer, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, PAUSED_TIMESTAMP,
                LAST_FIRED_TIMESTAMP);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
