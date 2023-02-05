package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.RoundManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;
import soliloquy.specs.ruleset.gameconcepts.RoundEndHandling;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoundManagerImplTests {
    @Mock private Character mockCharacter1;
    @Mock private Character mockCharacter2;
    @Mock private Character mockCharacter3;
    @Mock private VariableCache mockVariableCacheFromFactory;
    @Mock private VariableCache mockVariableCache2;
    @Mock private VariableCache mockVariableCache3;
    @Mock private VariableCacheFactory mockVariableCacheFactory;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;
    @Mock private ActiveCharactersProvider mockActiveCharactersProvider;
    @Mock private GameZone mockGameZone;
    @Mock private TurnHandling mockTurnHandling;
    @Mock private RoundEndHandling mockRoundEndHandling;

    private RoundManager roundManager;

    @BeforeEach
    void setUp() {
        mockCharacter1 = mock(Character.class);
        mockCharacter2 = mock(Character.class);
        mockCharacter3 = mock(Character.class);

        mockVariableCacheFromFactory = mock(VariableCache.class);
        mockVariableCache2 = mock(VariableCache.class);
        mockVariableCache3 = mock(VariableCache.class);
        mockVariableCacheFactory = mock(VariableCacheFactory.class);
        when(mockVariableCacheFactory.make()).thenReturn(mockVariableCacheFromFactory);

        var activeCharactersToProvide = listOf(
                new Pair<>(mockCharacter2, mockVariableCache2),
                new Pair<>(mockCharacter3, mockVariableCache3));
        mockActiveCharactersProvider = mock(ActiveCharactersProvider.class);
        when(mockActiveCharactersProvider.generateInTurnOrder(any()))
                .thenReturn(activeCharactersToProvide);

        mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        mockGameZone = mock(GameZone.class);

        mockTurnHandling = mock(TurnHandling.class);
        mockRoundEndHandling = mock(RoundEndHandling.class);

        roundManager = new RoundManagerImpl(mockVariableCacheFactory,
                mockRoundBasedTimerManager, mockActiveCharactersProvider, () -> mockGameZone,
                mockTurnHandling, mockRoundEndHandling);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(null, mockRoundBasedTimerManager,
                        mockActiveCharactersProvider, () -> mockGameZone, mockTurnHandling,
                        mockRoundEndHandling));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(mockVariableCacheFactory, null,
                        mockActiveCharactersProvider, () -> mockGameZone, mockTurnHandling,
                        mockRoundEndHandling));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(mockVariableCacheFactory, mockRoundBasedTimerManager,
                        null, () -> mockGameZone, mockTurnHandling, mockRoundEndHandling));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(mockVariableCacheFactory, mockRoundBasedTimerManager,
                        mockActiveCharactersProvider, null, mockTurnHandling,
                        mockRoundEndHandling));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(mockVariableCacheFactory, mockRoundBasedTimerManager,
                        mockActiveCharactersProvider, () -> mockGameZone, null,
                        mockRoundEndHandling));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(mockVariableCacheFactory, mockRoundBasedTimerManager,
                        mockActiveCharactersProvider, () -> mockGameZone, mockTurnHandling, null));
    }

    @Test
    void testSetCharacterPositionInQueueToAddCharacterWithEmptyData() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));

        assertTrue(roundManager.characterIsInQueue(mockCharacter1));
        assertSame(mockVariableCacheFromFactory,
                roundManager.characterRoundData(mockCharacter1));
    }

    @Test
    void testCharacterIsInQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> roundManager.characterIsInQueue(null));
    }

    @Test
    void testSetCharacterPositionInQueueSetsActualPositionAndCharacterPositionInQueue() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));
        roundManager.setCharacterPositionInQueue(mockCharacter2, 0);

        assertEquals(0, roundManager.getCharacterPositionInQueue(mockCharacter2));
        assertEquals(1, roundManager.getCharacterPositionInQueue(mockCharacter1));
    }

    @Test
    void testSetCharacterPositionInQueueDoesNotOverwriteDataForCharactersAlreadyInQueue() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));
        roundManager.setCharacterRoundData(mockCharacter1, mockVariableCache2);

        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));

        assertSame(mockVariableCache2, roundManager.characterRoundData(mockCharacter1));
    }

    @Test
    void testGetCharacterPositionInQueueForUnaddedCharacter() {
        assertNull(roundManager.getCharacterPositionInQueue(mockCharacter1));
    }

    @Test
    void testSetCharacterPositionInQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                roundManager.setCharacterPositionInQueue(null, randomIntWithInclusiveFloor(0)));
        assertThrows(IllegalArgumentException.class, () ->
                roundManager.setCharacterPositionInQueue(mockCharacter1, -1));
    }

    @Test
    void testCharacterRoundDataWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> roundManager.characterRoundData(null));
    }

    @Test
    void testGetCharacterPositionInQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                roundManager.getCharacterPositionInQueue(null));
    }

    @Test
    void testQueueSize() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));
        roundManager.setCharacterPositionInQueue(mockCharacter2, randomIntWithInclusiveFloor(0));

        assertEquals(2, roundManager.queueSize());
    }

    @Test
    void testSetCharacterRoundData() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));

        roundManager.setCharacterRoundData(mockCharacter1, mockVariableCache2);

        assertSame(mockVariableCache2, roundManager.characterRoundData(mockCharacter1));
    }

    @Test
    void testSetUnaddedCharacterRoundData() {
        roundManager.setCharacterRoundData(mockCharacter1, mockVariableCache2);

        assertNull(roundManager.characterRoundData(mockCharacter1));
    }

    @Test
    void testSetCharacterRoundDataWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                roundManager.setCharacterRoundData(null, mockVariableCache2));
        assertThrows(IllegalArgumentException.class, () ->
                roundManager.setCharacterRoundData(mockCharacter1, null));
    }

    @Test
    void testRemoveCharacterFromQueue() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));

        var characterRemoved = roundManager.removeCharacterFromQueue(mockCharacter1);

        assertTrue(characterRemoved);
        assertNull(roundManager.characterRoundData(mockCharacter1));
    }

    @Test
    void testRemoveUnaddedCharacterFromQueue() {
        assertFalse(roundManager.removeCharacterFromQueue(mockCharacter1));
    }

    @Test
    void testRemoveCharacterFromQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                roundManager.removeCharacterFromQueue(null));
    }

    @Test
    void testClearQueue() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));
        roundManager.setCharacterPositionInQueue(mockCharacter2, randomIntWithInclusiveFloor(0));

        roundManager.clearQueue();

        assertEquals(0, roundManager.queueSize());
        assertFalse(roundManager.characterIsInQueue(mockCharacter1));
        assertNull(roundManager.characterRoundData(mockCharacter1));
        assertFalse(roundManager.characterIsInQueue(mockCharacter2));
        assertNull(roundManager.characterRoundData(mockCharacter2));
    }

    @Test
    void testCharacterQueueRepresentation() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));
        roundManager.setCharacterPositionInQueue(mockCharacter2, randomIntWithInclusiveFloor(0));
        roundManager.setCharacterRoundData(mockCharacter2, mockVariableCache2);

        var expectedOutput = listOf(
                new Pair<>(mockCharacter1, mockVariableCacheFromFactory),
                new Pair<>(mockCharacter2, mockVariableCache2));

        var characterQueueRepresentation = roundManager.characterQueueRepresentation();
        var characterQueueRepresentation2 = roundManager.characterQueueRepresentation();

        assertNotNull(characterQueueRepresentation);
        assertEquals(expectedOutput, characterQueueRepresentation);
        assertNotSame(characterQueueRepresentation, characterQueueRepresentation2);
    }

    @Test
    void testActiveCharacterDefaultsToNull() {
        assertNull(roundManager.activeCharacter());
    }

    @Test
    void testRunActiveCharacterTurn() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));
    }

    @Test
    void testRunActiveCharacterTurnDoesNotThrowExceptionWhenQueueIsEmpty() {
        roundManager.runActiveCharacterTurn();
    }

    @Test
    void testActiveCharacterReturnsFirstCharacterInQueue() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, 0);
        roundManager.setCharacterPositionInQueue(mockCharacter2, 1);

        assertSame(mockCharacter1, roundManager.activeCharacter());
    }

    @Test
    void testRunActiveCharacterTurnWithCharactersRemainingInRoundAndActiveCharacter() {
        roundManager.setCharacterPositionInQueue(mockCharacter1, 0);
        roundManager.setCharacterPositionInQueue(mockCharacter2, 1);

        roundManager.runActiveCharacterTurn();

        assertSame(mockCharacter2, roundManager.activeCharacter());
    }

    @Test
    void testSetAndGetRoundNumber() {
        var newRoundNumber = randomInt();

        roundManager.setRoundNumber(newRoundNumber);

        assertEquals(newRoundNumber, roundManager.getRoundNumber());
    }

    // Round end tests
    @Test
    void testSetActiveCharactersProviderAndAdvanceSingleRound() {
        var initialRound = randomInt();
        roundManager.setRoundNumber(initialRound);
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));

        roundManager.advanceRounds(1);

        assertRoundsAdvanced(initialRound, 1);
    }

    @Test
    void testAdvanceMultipleRounds() {
        var initialRound = randomInt();
        var roundsToAdvance = randomIntInRange(2, 200);
        roundManager.setRoundNumber(initialRound);
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));

        roundManager.advanceRounds(roundsToAdvance);

        assertRoundsAdvanced(initialRound, roundsToAdvance);
    }

    @Test
    void testAdvanceRoundsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> roundManager.advanceRounds(0));
    }

    @Test
    void testRunActiveCharacterTurnAdvancesRoundWhenQueueIsEmpty() {
        var initialRound = randomInt();
        roundManager.setRoundNumber(initialRound);

        roundManager.runActiveCharacterTurn();

        assertRoundsAdvanced(initialRound, 1);
    }

    @Test
    void testRunActiveCharacterTurnAdvancesWhenOneCharacterLeftInQueue() {
        var initialRound = randomInt();
        roundManager.setRoundNumber(initialRound);
        roundManager.setCharacterPositionInQueue(mockCharacter1, randomIntWithInclusiveFloor(0));

        roundManager.runActiveCharacterTurn();

        assertRoundsAdvanced(initialRound, 1);
    }

    private void assertRoundsAdvanced(int initialRound, int roundsToAdvance) {
        assertEquals(initialRound + roundsToAdvance, roundManager.getRoundNumber());
        assertEquals(2, roundManager.queueSize());
        assertFalse(roundManager.characterIsInQueue(mockCharacter1));
        assertNull(roundManager.characterRoundData(mockCharacter1));
        assertEquals(0, roundManager.getCharacterPositionInQueue(mockCharacter2));
        assertEquals(1, roundManager.getCharacterPositionInQueue(mockCharacter3));
        assertSame(mockVariableCache2, roundManager.characterRoundData(mockCharacter2));
        assertSame(mockVariableCache3, roundManager.characterRoundData(mockCharacter3));
        verify(mockRoundBasedTimerManager)
                .fireTimersForRoundsElapsed(initialRound, initialRound + roundsToAdvance);
        verify(mockActiveCharactersProvider, times(1)).generateInTurnOrder(mockGameZone);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RoundManager.class.getCanonicalName(), roundManager.getInterfaceName());
    }
}
