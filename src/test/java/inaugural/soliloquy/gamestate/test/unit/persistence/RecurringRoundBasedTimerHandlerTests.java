package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RecurringRoundBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringRoundBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRegistry;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundBasedTimerFactory;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import static org.junit.Assert.*;

public class RecurringRoundBasedTimerHandlerTests {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY =
            new FakeRoundBasedTimerFactory();

    private final String RECURRING_TIMER_ID = "recurringRoundBasedTimerId";

    private final String ACTION_ID = "actionId";
    @SuppressWarnings("rawtypes")
    private final Action ACTION = new FakeAction(ACTION_ID);
    @SuppressWarnings("rawtypes")
    private final Registry<Action> ACTIONS = new FakeRegistry<>();

    private final int ROUND_MODULO = 456;
    private final int ROUND_OFFSET = 123;
    private final int PRIORITY = 789;

    private final String WRITTEN_VALUE =
            "{\"id\":\"recurringRoundBasedTimerId\",\"actionId\":\"actionId\"," +
                    "\"roundModulo\":456,\"roundOffset\":123,\"priority\":789}";

    private TypeHandler<RecurringRoundBasedTimer> _recurringRoundBasedTimerHandler;

    @Before
    public void setUp() {
        ACTIONS.add(ACTION);

        _recurringRoundBasedTimerHandler = new RecurringRoundBasedTimerHandler(
                TURN_BASED_TIMER_FACTORY, ACTIONS::get);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringRoundBasedTimerHandler(null, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new RecurringRoundBasedTimerHandler(TURN_BASED_TIMER_FACTORY, null));
    }

    @Test
    public void testArchetype() {
        assertNotNull(_recurringRoundBasedTimerHandler.archetype());
        assertEquals(RecurringRoundBasedTimer.class.getCanonicalName(),
                _recurringRoundBasedTimerHandler.archetype().getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        RecurringRoundBasedTimer.class.getCanonicalName() + ">",
                _recurringRoundBasedTimerHandler.getInterfaceName());
    }

    @Test
    public void testWrite() {
        RecurringRoundBasedTimer recurringTimer = new FakeRecurringRoundBasedTimer(
                RECURRING_TIMER_ID, ACTION, ROUND_MODULO, ROUND_OFFSET, PRIORITY);

        String writtenValue = _recurringRoundBasedTimerHandler.write(recurringTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _recurringRoundBasedTimerHandler.write(null));
    }

    @Test
    public void testRead() {
        RecurringRoundBasedTimer readResult = _recurringRoundBasedTimerHandler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(RECURRING_TIMER_ID, readResult.id());
        assertSame(ACTION_ID, readResult.actionId());
        assertEquals(ROUND_MODULO, readResult.roundModulo());
        assertEquals(ROUND_OFFSET, readResult.roundOffset());
        assertEquals(PRIORITY, readResult.priority());
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _recurringRoundBasedTimerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                _recurringRoundBasedTimerHandler.read(""));
    }
}
