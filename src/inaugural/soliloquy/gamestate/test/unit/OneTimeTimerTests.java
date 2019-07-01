package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.OneTimeTimer;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.RoundManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OneTimeTimerTests {
    private IOneTimeTimer _oneTimeTimer;

    private final String TIMER_ID = "TimerId";
    private final IAction<Void> ACTION = new ActionStub<>();
    private final RoundManagerStub ROUND_MANAGER = new RoundManagerStub();

    @BeforeEach
    void setUp() {
        _oneTimeTimer = new OneTimeTimer(TIMER_ID, ACTION, 0L,
                ROUND_MANAGER.ONE_TIME_TIMERS::add, ROUND_MANAGER.ONE_TIME_TIMERS::removeItem);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IOneTimeTimer.class.getCanonicalName(), _oneTimeTimer.getInterfaceName());
    }

    @Test
    void testEquals() {
        IOneTimeTimer oneTimeTimer = new OneTimeTimer(TIMER_ID, ACTION, 0L,
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
