package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeTurnBasedTimerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;

import static org.junit.jupiter.api.Assertions.*;

class OneTimeTurnBasedTimerImplTests {
    private OneTimeTurnBasedTimer _oneTimeTurnBasedTimer;

    private final String TIMER_ID = "TimerId";
    private final Action<Void> ACTION = new FakeAction<>();
    private final long ROUND_WHEN_GOES_OFF = 456L;
    private final int PRIORITY = 123;
    private final FakeRoundManager ROUND_MANAGER = new FakeRoundManager();

    @BeforeEach
    void setUp() {
        _oneTimeTurnBasedTimer = new OneTimeTurnBasedTimerImpl(TIMER_ID, ACTION,
                ROUND_WHEN_GOES_OFF, PRIORITY, ROUND_MANAGER.OneTimeTurnBasedTimers::add,
                ROUND_MANAGER.OneTimeTurnBasedTimers::remove);
    }

    // TODO: testConstructorWithInvalidParams

    @Test
    void testGetInterfaceName() {
        assertEquals(OneTimeTurnBasedTimer.class.getCanonicalName(),
                _oneTimeTurnBasedTimer.getInterfaceName());
    }

    // TODO: Test more rigorously the conditions for inequality
    @Test
    void testEquals() {
        OneTimeTurnBasedTimer oneTimeTimer = new OneTimeTurnBasedTimerImpl(TIMER_ID, ACTION, 0L,
                PRIORITY, ROUND_MANAGER.OneTimeTurnBasedTimers::add,
                ROUND_MANAGER.OneTimeTurnBasedTimers::remove);
        assertEquals(_oneTimeTurnBasedTimer, oneTimeTimer);
    }

    @Test
    void testRoundWhenGoesOff() {
        assertEquals(ROUND_WHEN_GOES_OFF, _oneTimeTurnBasedTimer.roundWhenGoesOff());
    }

    @Test
    void testAction() {
        assertSame(ACTION, _oneTimeTurnBasedTimer.action());
    }

    @Test
    void testFire() {
        _oneTimeTurnBasedTimer.fire();
        //noinspection rawtypes
        assertTrue(((FakeAction)ACTION)._actionRun);
    }

    @Test
    void testPriority() {
        assertEquals(PRIORITY, _oneTimeTurnBasedTimer.priority());
    }

    @Test
    void testId() {
        assertEquals(TIMER_ID, _oneTimeTurnBasedTimer.id());
    }

    @Test
    void testDelete() {
        assertTrue(ROUND_MANAGER.OneTimeTurnBasedTimers.contains(_oneTimeTurnBasedTimer));

        _oneTimeTurnBasedTimer.delete();

        assertFalse(ROUND_MANAGER.OneTimeTurnBasedTimers.contains(_oneTimeTurnBasedTimer));
    }

    @Test
    void testDeletedInvariant() {
        _oneTimeTurnBasedTimer.delete();

        assertThrows(EntityDeletedException.class, () -> _oneTimeTurnBasedTimer.id());
        assertThrows(EntityDeletedException.class, () -> _oneTimeTurnBasedTimer.fire());
        assertThrows(EntityDeletedException.class, () -> _oneTimeTurnBasedTimer.priority());
        assertThrows(EntityDeletedException.class, () -> _oneTimeTurnBasedTimer.roundWhenGoesOff());
    }
}
