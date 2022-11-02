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
    @Mock private OneTimeRoundBasedTimer mockOneTimeRoundBasedTimer;
    @Mock private RecurringRoundBasedTimer mockRecurringRoundBasedTimer;
    @Mock private GameEventFiring mockGameEventFiring;

    private RoundBasedTimerManager roundBasedTimerManager;

    @BeforeEach
    void setUp() {
        mockOneTimeRoundBasedTimer = mock(OneTimeRoundBasedTimer.class);
        mockRecurringRoundBasedTimer = mock(RecurringRoundBasedTimer.class);
        mockGameEventFiring = mock(GameEventFiring.class);

        roundBasedTimerManager = new RoundBasedTimerManagerImpl(mockGameEventFiring);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new RoundBasedTimerManagerImpl(null));
    }

    @Test
    void testRegisterOneTimeRoundBasedTimerAndOneTimeRoundBasedTimersRepresentation() {
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        assertEquals(new ArrayList<>() {{
                         add(mockOneTimeRoundBasedTimer);
                     }},
                roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation());
    }

    @Test
    void testRegisterOneTimeRoundBasedTimerTwiceAndOneTimeRoundBasedTimersRepresentation() {
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        assertEquals(new ArrayList<>() {{
                         add(mockOneTimeRoundBasedTimer);
                     }},
                roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation());
    }

    @Test
    void testDeregisterOneTimeRoundBasedTimer() {
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        roundBasedTimerManager.deregisterOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        assertTrue(roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation().isEmpty());
    }

    @Test
    void testRegisterRecurringRoundBasedTimerAndOneTimeRoundBasedTimersRepresentation() {
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        assertEquals(new ArrayList<>() {{
                         add(mockRecurringRoundBasedTimer);
                     }},
                roundBasedTimerManager.recurringRoundBasedTimersRepresentation());
    }

    @Test
    void testRegisterRecurringRoundBasedTimerTwiceAndOneTimeRoundBasedTimersRepresentation() {
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        assertEquals(new ArrayList<>() {{
                         add(mockRecurringRoundBasedTimer);
                     }},
                roundBasedTimerManager.recurringRoundBasedTimersRepresentation());
    }

    @Test
    void testDeregisterRecurringRoundBasedTimer() {
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        roundBasedTimerManager.deregisterRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        assertTrue(roundBasedTimerManager.recurringRoundBasedTimersRepresentation().isEmpty());
    }

    @Test
    void testRegisterAndDeregisterTimersWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                roundBasedTimerManager.registerOneTimeRoundBasedTimer(null));
        assertThrows(IllegalArgumentException.class, () ->
                roundBasedTimerManager.deregisterOneTimeRoundBasedTimer(null));
        assertThrows(IllegalArgumentException.class, () ->
                roundBasedTimerManager.registerRecurringRoundBasedTimer(null));
        assertThrows(IllegalArgumentException.class, () ->
                roundBasedTimerManager.deregisterRecurringRoundBasedTimer(null));
    }

    @Test
    void testClear() {
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        roundBasedTimerManager.clear();

        assertTrue(roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation().isEmpty());
        assertTrue(roundBasedTimerManager.recurringRoundBasedTimersRepresentation().isEmpty());
    }

    @Test
    void testFireTimersForRoundsElapsedFiresOneTimeTimers() {
        int roundWhenGoesOff = randomInt();
        int priority = randomInt();
        when(mockOneTimeRoundBasedTimer.roundWhenGoesOff()).thenReturn(roundWhenGoesOff);
        when(mockOneTimeRoundBasedTimer.priority()).thenReturn(priority);
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        roundBasedTimerManager.fireTimersForRoundsElapsed(
                randomIntInRange(roundWhenGoesOff - randomIntInRange(2, 100), roundWhenGoesOff - 1),
                roundWhenGoesOff);

        verify(mockGameEventFiring, times(0)).registerEvent(mockOneTimeRoundBasedTimer, priority);

        roundBasedTimerManager.fireTimersForRoundsElapsed(roundWhenGoesOff, roundWhenGoesOff + 1);

        verify(mockGameEventFiring, times(1)).registerEvent(mockOneTimeRoundBasedTimer, priority);
    }

    @Test
    void testFireTimersForRoundsElapsedFiresRecurringTimers() {
        int roundModulo = randomIntInRange(5, 100);
        int roundOffset = randomIntInRange(1, roundModulo - 1);
        int priority = randomInt();
        int numberOfTimesToFire = randomIntInRange(2, 10);
        int roundAfterWhichAllFiringsHaveOccurred =
                ((numberOfTimesToFire - 1) * roundModulo) + roundOffset;

        when(mockRecurringRoundBasedTimer.roundModulo()).thenReturn(roundModulo);
        when(mockRecurringRoundBasedTimer.roundOffset()).thenReturn(roundOffset);
        when(mockRecurringRoundBasedTimer.priority()).thenReturn(priority);
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        roundBasedTimerManager.fireTimersForRoundsElapsed(roundOffset - 1, roundOffset);

        verify(mockGameEventFiring, times(0))
                .registerEvent(mockRecurringRoundBasedTimer, priority);

        roundBasedTimerManager.fireTimersForRoundsElapsed(roundOffset,
                roundAfterWhichAllFiringsHaveOccurred);

        verify(mockGameEventFiring, times(numberOfTimesToFire - 1))
                .registerEvent(mockRecurringRoundBasedTimer, priority);

        roundBasedTimerManager.fireTimersForRoundsElapsed(roundAfterWhichAllFiringsHaveOccurred,
                roundAfterWhichAllFiringsHaveOccurred + 1);

        verify(mockGameEventFiring, times(numberOfTimesToFire))
                .registerEvent(mockRecurringRoundBasedTimer, priority);
    }

    @Test
    void testFireTimersForRoundsElapsedWithInvalidParams() {
        int previousRound = randomInt();

        roundBasedTimerManager.fireTimersForRoundsElapsed(previousRound, previousRound + 1);
        assertThrows(IllegalArgumentException.class, () ->
                roundBasedTimerManager.fireTimersForRoundsElapsed(previousRound, previousRound));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RoundBasedTimerManager.class.getCanonicalName(),
                roundBasedTimerManager.getInterfaceName());
    }
}
