package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentRecurringTurnBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeRecurringTurnBasedTimer;
import inaugural.soliloquy.gamestate.test.fakes.FakeRegistry;
import inaugural.soliloquy.gamestate.test.fakes.FakeTurnBasedTimerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;
import soliloquy.specs.gamestate.factories.TurnBasedTimerFactory;

import static org.junit.jupiter.api.Assertions.*;

class PersistentRecurringTurnBasedTimerTests {
    private final TurnBasedTimerFactory TURN_BASED_TIMER_FACTORY = new FakeTurnBasedTimerFactory();

    private final String RECURRING_TIMER_ID = "recurringTurnBasedTimerId";

    private final String ACTION_ID = "actionId";
    @SuppressWarnings("rawtypes")
    private final Action ACTION = new FakeAction(ACTION_ID);
    @SuppressWarnings("rawtypes")
    private final Registry<Action> ACTIONS = new FakeRegistry<>();

    private final int ROUND_MODULO = 456;
    private final int ROUND_OFFSET = 123;
    private final int PRIORITY = 789;

    private final String WRITTEN_VALUE = "{\"id\":\"recurringTurnBasedTimerId\",\"actionId\":\"actionId\",\"roundModulo\":456,\"roundOffset\":123,\"priority\":789}";

    private PersistentValueTypeHandler<RecurringTurnBasedTimer> _recurringTurnBasedTimerHandler;

    @BeforeEach
    void setUp() {
        ACTIONS.add(ACTION);

        _recurringTurnBasedTimerHandler = new PersistentRecurringTurnBasedTimerHandler(
                TURN_BASED_TIMER_FACTORY, ACTIONS::get);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentRecurringTurnBasedTimerHandler(null, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentRecurringTurnBasedTimerHandler(TURN_BASED_TIMER_FACTORY, null));
    }

    @Test
    void testArchetype() {
        assertNotNull(_recurringTurnBasedTimerHandler.getArchetype());
        assertEquals(RecurringTurnBasedTimer.class.getCanonicalName(),
                _recurringTurnBasedTimerHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        RecurringTurnBasedTimer.class.getCanonicalName() + ">",
                _recurringTurnBasedTimerHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        RecurringTurnBasedTimer recurringTimer = new FakeRecurringTurnBasedTimer(
                RECURRING_TIMER_ID, ACTION, ROUND_MODULO, ROUND_OFFSET, PRIORITY);

        String writtenValue = _recurringTurnBasedTimerHandler.write(recurringTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _recurringTurnBasedTimerHandler.write(null));
    }

    @Test
    void testRead() {
        RecurringTurnBasedTimer readResult = _recurringTurnBasedTimerHandler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(RECURRING_TIMER_ID, readResult.id());
        assertSame(ACTION, readResult.action());
        assertEquals(ROUND_MODULO, readResult.roundModulo());
        assertEquals(ROUND_OFFSET, readResult.roundOffset());
        assertEquals(PRIORITY, readResult.priority());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _recurringTurnBasedTimerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _recurringTurnBasedTimerHandler.read(""));
    }
}
