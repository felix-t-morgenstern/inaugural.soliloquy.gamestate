package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.RoundBasedTimerManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.gamestate.entities.gameevents.GameEventFiring;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.ArrayList;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomIntInRange;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoundBasedTimerManagerImplTests {
    @Mock private OneTimeRoundBasedTimer _mockOneTimeRoundBasedTimer;
    @Mock private RecurringRoundBasedTimer _mockRecurringRoundBasedTimer;
    @Mock private GameEventFiring _mockGameEventFiring;

    private RoundBasedTimerManager _roundBasedTimerManager;

    @BeforeEach
    void setUp() {
        _mockOneTimeRoundBasedTimer = mock(OneTimeRoundBasedTimer.class);
        _mockRecurringRoundBasedTimer = mock(RecurringRoundBasedTimer.class);
        _mockGameEventFiring = mock(GameEventFiring.class);

        _roundBasedTimerManager = new RoundBasedTimerManagerImpl(_mockGameEventFiring);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new RoundBasedTimerManagerImpl(null));
    }

    @Test
    void testRegisterOneTimeRoundBasedTimerAndOneTimeRoundBasedTimersRepresentation() {
        _roundBasedTimerManager.registerOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);

        assertEquals(new ArrayList<>() {{
                         add(_mockOneTimeRoundBasedTimer);
                     }},
                _roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation());
    }

    @Test
    void testRegisterOneTimeRoundBasedTimerTwiceAndOneTimeRoundBasedTimersRepresentation() {
        _roundBasedTimerManager.registerOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);
        _roundBasedTimerManager.registerOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);

        assertEquals(new ArrayList<>() {{
                         add(_mockOneTimeRoundBasedTimer);
                     }},
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

        assertEquals(new ArrayList<>() {{
                         add(_mockRecurringRoundBasedTimer);
                     }},
                _roundBasedTimerManager.recurringRoundBasedTimersRepresentation());
    }

    @Test
    void testRegisterRecurringRoundBasedTimerTwiceAndOneTimeRoundBasedTimersRepresentation() {
        _roundBasedTimerManager.registerRecurringRoundBasedTimer(_mockRecurringRoundBasedTimer);
        _roundBasedTimerManager.registerRecurringRoundBasedTimer(_mockRecurringRoundBasedTimer);

        assertEquals(new ArrayList<>() {{
                         add(_mockRecurringRoundBasedTimer);
                     }},
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
        int priority = randomInt();
        when(_mockOneTimeRoundBasedTimer.roundWhenGoesOff()).thenReturn(roundWhenGoesOff);
        when(_mockOneTimeRoundBasedTimer.priority()).thenReturn(priority);
        _roundBasedTimerManager.registerOneTimeRoundBasedTimer(_mockOneTimeRoundBasedTimer);

        _roundBasedTimerManager.fireTimersForRoundsElapsed(
                randomIntInRange(roundWhenGoesOff - randomIntInRange(2, 100), roundWhenGoesOff - 1),
                roundWhenGoesOff);

        verify(_mockGameEventFiring, times(0)).registerEvent(_mockOneTimeRoundBasedTimer, priority);

        _roundBasedTimerManager.fireTimersForRoundsElapsed(roundWhenGoesOff, roundWhenGoesOff + 1);

        verify(_mockGameEventFiring, times(1)).registerEvent(_mockOneTimeRoundBasedTimer, priority);
    }

    @Test
    void testFireTimersForRoundsElapsedFiresRecurringTimers() {
        int roundModulo = randomIntInRange(1, 100);
        int roundOffset = randomIntInRange(0, roundModulo - 1);
        int priority = randomInt();
        int numberOfTimesToFire = randomIntInRange(0, 10);
        int roundAfterWhichAllFiringsHaveOccurred =
                ((numberOfTimesToFire - 1) * roundModulo) + roundOffset;

        when(_mockRecurringRoundBasedTimer.roundModulo()).thenReturn(roundModulo);
        when(_mockRecurringRoundBasedTimer.roundOffset()).thenReturn(roundOffset);
        when(_mockRecurringRoundBasedTimer.priority()).thenReturn(priority);
        _roundBasedTimerManager.registerRecurringRoundBasedTimer(_mockRecurringRoundBasedTimer);

        _roundBasedTimerManager.fireTimersForRoundsElapsed(roundOffset - 1, roundOffset);

        verify(_mockGameEventFiring, times(0))
                .registerEvent(_mockRecurringRoundBasedTimer, priority);

        _roundBasedTimerManager.fireTimersForRoundsElapsed(roundOffset,
                roundAfterWhichAllFiringsHaveOccurred);

        verify(_mockGameEventFiring, times(numberOfTimesToFire - 1))
                .registerEvent(_mockRecurringRoundBasedTimer, priority);

        _roundBasedTimerManager.fireTimersForRoundsElapsed(roundAfterWhichAllFiringsHaveOccurred,
                roundAfterWhichAllFiringsHaveOccurred + 1);

        verify(_mockGameEventFiring, times(numberOfTimesToFire))
                .registerEvent(_mockRecurringRoundBasedTimer, priority);
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
