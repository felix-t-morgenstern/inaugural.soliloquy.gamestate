package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.RoundBasedTimerManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.ArrayList;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoundBasedTimerManagerImplTests {
    @Mock private OneTimeRoundBasedTimer _mockOneTimeRoundBasedTimer;
    @Mock private RecurringRoundBasedTimer _mockRecurringRoundBasedTimer;

    private RoundBasedTimerManager _roundBasedTimerManager;

    @BeforeEach
    void setUp() {
        _mockOneTimeRoundBasedTimer = mock(OneTimeRoundBasedTimer.class);
        _mockRecurringRoundBasedTimer = mock(RecurringRoundBasedTimer.class);

        _roundBasedTimerManager = new RoundBasedTimerManagerImpl();
    }

    @Test
    void testRegisterOneTimeRoundBasedTimerAndOneTimeRoundBasedTimersRepresentation() {
        _roundBasedTimerManager.registerOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);

        assertEquals(new ArrayList<>(){{ add(_mockOneTimeRoundBasedTimer); }},
                _roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation());
    }

    @Test
    void testRegisterOneTimeRoundBasedTimerTwiceAndOneTimeRoundBasedTimersRepresentation() {
        _roundBasedTimerManager.registerOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);
        _roundBasedTimerManager.registerOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);

        assertEquals(new ArrayList<>(){{ add(_mockOneTimeRoundBasedTimer); }},
                _roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation());
    }

    @Test
    void testDeregisterOneTimeRoundBasedTimer() {
        _roundBasedTimerManager.registerOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);

        _roundBasedTimerManager.deregisterOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);

        assertTrue(_roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation().isEmpty());
    }

    @Test
    void testRegisterRecurringRoundBasedTimerAndOneTimeRoundBasedTimersRepresentation() {
        _roundBasedTimerManager.registerRecurringRoundBasedTimer(_mockRecurringRoundBasedTimer);

        assertEquals(new ArrayList<>(){{ add(_mockRecurringRoundBasedTimer); }},
                _roundBasedTimerManager.recurringRoundBasedTimersRepresentation());
    }

    @Test
    void testRegisterRecurringRoundBasedTimerTwiceAndOneTimeRoundBasedTimersRepresentation() {
        _roundBasedTimerManager.registerRecurringRoundBasedTimer(_mockRecurringRoundBasedTimer);
        _roundBasedTimerManager.registerRecurringRoundBasedTimer(_mockRecurringRoundBasedTimer);

        assertEquals(new ArrayList<>(){{ add(_mockRecurringRoundBasedTimer); }},
                _roundBasedTimerManager.recurringRoundBasedTimersRepresentation());
    }

    @Test
    void testDeregisterRecurringRoundBasedTimer() {
        _roundBasedTimerManager.registerRecurringRoundBasedTimer(_mockRecurringRoundBasedTimer);

        _roundBasedTimerManager.deregisterRecurringRoundBasedTimer(_mockRecurringRoundBasedTimer);

        assertTrue(_roundBasedTimerManager.recurringRoundBasedTimersRepresentation().isEmpty());
    }

    @Test
    void testRegisterAndDeregisterTimersWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _roundBasedTimerManager.registerOneTimeRoundBasedTimer(null));
        assertThrows(IllegalArgumentException.class, () ->
                _roundBasedTimerManager.deregisterOneTimeRoundBasedTimer(null));
        assertThrows(IllegalArgumentException.class, () ->
                _roundBasedTimerManager.registerRecurringRoundBasedTimer(null));
        assertThrows(IllegalArgumentException.class, () ->
                _roundBasedTimerManager.deregisterRecurringRoundBasedTimer(null));
    }

    @Test
    void testFireTimersForRoundsElapsedFiresOneTimeTimers() {
        int roundWhenGoesOff = randomInt();
        when(_mockOneTimeRoundBasedTimer.roundWhenGoesOff()).thenReturn(roundWhenGoesOff);

        _roundBasedTimerManager.fireTimersForRoundsElapsed(Integer.MIN_VALUE, roundWhenGoesOff);
    }

    @Test
    void testFireTimersForRoundsElapsedWithInvalidParams() {
        int previousRound = randomInt();

        _roundBasedTimerManager.fireTimersForRoundsElapsed(previousRound, previousRound + 1);
        assertThrows(IllegalArgumentException.class, () ->
                _roundBasedTimerManager.fireTimersForRoundsElapsed(previousRound, previousRound));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RoundBasedTimerManager.class.getCanonicalName(),
                _roundBasedTimerManager.getInterfaceName());
    }
}
