package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RecurringRoundBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringRoundBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundBasedTimerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class RecurringRoundBasedTimerHandlerTests {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY =
            new FakeRoundBasedTimerFactory();

    private final String RECURRING_TIMER_ID = "recurringRoundBasedTimerId";

    private final String CONSUMER_ID = "consumerId";

    private final int ROUND_MODULO = 456;
    private final int ROUND_OFFSET = 123;
    private final int PRIORITY = 789;

    @SuppressWarnings("rawtypes")
    private Map<String, Consumer> actions;

    @SuppressWarnings("rawtypes")
    @Mock private Consumer mockConsumer;

    private final String WRITTEN_VALUE =
            "{\"id\":\"recurringRoundBasedTimerId\",\"consumerId\":\"consumerId\"," +
                    "\"roundModulo\":456,\"roundOffset\":123,\"priority\":789}";

    private TypeHandler<RecurringRoundBasedTimer> handler;

    @BeforeEach
    public void setUp() {
        lenient().when(mockConsumer.id()).thenReturn(CONSUMER_ID);

        actions = mapOf(pairOf(CONSUMER_ID, mockConsumer));

        handler = new RecurringRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, actions::get);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringRoundBasedTimerHandler(null, actions::get));
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, null));
    }

    @Test
    public void testWrite() {
        RecurringRoundBasedTimer recurringTimer = new FakeRecurringRoundBasedTimer(
                RECURRING_TIMER_ID, mockConsumer, ROUND_MODULO, ROUND_OFFSET, PRIORITY);

        String writtenValue = handler.write(recurringTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.write(null));
    }

    @Test
    public void testRead() {
        RecurringRoundBasedTimer readResult = handler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(RECURRING_TIMER_ID, readResult.id());
        assertSame(CONSUMER_ID, readResult.consumerId());
        assertEquals(ROUND_MODULO, readResult.roundModulo());
        assertEquals(ROUND_OFFSET, readResult.roundOffset());
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
