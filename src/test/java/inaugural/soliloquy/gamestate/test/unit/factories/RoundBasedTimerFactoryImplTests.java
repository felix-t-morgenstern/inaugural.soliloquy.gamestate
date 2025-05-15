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

public class RoundBasedTimerFactoryImplTests {
    private final String TIMER_ID = randomString();
    private final String ACTION_ID = randomString();
    private final int PRIORITY = randomInt();

    @SuppressWarnings({"rawtypes"})
    @Mock
    private Action mockAction;
    @Mock
    private RoundBasedTimerManager mockRoundBasedTimerManager;

    private RoundBasedTimerFactory roundbasedtimerfactory;

    @BeforeEach
    public void setUp() {
        mockAction = mock(Action.class);
        when(mockAction.id()).thenReturn(ACTION_ID);

        mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        roundbasedtimerfactory = new RoundBasedTimerFactoryImpl(mockRoundBasedTimerManager);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new RoundBasedTimerFactoryImpl(null));
    }

    @Test
    public void testMakeOneTimeTimer() {
        int roundWhenGoesOff = randomInt();

        OneTimeRoundBasedTimer oneTimeRoundBasedTimer = roundbasedtimerfactory.makeOneTimeTimer(
                TIMER_ID, mockAction, roundWhenGoesOff, PRIORITY);

        assertEquals(TIMER_ID, oneTimeRoundBasedTimer.id());
        assertEquals(ACTION_ID, oneTimeRoundBasedTimer.actionId());
        assertEquals(roundWhenGoesOff, oneTimeRoundBasedTimer.roundWhenGoesOff());
        assertEquals(PRIORITY, oneTimeRoundBasedTimer.priority());
    }

    @Test
    public void testMakeRecurringTimer() {
        int roundModulo = randomIntWithInclusiveFloor(1);
        int roundOffset = roundModulo - 1;

        RecurringRoundBasedTimer recurringRoundBasedTimer = roundbasedtimerfactory
                .makeRecurringTimer(TIMER_ID, mockAction, roundModulo, roundOffset, PRIORITY);

        assertEquals(TIMER_ID, recurringRoundBasedTimer.id());
        assertEquals(ACTION_ID, recurringRoundBasedTimer.actionId());
        assertEquals(roundModulo, recurringRoundBasedTimer.roundModulo());
        assertEquals(roundOffset, recurringRoundBasedTimer.roundOffset());
        assertEquals(PRIORITY, recurringRoundBasedTimer.priority());
    }
}
