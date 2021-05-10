package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.timers.RecurringTurnBasedTimerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;

import static org.junit.jupiter.api.Assertions.*;

class RecurringTurnBasedTimerImplTests {
    private RecurringTurnBasedTimer _recurringTurnBasedTimer;

    private final String TIMER_ID = "TimerId";
    private final Action<Void> TIMER_ACTION = new FakeAction<>();
    private final int ROUND_MODULO = 789;
    private final int ROUND_OFFSET = 456;
    private final int PRIORITY = 123;
    private final FakeRoundManager ROUND_MANAGER = new FakeRoundManager();

    @BeforeEach
    void setUp() {
        _recurringTurnBasedTimer = new RecurringTurnBasedTimerImpl(TIMER_ID, TIMER_ACTION,
                ROUND_MODULO, ROUND_OFFSET, PRIORITY, ROUND_MANAGER.RecurringTurnBasedTimers::add,
                ROUND_MANAGER.RecurringTurnBasedTimers::remove);
    }

    // TODO: testConstructorWithInvalidParams; include test of modulo and offset validity

    @Test
    void testGetInterfaceName() {
        assertEquals(RecurringTurnBasedTimer.class.getCanonicalName(),
                _recurringTurnBasedTimer.getInterfaceName());
    }

    @Test
    void testEquals() {
        RecurringTurnBasedTimer recurringTimer =
                new RecurringTurnBasedTimerImpl(TIMER_ID, TIMER_ACTION, 0, 0,
                        PRIORITY, ROUND_MANAGER.RecurringTurnBasedTimers::add,
                        ROUND_MANAGER.RecurringTurnBasedTimers::remove);
        assertEquals(_recurringTurnBasedTimer, recurringTimer);
    }

    @Test
    void testRoundModulo() {
        assertEquals(ROUND_MODULO, _recurringTurnBasedTimer.roundModulo());
    }

    @Test
    void testRoundOffset() {
        assertEquals(ROUND_OFFSET, _recurringTurnBasedTimer.roundOffset());
    }

    @Test
    void testAction() {
        assertSame(TIMER_ACTION, _recurringTurnBasedTimer.action());
    }

    @Test
    void testFire() {
        _recurringTurnBasedTimer.fire();
        //noinspection rawtypes
        assertTrue(((FakeAction)TIMER_ACTION)._actionRun);
    }

    @Test
    void testPriority() {
        assertEquals(PRIORITY, _recurringTurnBasedTimer.priority());
    }

    @Test
    void testId() {
        assertEquals(TIMER_ID, _recurringTurnBasedTimer.id());
    }

    @Test
    void testDelete() {
        assertTrue(ROUND_MANAGER.RecurringTurnBasedTimers.contains(_recurringTurnBasedTimer));

        _recurringTurnBasedTimer.delete();

        assertFalse(ROUND_MANAGER.RecurringTurnBasedTimers.contains(_recurringTurnBasedTimer));
    }

    @Test
    void testDeletedInvariant() {
        _recurringTurnBasedTimer.delete();

        assertThrows(EntityDeletedException.class, () -> _recurringTurnBasedTimer.id());
        assertThrows(EntityDeletedException.class, () -> _recurringTurnBasedTimer.fire());
        assertThrows(EntityDeletedException.class, () -> _recurringTurnBasedTimer.priority());
        assertThrows(EntityDeletedException.class, () -> _recurringTurnBasedTimer.roundModulo());
        assertThrows(EntityDeletedException.class, () -> _recurringTurnBasedTimer.roundOffset());
    }
}
