package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.OneTimeRoundBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeRoundBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundBasedTimerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class OneTimeRoundBasedTimerHandlerTests {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY =
            new FakeRoundBasedTimerFactory();

    private final String ONE_TIME_TIMER_ID = "oneTimeRoundBasedTimerId";

    private final String CONSUMER_ID = "consumerId";

    @SuppressWarnings("rawtypes")
    @Mock private Map<String, Consumer> consumers;

    private final int ROUND_WHEN_GOES_OFF = 123123123;
    private final int PRIORITY = 456;

    @SuppressWarnings("rawtypes")
    @Mock private Consumer mockConsumer;

    private final String WRITTEN_VALUE =
            "{\"id\":\"oneTimeRoundBasedTimerId\",\"consumerId\":\"consumerId\"," +
                    "\"round\":123123123,\"priority\":456}";

    private TypeHandler<OneTimeRoundBasedTimer> handler;

    @BeforeEach
    public void setUp() {
        lenient().when(mockConsumer.id()).thenReturn(CONSUMER_ID);

        consumers = mapOf(pairOf(CONSUMER_ID, mockConsumer));

        handler = new OneTimeRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, consumers::get);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeRoundBasedTimerHandler(null, consumers::get));
        assertThrows(IllegalArgumentException.class,
                () -> new OneTimeRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, null));
    }

    @Test
    public void testWrite() {
        OneTimeRoundBasedTimer oneTimeRoundBasedTimer = new FakeOneTimeRoundBasedTimer(
                ONE_TIME_TIMER_ID, mockConsumer, ROUND_WHEN_GOES_OFF, PRIORITY);

        String writtenValue = handler.write(oneTimeRoundBasedTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.write(null));
    }

    @Test
    public void testRead() {
        OneTimeRoundBasedTimer readResult = handler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(ONE_TIME_TIMER_ID, readResult.id());
        assertSame(CONSUMER_ID, readResult.consumerId());
        assertEquals(ROUND_WHEN_GOES_OFF, readResult.roundWhenGoesOff());
        assertEquals(PRIORITY, readResult.priority());
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                handler.read(""));
    }
}
