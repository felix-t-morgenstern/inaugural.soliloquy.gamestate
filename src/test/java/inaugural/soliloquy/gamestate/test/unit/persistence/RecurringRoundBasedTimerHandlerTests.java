package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.entities.timers.RecurringRoundBasedTimerImpl;
import inaugural.soliloquy.gamestate.persistence.RecurringRoundBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringRoundBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundBasedTimerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;


public class RecurringRoundBasedTimerHandlerTests {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY =
            new FakeRoundBasedTimerFactory();

    private final String RECURRING_TIMER_ID = "recurringRoundBasedTimerId";

    private final String ACTION_ID = "actionId";
    @SuppressWarnings("rawtypes")
    private final Action ACTION = new FakeAction(ACTION_ID);

    @SuppressWarnings("rawtypes")
    private Map<String, Action> actions;

    private final int ROUND_MODULO = 456;
    private final int ROUND_OFFSET = 123;
    private final int PRIORITY = 789;

    private final String WRITTEN_VALUE =
            "{\"id\":\"recurringRoundBasedTimerId\",\"actionId\":\"actionId\"," +
                    "\"roundModulo\":456,\"roundOffset\":123,\"priority\":789}";

    private TypeHandler<RecurringRoundBasedTimer> handler;

    @BeforeEach
    public void setUp() {
        actions = mapOf(pairOf(ACTION_ID, ACTION));

        handler = new RecurringRoundBasedTimerHandler(
                TURN_BASED_TIMER_FACTORY, actions::get);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringRoundBasedTimerHandler(null, actions::get));
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, null));
    }

    @Test
    public void testTypeHandled() {
        assertEquals(RecurringRoundBasedTimerImpl.class.getCanonicalName(), handler.typeHandled());
    }

    @Test
    public void testWrite() {
        RecurringRoundBasedTimer recurringTimer = new FakeRecurringRoundBasedTimer(
                RECURRING_TIMER_ID, ACTION, ROUND_MODULO, ROUND_OFFSET, PRIORITY);

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
        assertSame(ACTION_ID, readResult.actionId());
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
