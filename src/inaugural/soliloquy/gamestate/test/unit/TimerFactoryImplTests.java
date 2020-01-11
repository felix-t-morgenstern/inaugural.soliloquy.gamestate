package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TimerFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.ActionStub;
import inaugural.soliloquy.gamestate.test.stubs.RoundManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.factories.TimerFactory;

import static org.junit.jupiter.api.Assertions.*;

class TimerFactoryImplTests {
    private final RoundManagerStub ROUND_MANAGER = new RoundManagerStub();

    private TimerFactory _timerFactory;

    @BeforeEach
    void setUp() {
        _timerFactory = new TimerFactoryImpl(ROUND_MANAGER.ONE_TIME_TIMERS::add,
                ROUND_MANAGER.ONE_TIME_TIMERS::remove,
                ROUND_MANAGER.RECURRING_TIMERS::add,
                ROUND_MANAGER.RECURRING_TIMERS::remove);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TimerFactory.class.getCanonicalName(), _timerFactory.getInterfaceName());
    }

    @Test
    void testMakeOneTimeTimer() {
        String timerId = "TimerId";
        Action<Void> action = new ActionStub<>();
        long roundWhenGoesOff = 123123L;

        OneTimeTimer oneTimeTimer =
                _timerFactory.makeOneTimeTimer(timerId, action, roundWhenGoesOff);

        assertEquals(timerId, oneTimeTimer.id());
        assertEquals(roundWhenGoesOff, oneTimeTimer.getRoundWhenGoesOff());
    }

    @Test
    void testMakeRecurringTimer() {
        String timerId = "TimerId";
        Action<Void> action = new ActionStub<>();
        int roundModulo = 123;
        int roundOffset = 12;

        RecurringTimer recurringTimer =
                _timerFactory.makeRecurringTimer(timerId, action, roundModulo, roundOffset);

        assertEquals(timerId, recurringTimer.id());
        assertEquals(roundModulo, recurringTimer.getRoundModulo());
        assertEquals(roundOffset, recurringTimer.getRoundOffset());
    }
}
