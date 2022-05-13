package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.RoundBasedTimerFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoundBasedTimerFactoryImplTests {
    private final String TIMER_ID = randomString();
    private final String ACTION_ID = randomString();
    private final int PRIORITY = randomInt();

    @SuppressWarnings({"rawtypes"})
    @Mock
    private Action _mockAction;
    @Mock
    private RoundBasedTimerManager _mockRoundBasedTimerManager;

    private RoundBasedTimerFactory _roundBasedTimerFactory;

    @BeforeEach
    void setUp() {
        _mockAction = mock(Action.class);
        when(_mockAction.id()).thenReturn(ACTION_ID);

        _mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        _roundBasedTimerFactory = new RoundBasedTimerFactoryImpl(_mockRoundBasedTimerManager);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new RoundBasedTimerFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RoundBasedTimerFactory.class.getCanonicalName(),
                _roundBasedTimerFactory.getInterfaceName());
    }

    @Test
    void testMakeOneTimeTimer() {
        int roundWhenGoesOff = randomInt();

        OneTimeRoundBasedTimer oneTimeRoundBasedTimer = _roundBasedTimerFactory.makeOneTimeTimer(
                TIMER_ID, _mockAction, roundWhenGoesOff, PRIORITY);

        assertEquals(TIMER_ID, oneTimeRoundBasedTimer.id());
        assertEquals(ACTION_ID, oneTimeRoundBasedTimer.actionId());
        assertEquals(roundWhenGoesOff, oneTimeRoundBasedTimer.roundWhenGoesOff());
        assertEquals(PRIORITY, oneTimeRoundBasedTimer.priority());
    }

    @Test
    void testMakeRecurringTimer() {
        int roundModulo = randomIntWithInclusiveFloor(1);
        int roundOffset = roundModulo - 1;

        RecurringRoundBasedTimer recurringRoundBasedTimer = _roundBasedTimerFactory
                .makeRecurringTimer(TIMER_ID, _mockAction, roundModulo, roundOffset, PRIORITY);

        assertEquals(TIMER_ID, recurringRoundBasedTimer.id());
        assertEquals(ACTION_ID, recurringRoundBasedTimer.actionId());
        assertEquals(roundModulo, recurringRoundBasedTimer.roundModulo());
        assertEquals(roundOffset, recurringRoundBasedTimer.roundOffset());
        assertEquals(PRIORITY, recurringRoundBasedTimer.priority());
    }
}
