package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.RoundBasedTimerManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.gamestate.entities.gameevents.GameEventFiring;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;


import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomIntInRange;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoundBasedTimerManagerImplTests {
    @Mock private OneTimeRoundBasedTimer mockOneTimeRoundBasedTimer;
    @Mock private RecurringRoundBasedTimer mockRecurringRoundBasedTimer;
    @Mock private GameEventFiring mockGameEventFiring;

    private RoundBasedTimerManager roundBasedTimerManager;

    @BeforeEach
    public void setUp() {
        mockOneTimeRoundBasedTimer = mock(OneTimeRoundBasedTimer.class);
        mockRecurringRoundBasedTimer = mock(RecurringRoundBasedTimer.class);
        mockGameEventFiring = mock(GameEventFiring.class);

        roundBasedTimerManager = new RoundBasedTimerManagerImpl(mockGameEventFiring);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new RoundBasedTimerManagerImpl(null));
    }

    @Test
    public void testRegisterOneTimeRoundBasedTimerAndOneTimeRoundBasedTimersRepresentation() {
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        assertEquals(listOf(mockOneTimeRoundBasedTimer),
                roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation());
    }

    @Test
    public void testRegisterOneTimeRoundBasedTimerTwiceAndOneTimeRoundBasedTimersRepresentation() {
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        assertEquals(listOf(mockOneTimeRoundBasedTimer),
                roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation());
    }

    @Test
    public void testDeregisterOneTimeRoundBasedTimer() {
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        roundBasedTimerManager.deregisterOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        assertTrue(roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation().isEmpty());
    }

    @Test
    public void testRegisterRecurringRoundBasedTimerAndOneTimeRoundBasedTimersRepresentation() {
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        assertEquals(listOf(mockRecurringRoundBasedTimer),
                roundBasedTimerManager.recurringRoundBasedTimersRepresentation());
    }

    @Test
    public void testRegisterRecurringRoundBasedTimerTwiceAndOneTimeRoundBasedTimersRepresentation() {
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        assertEquals(listOf(mockRecurringRoundBasedTimer),
                roundBasedTimerManager.recurringRoundBasedTimersRepresentation());
    }

    @Test
    public void testDeregisterRecurringRoundBasedTimer() {
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        roundBasedTimerManager.deregisterRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        assertTrue(roundBasedTimerManager.recurringRoundBasedTimersRepresentation().isEmpty());
    }

    @Test
    public void testRegisterAndDeregisterTimersWithInvalidArgs() {
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
    public void testClear() {
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);
        roundBasedTimerManager.registerRecurringRoundBasedTimer(mockRecurringRoundBasedTimer);

        roundBasedTimerManager.clear();

        assertTrue(roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation().isEmpty());
        assertTrue(roundBasedTimerManager.recurringRoundBasedTimersRepresentation().isEmpty());
    }

    @Test
    public void testFireTimersForRoundsElapsedFiresOneTimeTimers() {
        var roundWhenGoesOff = randomInt();
        var priority = randomInt();
        when(mockOneTimeRoundBasedTimer.roundWhenGoesOff()).thenReturn(roundWhenGoesOff);
        when(mockOneTimeRoundBasedTimer.priority()).thenReturn(priority);
        roundBasedTimerManager.registerOneTimeRoundBasedTimer(mockOneTimeRoundBasedTimer);

        roundBasedTimerManager.fireTimersForRoundsElapsed(
                randomIntInRange(roundWhenGoesOff - randomIntInRange(2, 100), roundWhenGoesOff - 1),
                roundWhenGoesOff);

        verify(mockGameEventFiring, times(0)).registerEvent(mockOneTimeRoundBasedTimer, priority);

        roundBasedTimerManager.fireTimersForRoundsElapsed(roundWhenGoesOff, roundWhenGoesOff + 1);

        verify(mockGameEventFiring, once()).registerEvent(mockOneTimeRoundBasedTimer, priority);
    }

    @Test
    public void testFireTimersForRoundsElapsedFiresRecurringTimers() {
        var roundModulo = randomIntInRange(5, 100);
        var roundOffset = randomIntInRange(1, roundModulo - 1);
        var priority = randomInt();
        var numberOfTimesToFire = randomIntInRange(2, 10);
        var roundAfterWhichAllFiringsHaveOccurred =
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
    public void testFireTimersForRoundsElapsedWithInvalidArgs() {
        var previousRound = randomInt();

        roundBasedTimerManager.fireTimersForRoundsElapsed(previousRound, previousRound + 1);
        assertThrows(IllegalArgumentException.class, () ->
                roundBasedTimerManager.fireTimersForRoundsElapsed(previousRound, previousRound));
    }
}
