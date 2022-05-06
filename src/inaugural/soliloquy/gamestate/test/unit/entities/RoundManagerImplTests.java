package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.RoundManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import static inaugural.soliloquy.tools.random.Random.randomIntWithInclusiveFloor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoundManagerImplTests {
    @Mock
    private Character _mockCharacter1;
    @Mock
    private Character _mockCharacter2;
    @Mock
    private VariableCache _mockVariableCacheFromFactory;
    @Mock
    private VariableCache _mockVariableCache2;
    @Mock
    private VariableCacheFactory _mockVariableCacheFactory;
    @Mock
    private RoundBasedTimerManager _mockRoundBasedTimerManager;

    private RoundManager _roundManager;

    @BeforeEach
    void setUp() {
        _mockCharacter1 = mock(Character.class);
        _mockCharacter2 = mock(Character.class);

        _mockVariableCacheFromFactory = mock(VariableCache.class);
        _mockVariableCache2 = mock(VariableCache.class);
        _mockVariableCacheFactory = mock(VariableCacheFactory.class);
        when(_mockVariableCacheFactory.make()).thenReturn(_mockVariableCacheFromFactory);

        _mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        _roundManager = new RoundManagerImpl(_mockVariableCacheFactory);
    }

    @Test
    void testConstructorWithInvalidParams() {
        // TODO: fail("fill in at end");
    }

    @Test
    void testSetCharacterPositionInQueueToAddCharacterWithEmptyData() {
        _roundManager.setCharacterPositionInQueue(_mockCharacter1, randomIntWithInclusiveFloor(0));

        assertTrue(_roundManager.characterIsInQueue(_mockCharacter1));
        assertSame(_mockVariableCacheFromFactory, _roundManager.characterRoundData(_mockCharacter1));
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
    void testGetInterfaceName() {
        assertEquals(RoundManager.class.getCanonicalName(), _roundManager.getInterfaceName());
    }
}
