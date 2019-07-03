package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TimerFactory;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.RoundManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.gamestate.factories.ITimerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class TimerFactoryTests {
    private final RoundManagerStub ROUND_MANAGER = new RoundManagerStub();

    private ITimerFactory _timerFactory;

    @BeforeEach
    void setUp() {
        _timerFactory = new TimerFactory(ROUND_MANAGER.ONE_TIME_TIMERS::add,
                ROUND_MANAGER.ONE_TIME_TIMERS::removeItem,
                ROUND_MANAGER.RECURRING_TIMERS::add,
                ROUND_MANAGER.RECURRING_TIMERS::removeItem);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ITimerFactory.class.getCanonicalName(), _timerFactory.getInterfaceName());
    }

    @Test
    void testMakeOneTimeTimer() {
        String timerId = "TimerId";
        IAction<Void> action = new ActionStub<>();
        long roundWhenGoesOff = 123123L;

        IOneTimeTimer oneTimeTimer =
                _timerFactory.makeOneTimeTimer(timerId, action, roundWhenGoesOff);

        assertEquals(timerId, oneTimeTimer.id());
        assertSame(action, oneTimeTimer.action());
        assertEquals(roundWhenGoesOff, oneTimeTimer.getRoundWhenGoesOff());
    }

    @Test
    void testMakeRecurringTimer() {
        String timerId = "TimerId";
        IAction<Void> action = new ActionStub<>();
        int roundModulo = 123;
        int roundOffset = 12;

        IRecurringTimer recurringTimer =
                _timerFactory.makeRecurringTimer(timerId, action, roundModulo, roundOffset);

        assertEquals(timerId, recurringTimer.id());
        assertSame(action, recurringTimer.action());
        assertEquals(roundModulo, recurringTimer.getRoundModulo());
        assertEquals(roundOffset, recurringTimer.getRoundOffset());
    }
}
