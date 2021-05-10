package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TurnBasedTimerFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import inaugural.soliloquy.gamestate.test.fakes.FakeRoundManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;
import soliloquy.specs.gamestate.factories.TurnBasedTimerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnBasedTimerFactoryImplTests {
    private final FakeRoundManager ROUND_MANAGER = new FakeRoundManager();

    private TurnBasedTimerFactory _turnBasedTimerFactory;

    @BeforeEach
    void setUp() {
        _turnBasedTimerFactory = new TurnBasedTimerFactoryImpl(
                ROUND_MANAGER.OneTimeTurnBasedTimers::add,
                ROUND_MANAGER.OneTimeTurnBasedTimers::remove,
                ROUND_MANAGER.RecurringTurnBasedTimers::add,
                ROUND_MANAGER.RecurringTurnBasedTimers::remove);
    }

    // TODO: testConstructorWithInvalidParams

    @Test
    void testGetInterfaceName() {
        assertEquals(TurnBasedTimerFactory.class.getCanonicalName(),
                _turnBasedTimerFactory.getInterfaceName());
    }

    @Test
    void testMakeOneTimeTimer() {
        String timerId = "TimerId";
        Action<Void> action = new FakeAction<>();
        long roundWhenGoesOff = 123123L;
        int priority = 456;

        OneTimeTurnBasedTimer oneTimeTurnBasedTimer = _turnBasedTimerFactory.makeOneTimeTimer(
                timerId, action, roundWhenGoesOff, priority);

        assertEquals(timerId, oneTimeTurnBasedTimer.id());
        assertEquals(roundWhenGoesOff, oneTimeTurnBasedTimer.roundWhenGoesOff());
        assertEquals(priority, oneTimeTurnBasedTimer.priority());
    }

    @Test
    void testMakeRecurringTimer() {
        String timerId = "TimerId";
        Action<Void> action = new FakeAction<>();
        int roundModulo = 123;
        int roundOffset = 12;
        int priority = 456;

        RecurringTurnBasedTimer recurringTurnBasedTimer = _turnBasedTimerFactory
                .makeRecurringTimer(timerId, action, roundModulo, roundOffset, priority);

        assertEquals(timerId, recurringTurnBasedTimer.id());
        assertEquals(roundModulo, recurringTurnBasedTimer.roundModulo());
        assertEquals(roundOffset, recurringTurnBasedTimer.roundOffset());
        assertEquals(priority, recurringTurnBasedTimer.priority());
    }
}
