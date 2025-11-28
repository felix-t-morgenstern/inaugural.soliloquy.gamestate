package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.OneTimeClockBasedTimerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomLong;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OneTimeClockBasedTimerHandlerTests {
    private final String ONE_TIME_CLOCK_TIMER_ID = randomString();
    private final String CONSUMER_ID = randomString();
    private final long FIRING_TIME = randomLong();
    private final long PAUSE_TIME = randomLong();

    @SuppressWarnings("rawtypes")
    @Mock private Consumer mockConsumer;
    @Mock private OneTimeClockBasedTimer mockOneTimeClockBasedTimer;
    @Mock private ClockBasedTimerFactory mockClockBasedTimerFactory;

    @SuppressWarnings("rawtypes")
    private Map<String, Consumer> actions = mapOf(pairOf(CONSUMER_ID, mockConsumer));

    private TypeHandler<OneTimeClockBasedTimer> handler;

    private final String WRITTEN_VALUE = String.format(
            "{\"id\":\"%s\",\"consumerId\":\"%s\",\"firingTime\":%d,\"pausedTime\":%d}",
            ONE_TIME_CLOCK_TIMER_ID, CONSUMER_ID, FIRING_TIME, PAUSE_TIME);

    @BeforeEach
    public void setUp() {
        lenient().when(mockConsumer.id()).thenReturn(CONSUMER_ID);

        lenient().when(mockClockBasedTimerFactory.make(anyString(), anyLong(), any(), anyLong()))
                .thenReturn(mockOneTimeClockBasedTimer);

        actions = mapOf(pairOf(CONSUMER_ID, mockConsumer));

        handler = new OneTimeClockBasedTimerHandler(mockClockBasedTimerFactory, actions::get);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeClockBasedTimerHandler(null, actions::get));
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeClockBasedTimerHandler(mockClockBasedTimerFactory, null));
    }

    @Test
    public void testWrite() {
        when(mockOneTimeClockBasedTimer.id()).thenReturn(ONE_TIME_CLOCK_TIMER_ID);
        when(mockOneTimeClockBasedTimer.firingTime()).thenReturn(FIRING_TIME);
        when(mockOneTimeClockBasedTimer.pausedTimestamp()).thenReturn(PAUSE_TIME);
        when(mockOneTimeClockBasedTimer.consumerId()).thenReturn(CONSUMER_ID);

        var result = handler.write(mockOneTimeClockBasedTimer);

        assertEquals(WRITTEN_VALUE, result);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var result = handler.read(WRITTEN_VALUE);

        assertSame(mockOneTimeClockBasedTimer, result);
        //noinspection unchecked
        verify(mockClockBasedTimerFactory)
                .make(ONE_TIME_CLOCK_TIMER_ID, FIRING_TIME, mockConsumer, PAUSE_TIME);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
