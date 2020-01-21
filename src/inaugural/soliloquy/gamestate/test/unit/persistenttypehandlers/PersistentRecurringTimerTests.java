package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentRecurringTimerHandler;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.RecurringTimerStub;
import inaugural.soliloquy.gamestate.test.stubs.RegistryStub;
import inaugural.soliloquy.gamestate.test.stubs.TimerFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.factories.TimerFactory;

import static org.junit.jupiter.api.Assertions.*;

class PersistentRecurringTimerTests {
    private final TimerFactory TIMER_FACTORY = new TimerFactoryStub();

    private final String RECURRING_TIMER_ID = "recurringTimerId";

    private final String ACTION_ID = "actionId";
    private final Action ACTION = new ActionStub(ACTION_ID);
    private final Registry<Action> ACTIONS = new RegistryStub<>();

    private final int ROUND_MODULO = 456;
    private final int ROUND_OFFSET = 123;

    private final String WRITTEN_VALUE = "{\"id\":\"recurringTimerId\",\"actionId\":\"actionId\",\"roundModulo\":456,\"roundOffset\":123}";

    private PersistentValueTypeHandler<RecurringTimer> _recurringTimerHandler;

    @BeforeEach
    void setUp() {
        ACTIONS.add(ACTION);

        _recurringTimerHandler = new PersistentRecurringTimerHandler(TIMER_FACTORY, ACTIONS::get);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentRecurringTimerHandler(null, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentRecurringTimerHandler(TIMER_FACTORY, null));
    }

    @Test
    void testArchetype() {
        assertNotNull(_recurringTimerHandler.getArchetype());
        assertEquals(RecurringTimer.class.getCanonicalName(),
                _recurringTimerHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        RecurringTimer.class.getCanonicalName() + ">",
                _recurringTimerHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        RecurringTimer recurringTimer = new RecurringTimerStub(RECURRING_TIMER_ID, ACTION);
        recurringTimer.setRoundModulo(ROUND_MODULO);
        recurringTimer.setRoundOffset(ROUND_OFFSET);

        String writtenValue = _recurringTimerHandler.write(recurringTimer);

        assertEquals(WRITTEN_VALUE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _recurringTimerHandler.write(null));
    }

    @Test
    void testRead() {
        RecurringTimer readResult = _recurringTimerHandler.read(WRITTEN_VALUE);

        assertNotNull(readResult);
        assertEquals(RECURRING_TIMER_ID, readResult.id());
        assertSame(ACTION, readResult.action());
        assertEquals(ROUND_MODULO, readResult.getRoundModulo());
        assertEquals(ROUND_OFFSET, readResult.getRoundOffset());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _recurringTimerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _recurringTimerHandler.read(""));
    }
}
