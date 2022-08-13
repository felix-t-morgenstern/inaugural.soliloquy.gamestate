package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.RoundManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.ArrayList;
import java.util.List;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoundManagerImplTests {
    @Mock private Character _mockCharacter1;
    @Mock private Character _mockCharacter2;
    @Mock private Character _mockCharacter3;
    @Mock private VariableCache _mockVariableCacheFromFactory;
    @Mock private VariableCache _mockVariableCache2;
    @Mock private VariableCache _mockVariableCache3;
    @Mock private VariableCacheFactory _mockVariableCacheFactory;
    @Mock private RoundBasedTimerManager _mockRoundBasedTimerManager;
    private List<Pair<Character, VariableCache>> _activeCharactersToProvide;

    private RoundManager _roundManager;

    @BeforeEach
    void setUp() {
        _mockCharacter1 = mock(Character.class);
        _mockCharacter2 = mock(Character.class);
        _mockCharacter3 = mock(Character.class);

        _mockVariableCacheFromFactory = mock(VariableCache.class);
        _mockVariableCache2 = mock(VariableCache.class);
        _mockVariableCache3 = mock(VariableCache.class);
        _mockVariableCacheFactory = mock(VariableCacheFactory.class);
        when(_mockVariableCacheFactory.make()).thenReturn(_mockVariableCacheFromFactory);

        _activeCharactersToProvide = new ArrayList<>() {{
            add(new Pair<>(_mockCharacter2, _mockVariableCache2));
            add(new Pair<>(_mockCharacter3, _mockVariableCache3));
        }};

        _mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        _roundManager = new RoundManagerImpl(_mockVariableCacheFactory,
                _mockRoundBasedTimerManager);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new RoundManagerImpl(
                null, _mockRoundBasedTimerManager));
        assertThrows(IllegalArgumentException.class, () -> new RoundManagerImpl(
                _mockVariableCacheFactory, null));
    }

    @Test
    void testSetCharacterPositionInQueueToAddCharacterWithEmptyData() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));

        assertTrue(_roundManager.characterIsInQueue(_mockCharacter1));
        assertSame(_mockVariableCacheFromFactory,
                _roundManager.characterRoundData(_mockCharacter1));
    }

    @Test
    void testCharacterIsInQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _roundManager.characterIsInQueue(null));
    }

    @Test
    void testSetCharacterPositionInQueueSetsActualPositionAndCharacterPositionInQueue() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));
        _roundManager.setCharacterPositionInQueue(_mockCharacter2, 0);

        assertEquals(0, _roundManager.getCharacterPositionInQueue(_mockCharacter2));
        assertEquals(1, _roundManager.getCharacterPositionInQueue(_mockCharacter1));
    }

    @Test
    void testSetCharacterPositionInQueueDoesNotOverwriteDataForCharactersAlreadyInQueue() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));
        _roundManager.setCharacterRoundData(_mockCharacter1, _mockVariableCache2);

        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));

        assertSame(_mockVariableCache2, _roundManager.characterRoundData(_mockCharacter1));
    }

    @Test
    void testGetCharacterPositionInQueueForUnaddedCharacter() {
        assertNull(_roundManager.getCharacterPositionInQueue(_mockCharacter1));
    }

    @Test
    void testSetCharacterPositionInQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _roundManager.setCharacterPositionInQueue(null, randomIntWithInclusiveFloor(0)));
        assertThrows(IllegalArgumentException.class, () ->
                _roundManager.setCharacterPositionInQueue(_mockCharacter1, -1));
    }

    @Test
    void testCharacterRoundDataWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _roundManager.characterRoundData(null));
    }

    @Test
    void testGetCharacterPositionInQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _roundManager.getCharacterPositionInQueue(null));
    }

    @Test
    void testQueueSize() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));
        _roundManager.setCharacterPositionInQueue(_mockCharacter2, randomIntWithInclusiveFloor(0));

        assertEquals(2, _roundManager.queueSize());
    }

    @Test
    void testSetCharacterRoundData() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));

        _roundManager.setCharacterRoundData(_mockCharacter1, _mockVariableCache2);

        assertSame(_mockVariableCache2, _roundManager.characterRoundData(_mockCharacter1));
    }

    @Test
    void testSetUnaddedCharacterRoundData() {
        _roundManager.setCharacterRoundData(_mockCharacter1, _mockVariableCache2);

        assertNull(_roundManager.characterRoundData(_mockCharacter1));
    }

    @Test
    void testSetCharacterRoundDataWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _roundManager.setCharacterRoundData(null, _mockVariableCache2));
        assertThrows(IllegalArgumentException.class, () ->
                _roundManager.setCharacterRoundData(_mockCharacter1, null));
    }

    @Test
    void testRemoveCharacterFromQueue() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));

        boolean characterRemoved = _roundManager.removeCharacterFromQueue(_mockCharacter1);

        assertTrue(characterRemoved);
        assertNull(_roundManager.characterRoundData(_mockCharacter1));
    }

    @Test
    void testRemoveUnaddedCharacterFromQueue() {
        assertFalse(_roundManager.removeCharacterFromQueue(_mockCharacter1));
    }

    @Test
    void testRemoveCharacterFromQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _roundManager.removeCharacterFromQueue(null));
    }

    @Test
    void testClearQueue() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));
        _roundManager.setCharacterPositionInQueue(_mockCharacter2, randomIntWithInclusiveFloor(0));

        _roundManager.clearQueue();

        assertEquals(0, _roundManager.queueSize());
        assertFalse(_roundManager.characterIsInQueue(_mockCharacter1));
        assertNull(_roundManager.characterRoundData(_mockCharacter1));
        assertFalse(_roundManager.characterIsInQueue(_mockCharacter2));
        assertNull(_roundManager.characterRoundData(_mockCharacter2));
    }

    @Test
    void testCharacterQueueRepresentation() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));
        _roundManager.setCharacterPositionInQueue(_mockCharacter2, randomIntWithInclusiveFloor(0));
        _roundManager.setCharacterRoundData(_mockCharacter2, _mockVariableCache2);

        List<Pair<Character, VariableCache>> expectedOutput = new ArrayList<>() {{
            add(new Pair<>(_mockCharacter1, _mockVariableCacheFromFactory));
            add(new Pair<>(_mockCharacter2, _mockVariableCache2));
        }};

        List<Pair<Character, VariableCache>> characterQueueRepresentation =
                _roundManager.characterQueueRepresentation();
        List<Pair<Character, VariableCache>> characterQueueRepresentation2 =
                _roundManager.characterQueueRepresentation();

        assertNotNull(characterQueueRepresentation);
        assertEquals(expectedOutput, characterQueueRepresentation);
        assertNotSame(characterQueueRepresentation, characterQueueRepresentation2);
    }

    @Test
    void testActiveCharacterDefaultsToNull() {
        assertNull(_roundManager.activeCharacter());
    }

    @Test
    void testEndActiveCharacterTurnDoesNotThrowExceptionWhenQueueIsEmpty() {
        _roundManager.setActiveCharactersProvider(() -> _activeCharactersToProvide);

        _roundManager.endActiveCharacterTurn();
    }

    @Test
    void testActiveCharacterReturnsFirstCharacterInQueue() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, 0);
        _roundManager.setCharacterPositionInQueue(_mockCharacter2, 1);

        assertSame(_mockCharacter1, _roundManager.activeCharacter());
    }

    @Test
    void testEndActiveCharacterTurnWithCharactersRemainingInRoundAndActiveCharacter() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, 0);
        _roundManager.setCharacterPositionInQueue(_mockCharacter2, 1);
        _roundManager.setActiveCharactersProvider(() -> _activeCharactersToProvide);

        _roundManager.endActiveCharacterTurn();

        assertSame(_mockCharacter2, _roundManager.activeCharacter());
    }

    @Test
    void testSetAndGetRoundNumber() {
        int newRoundNumber = randomInt();

        _roundManager.setRoundNumber(newRoundNumber);

        assertEquals(newRoundNumber, _roundManager.getRoundNumber());
    }

    // Round end tests
    @Test
    void testSetActiveCharactersProviderAndAdvanceSingleRound() {
        int initialRound = randomInt();
        _roundManager.setRoundNumber(initialRound);
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));

        _roundManager.setActiveCharactersProvider(() -> _activeCharactersToProvide);
        _roundManager.advanceRounds(1);

        assertRoundsAdvanced(initialRound, 1);
    }

    @Test
    void testAdvanceMultipleRounds() {
        int initialRound = randomInt();
        int roundsToAdvance = randomIntInRange(2, 200);
        _roundManager.setRoundNumber(initialRound);
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));

        _roundManager.setActiveCharactersProvider(() -> _activeCharactersToProvide);
        _roundManager.advanceRounds(roundsToAdvance);

        assertRoundsAdvanced(initialRound, roundsToAdvance);
    }

    @Test
    void testSetActiveCharactersProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _roundManager.setActiveCharactersProvider(null));
    }

    @Test
    void testAdvanceRoundsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _roundManager.advanceRounds(0));
    }

    @Test
    void testAdvanceRoundWithoutActiveCharactersProvided() {
        assertThrows(IllegalStateException.class, () -> _roundManager.advanceRounds(1));
    }

    // Ensure that ending active character turn advances round when one or zero characters remain
    // Ensure that active characters provided is defined in those scenarios

    @Test
    void testEndActiveCharacterTurnAdvancesRoundWhenQueueIsEmpty() {
        int initialRound = randomInt();
        _roundManager.setRoundNumber(initialRound);
        _roundManager.setActiveCharactersProvider(() -> _activeCharactersToProvide);

        _roundManager.endActiveCharacterTurn();

        assertRoundsAdvanced(initialRound, 1);
    }

    @Test
    void testEndActiveCharacterTurnAdvancesWhenOneCharacterLeftInQueue() {
        int initialRound = randomInt();
        _roundManager.setRoundNumber(initialRound);
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));
        _roundManager.setActiveCharactersProvider(() -> _activeCharactersToProvide);

        _roundManager.endActiveCharacterTurn();

        assertRoundsAdvanced(initialRound, 1);
    }

    @Test
    void testEndActiveCharacterTurnWithoutActiveCharactersProvided() {
        // NB: Characters are added to ensure that exception is thrown even when round does not
        // advance
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));
        _roundManager.setCharacterPositionInQueue(_mockCharacter2, randomIntWithInclusiveFloor(0));

        assertThrows(IllegalStateException.class, () -> _roundManager.endActiveCharacterTurn());
    }

    private void assertRoundsAdvanced(int initialRound, int roundsToAdvance) {
        assertEquals(initialRound + roundsToAdvance, _roundManager.getRoundNumber());
        assertEquals(2, _roundManager.queueSize());
        assertFalse(_roundManager.characterIsInQueue(_mockCharacter1));
        assertNull(_roundManager.characterRoundData(_mockCharacter1));
        assertEquals(0, _roundManager.getCharacterPositionInQueue(_mockCharacter2));
        assertEquals(1, _roundManager.getCharacterPositionInQueue(_mockCharacter3));
        assertSame(_mockVariableCache2, _roundManager.characterRoundData(_mockCharacter2));
        assertSame(_mockVariableCache3, _roundManager.characterRoundData(_mockCharacter3));
        verify(_mockRoundBasedTimerManager)
                .fireTimersForRoundsElapsed(initialRound, initialRound + roundsToAdvance);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RoundManager.class.getCanonicalName(), _roundManager.getInterfaceName());
    }
}
