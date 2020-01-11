package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.RecurringTimerImpl;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.RoundManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.RecurringTimer;

import static org.junit.jupiter.api.Assertions.*;

class RecurringTimerImplTests {
    private RecurringTimer _recurringTimer;

    private final String TIMER_ID = "TimerId";
    private final Action<Void> TIMER_ACTION = new ActionStub<>();
    private final RoundManagerStub ROUND_MANAGER = new RoundManagerStub();

    @BeforeEach
    void setUp() {
        _recurringTimer = new RecurringTimerImpl(TIMER_ID, TIMER_ACTION, 0, 0,
                ROUND_MANAGER.RECURRING_TIMERS::add, ROUND_MANAGER.RECURRING_TIMERS::remove);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RecurringTimer.class.getCanonicalName(), _recurringTimer.getInterfaceName());
    }

    @Test
    void testEquals() {
        RecurringTimer recurringTimer =
                new RecurringTimerImpl(TIMER_ID, TIMER_ACTION, 0, 0,
                        ROUND_MANAGER.RECURRING_TIMERS::add,
                        ROUND_MANAGER.RECURRING_TIMERS::remove);
        assertEquals(_recurringTimer, recurringTimer);
    }

    @Test
    void testSetAndGetRoundModulo() {
        _recurringTimer.setRoundModulo(123);

        assertEquals(123, _recurringTimer.getRoundModulo());
    }

    @Test
    void testSetAndGetRoundOffset() {
        _recurringTimer.setRoundOffset(456);

        assertEquals(456, _recurringTimer.getRoundOffset());
    }

    @Test
    void testActionTypeId() {
        assertEquals(TIMER_ACTION.id(), _recurringTimer.actionTypeId());
    }

    @Test
    void testFire() {
        _recurringTimer.fire();
        assertTrue(((ActionStub)TIMER_ACTION)._actionRun);
    }

    @Test
    void testSetAndGetPriority() {
        _recurringTimer.setPriority(123);

        assertEquals(123, _recurringTimer.getPriority());
    }

    @Test
    void testId() {
        assertEquals(TIMER_ID, _recurringTimer.id());
    }

    @Test
    void testDelete() {
        assertTrue(ROUND_MANAGER.RECURRING_TIMERS.contains(_recurringTimer));

        _recurringTimer.delete();

        assertFalse(ROUND_MANAGER.RECURRING_TIMERS.contains(_recurringTimer));
    }
}
