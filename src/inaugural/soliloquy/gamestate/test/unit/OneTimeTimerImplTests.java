package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.OneTimeTimerImpl;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.RoundManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OneTimeTimerImplTests {
    private OneTimeTimer _oneTimeTimer;

    private final String TIMER_ID = "TimerId";
    private final Action<Void> ACTION = new ActionStub<>();
    private final RoundManagerStub ROUND_MANAGER = new RoundManagerStub();

    @BeforeEach
    void setUp() {
        _oneTimeTimer = new OneTimeTimerImpl(TIMER_ID, ACTION, 0L,
                ROUND_MANAGER.ONE_TIME_TIMERS::add, ROUND_MANAGER.ONE_TIME_TIMERS::removeItem);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(OneTimeTimer.class.getCanonicalName(), _oneTimeTimer.getInterfaceName());
    }

    @Test
    void testEquals() {
        OneTimeTimer oneTimeTimer = new OneTimeTimerImpl(TIMER_ID, ACTION, 0L,
                ROUND_MANAGER.ONE_TIME_TIMERS::add, ROUND_MANAGER.ONE_TIME_TIMERS::removeItem);
        assertEquals(_oneTimeTimer, oneTimeTimer);
    }

    @Test
    void testSetAndGetRoundWhenGoesOff() {
        long roundWhenGoesOff = 123L;
        _oneTimeTimer.setRoundWhenGoesOff(roundWhenGoesOff);

        assertEquals(roundWhenGoesOff, _oneTimeTimer.getRoundWhenGoesOff());
    }

    @Test
    void testTimerTypeId() {
        assertEquals(ACTION, _oneTimeTimer.action());
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
}