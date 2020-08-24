package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.OneTimeTimerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import static org.junit.jupiter.api.Assertions.*;

class OneTimeTimerImplTests {
    private OneTimeTimer _oneTimeTimer;

    private final String TIMER_ID = "TimerId";
    private final Action<Void> ACTION = new FakeAction<>();
    private final FakeRoundManager ROUND_MANAGER = new FakeRoundManager();

    @BeforeEach
    void setUp() {
        _oneTimeTimer = new OneTimeTimerImpl(TIMER_ID, ACTION, 0L,
                ROUND_MANAGER.OneTimeTimers::add, ROUND_MANAGER.OneTimeTimers::remove);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(OneTimeTimer.class.getCanonicalName(), _oneTimeTimer.getInterfaceName());
    }

    @Test
    void testEquals() {
        OneTimeTimer oneTimeTimer = new OneTimeTimerImpl(TIMER_ID, ACTION, 0L,
                ROUND_MANAGER.OneTimeTimers::add, ROUND_MANAGER.OneTimeTimers::remove);
        assertEquals(_oneTimeTimer, oneTimeTimer);
    }

    @Test
    void testSetAndGetRoundWhenGoesOff() {
        long roundWhenGoesOff = 123L;
        _oneTimeTimer.setRoundWhenGoesOff(roundWhenGoesOff);

        assertEquals(roundWhenGoesOff, _oneTimeTimer.getRoundWhenGoesOff());
    }

    @Test
    void testAction() {
        assertSame(ACTION, _oneTimeTimer.action());
    }

    @Test
    void testFire() {
        _oneTimeTimer.fire();
        assertTrue(((FakeAction)ACTION)._actionRun);
    }

    @Test
    void testSetAndGetPriority() {
        _oneTimeTimer.setPriority(123);

        assertEquals(123, _oneTimeTimer.getPriority());
    }

    @Test
    void testId() {
        assertEquals(TIMER_ID, _oneTimeTimer.id());
    }

    @Test
    void testDelete() {
        assertTrue(ROUND_MANAGER.OneTimeTimers.contains(_oneTimeTimer));

        _oneTimeTimer.delete();

        assertFalse(ROUND_MANAGER.OneTimeTimers.contains(_oneTimeTimer));
    }

    @Test
    void testDeletedInvariant() {
        _oneTimeTimer.delete();

        assertThrows(EntityDeletedException.class, () -> _oneTimeTimer.id());
        assertThrows(EntityDeletedException.class, () -> _oneTimeTimer.fire());
        assertThrows(EntityDeletedException.class, () -> _oneTimeTimer.getPriority());
        assertThrows(EntityDeletedException.class, () -> _oneTimeTimer.setPriority(0));
        assertThrows(EntityDeletedException.class, () -> _oneTimeTimer.getRoundWhenGoesOff());
        assertThrows(EntityDeletedException.class, () -> _oneTimeTimer.setRoundWhenGoesOff(0));
    }
}
