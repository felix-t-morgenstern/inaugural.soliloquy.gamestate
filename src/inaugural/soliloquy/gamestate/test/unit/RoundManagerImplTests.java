package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.RoundManagerImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoundManagerImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final PairFactory PAIR_FACTORY = new PairFactoryStub();
    private final VariableCacheFactoryStub VARIABLE_CACHE_FACTORY = new VariableCacheFactoryStub();
    private final ActiveCharactersProviderStub ACTIVE_CHARACTERS_PROVIDER =
            new ActiveCharactersProviderStub();
    private final TurnHandlingStub TURN_HANDLING = new TurnHandlingStub();
    private final RoundEndHandlingStub ROUND_END_HANDLING = new RoundEndHandlingStub();

    private RoundManager _roundManager;

    @BeforeEach
    void setUp() {
        _roundManager = new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                ROUND_END_HANDLING);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(null, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, null,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        null, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, null, TURN_HANDLING,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, null,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RoundManager.class.getCanonicalName(), _roundManager.getInterfaceName());
    }

    @Test
    void testSetAndGetNewCharacterPositionInQueue() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, 2);

        assertEquals(0, _roundManager.getCharacterPositionInQueue(character1));
        assertEquals(1, _roundManager.getCharacterPositionInQueue(character2));
        assertEquals(2, _roundManager.getCharacterPositionInQueue(character3));
    }

    @Test
    void testSetCharacterPositionWithRoundData() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();

        VariableCache roundData1 = new VariableCacheStub();
        VariableCache roundData2 = new VariableCacheStub();
        VariableCache roundData3 = new VariableCacheStub();

        _roundManager.setCharacterPositionInQueue(character1, 0, roundData1);
        _roundManager.setCharacterPositionInQueue(character2, 1, roundData2);
        _roundManager.setCharacterPositionInQueue(character3, 2, roundData3);

        assertEquals(0, _roundManager.getCharacterPositionInQueue(character1));
        assertEquals(1, _roundManager.getCharacterPositionInQueue(character2));
        assertEquals(2, _roundManager.getCharacterPositionInQueue(character3));

        assertSame(roundData1, _roundManager.characterRoundData(character1));
        assertSame(roundData2, _roundManager.characterRoundData(character2));
        assertSame(roundData3, _roundManager.characterRoundData(character3));
    }

    @Test
    void testQueueSize() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();
        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, 2);

        int queueSize = _roundManager.queueSize();

        assertEquals(3, queueSize);
    }

    @Test
    void testCharacterRoundData() {
        Character character = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character, 0);

        assertNotNull(_roundManager.characterRoundData(character));
    }

    @Test
    void testCharacterRoundDataPairArchetypes() {
        Character character = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character, 0);

        VariableCache data = _roundManager.characterRoundData(character);
        assertNotNull(data);
        assertEquals(VariableCache.class.getCanonicalName(), data.getInterfaceName());
    }

    @Test
    void testSetCharacterPositionDisplacesOtherCharacters() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, 1);

        assertEquals(0, _roundManager.getCharacterPositionInQueue(character1));
    }

    @Test
    void testPlaceCharacterAtBackOfQueue() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, Integer.MAX_VALUE);

        assertEquals(2, _roundManager.getCharacterPositionInQueue(character3));
    }

    @Test
    void testMoveCharacterToBackOfQueue() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, 2);
        _roundManager.setCharacterPositionInQueue(character1, Integer.MAX_VALUE);

        assertEquals(2, _roundManager.getCharacterPositionInQueue(character1));
        assertEquals(0, _roundManager.getCharacterPositionInQueue(character2));
        assertEquals(1, _roundManager.getCharacterPositionInQueue(character3));
    }

    @Test
    void testMoveToMiddleOfQueue() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, 2);
        _roundManager.setCharacterPositionInQueue(character1, 1);

        assertEquals(1, _roundManager.getCharacterPositionInQueue(character1));
        assertEquals(0, _roundManager.getCharacterPositionInQueue(character2));
        assertEquals(2, _roundManager.getCharacterPositionInQueue(character3));
    }

    @Test
    void testDataNotCreatedForExistingCharacter() {
        Character character = new CharacterStub();
        _roundManager.setCharacterPositionInQueue(character, 0);
        VariableCache initialData = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;

        _roundManager.setCharacterPositionInQueue(character, 0);

        assertSame(initialData, VARIABLE_CACHE_FACTORY._mostRecentlyCreated);
    }

    @Test
    void testSetAndGetCharacterPositionWithInvalidParameters() {
        VariableCache roundData = new VariableCacheStub();

        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(null, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(new CharacterStub(), -1));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.getCharacterPositionInQueue(null));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(null, 0, roundData));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(new CharacterStub(), -1,
                        roundData));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(new CharacterStub(), 0, null));
    }

    @Test
    void testCharacterRoundDataWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _roundManager.characterRoundData(null));
    }

    @Test
    void testCharacterIsInQueue() {
        Character character = new CharacterStub();
        _roundManager.setCharacterPositionInQueue(character, 0);

        assertTrue(_roundManager.characterIsInQueue(character));
    }

    @Test
    void testCharacterIsInQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _roundManager.characterIsInQueue(null));
    }

    @Test
    void testRemoveCharacterFromQueue() {
        Character character = new CharacterStub();
        _roundManager.setCharacterPositionInQueue(character, 0);
        VariableCache initialData = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;

        assertTrue(_roundManager.removeCharacterFromQueue(character));
        assertFalse(_roundManager.removeCharacterFromQueue(character));

        _roundManager.setCharacterPositionInQueue(character, 0);
        VariableCache dataAfterSecondAdd = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;

        assertNotEquals(initialData, dataAfterSecondAdd);
    }

    @Test
    void testClearQueue() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();
        _roundManager.setCharacterPositionInQueue(character1, 0);
        VariableCache initialData1 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;
        _roundManager.setCharacterPositionInQueue(character2, 0);
        VariableCache initialData2 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;
        _roundManager.setCharacterPositionInQueue(character3, 0);
        VariableCache initialData3 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;

        _roundManager.clearQueue();

        assertEquals(0, _roundManager.queueSize());
        assertFalse(_roundManager.characterIsInQueue(character1));
        assertFalse(_roundManager.characterIsInQueue(character2));
        assertFalse(_roundManager.characterIsInQueue(character3));

        _roundManager.setCharacterPositionInQueue(character1, Integer.MAX_VALUE);
        VariableCache dataAfterSecondAdd1 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;
        _roundManager.setCharacterPositionInQueue(character2, Integer.MAX_VALUE);
        VariableCache dataAfterSecondAdd2 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;
        _roundManager.setCharacterPositionInQueue(character3, Integer.MAX_VALUE);
        VariableCache dataAfterSecondAdd3 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;

        assertNotEquals(initialData1, dataAfterSecondAdd1);
        assertNotEquals(initialData2, dataAfterSecondAdd2);
        assertNotEquals(initialData3, dataAfterSecondAdd3);
    }

    @Test
    void testCharacterQueueRepresentation() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();
        _roundManager.setCharacterPositionInQueue(character1, Integer.MAX_VALUE);
        VariableCache initialData1 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;
        _roundManager.setCharacterPositionInQueue(character2, Integer.MAX_VALUE);
        VariableCache initialData2 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;
        _roundManager.setCharacterPositionInQueue(character3, Integer.MAX_VALUE);
        VariableCache initialData3 = VARIABLE_CACHE_FACTORY._mostRecentlyCreated;

        ReadableCollection<ReadablePair<Character,VariableCache>> representation =
                _roundManager.characterQueueRepresentation();

        assertNotNull(representation);
        assertNotNull(representation.getArchetype());
        assertEquals(Pair.class.getCanonicalName() + "<" + Character.class.getCanonicalName() +
                "," + VariableCache.class.getCanonicalName() + ">",
                representation.getArchetype().getInterfaceName());
        assertEquals(3, representation.size());
        assertSame(character1, representation.get(0).getItem1());
        assertSame(character2, representation.get(1).getItem1());
        assertSame(character3, representation.get(2).getItem1());
        assertSame(initialData1, representation.get(0).getItem2());
        assertSame(initialData2, representation.get(1).getItem2());
        assertSame(initialData3, representation.get(2).getItem2());
    }

    @Test
    void testEndActiveCharacterTurnAndActiveCharacter() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character1, Integer.MAX_VALUE);
        _roundManager.setCharacterPositionInQueue(character2, Integer.MAX_VALUE);

        _roundManager.endActiveCharacterTurn();

        assertSame(character1, _roundManager.activeCharacter());

        _roundManager.removeCharacterFromQueue(character1);

        _roundManager.endActiveCharacterTurn();

        assertEquals(1, TURN_HANDLING.TurnEnds.size());
        assertSame(character1, TURN_HANDLING.TurnEnds.get(0).getItem1());
        assertEquals(1, TURN_HANDLING.TurnEnds.get(0).getItem2());
        assertEquals(2, TURN_HANDLING.TurnStarts.size());
        assertSame(character1, TURN_HANDLING.TurnStarts.get(0).getItem1());
        assertEquals(1, TURN_HANDLING.TurnStarts.get(0).getItem2());
        assertSame(character2, TURN_HANDLING.TurnStarts.get(1).getItem1());
        assertEquals(1, TURN_HANDLING.TurnStarts.get(1).getItem2());

        assertSame(character2, _roundManager.activeCharacter());
    }

    @Test
    void testEndActiveCharacterTurnWhenNoCharactersAreInQueue() {
        ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.clear();
        final int roundNumber = 123;
        _roundManager.setRoundNumber(roundNumber);

        _roundManager.endActiveCharacterTurn();

        assertNull(_roundManager.activeCharacter());
        assertEquals(roundNumber + 1, _roundManager.getRoundNumber());
    }

    @Test
    void testSetAndGetRoundNumber() {
        final int roundNumber = 123;

        _roundManager.setRoundNumber(roundNumber);

        assertEquals(roundNumber, _roundManager.getRoundNumber());
    }

    @Test
    void testOneTimeTimers() {
        assertNotNull(_roundManager.oneTimeTimersRepresentation());
    }

    @Test
    void testOneTimeTimersRepresentation() {
        OneTimeTimer oneTimeTimer1 = new OneTimeTimerStub();
        OneTimeTimer oneTimeTimer2 = new OneTimeTimerStub();
        OneTimeTimer oneTimeTimer3 = new OneTimeTimerStub();

        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer1);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer2);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer3);

        ReadableCollection<OneTimeTimer> representation =
                _roundManager.oneTimeTimersRepresentation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(oneTimeTimer1));
        assertTrue(representation.contains(oneTimeTimer2));
        assertTrue(representation.contains(oneTimeTimer3));

        ((RoundManagerImpl)_roundManager).removeOneTimeTimer(oneTimeTimer1);

        representation = _roundManager.oneTimeTimersRepresentation();

        assertFalse(representation.contains(oneTimeTimer1));
    }

    @Test
    void testRecurringTimersRepresentation() {
        RecurringTimer recurringTimer1 = new RecurringTimerStub();
        RecurringTimer recurringTimer2 = new RecurringTimerStub();
        RecurringTimer recurringTimer3 = new RecurringTimerStub();

        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer1);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer3);

        ReadableCollection<RecurringTimer> representation =
                _roundManager.recurringTimersRepresentation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(recurringTimer1));
        assertTrue(representation.contains(recurringTimer2));
        assertTrue(representation.contains(recurringTimer3));

        ((RoundManagerImpl)_roundManager).removeRecurringTimer(recurringTimer1);

        representation = _roundManager.recurringTimersRepresentation();

        assertFalse(representation.contains(recurringTimer1));
    }

    @Test
    void testEndActiveCharacterTurnAdvancesRound() {
        final int roundNumber = 123;
        _roundManager.setRoundNumber(roundNumber);

        RecurringTimerStub recurringTimer1 = new RecurringTimerStub();
        recurringTimer1.setRoundModulo(2);
        recurringTimer1.setRoundOffset(0);
        recurringTimer1.setPriority(0);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer1);
        RecurringTimerStub recurringTimer2 = new RecurringTimerStub();
        recurringTimer2.setRoundModulo(4);
        recurringTimer2.setRoundOffset(0);
        recurringTimer2.setPriority(1);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer2);
        RecurringTimerStub recurringTimer3 = new RecurringTimerStub();
        recurringTimer3.setRoundModulo(4);
        recurringTimer3.setRoundOffset(1);
        recurringTimer3.setPriority(2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer3);
        RecurringTimerStub recurringTimer4 = new RecurringTimerStub();
        recurringTimer4.setRoundModulo(5);
        recurringTimer4.setRoundOffset(0);
        recurringTimer4.setPriority(3);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer4);

        OneTimeTimerStub oneTimeTimer1 = new OneTimeTimerStub();
        oneTimeTimer1.setRoundWhenGoesOff(roundNumber+1);
        oneTimeTimer1.setPriority(4);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer1);
        OneTimeTimerStub oneTimeTimer2 = new OneTimeTimerStub();
        oneTimeTimer2.setRoundWhenGoesOff(roundNumber+2);
        oneTimeTimer2.setPriority(5);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer2);

        _roundManager.endActiveCharacterTurn();

        assertEquals(roundNumber + 1, _roundManager.getRoundNumber());
        assertEquals(ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.size(),
                _roundManager.queueSize());
        for(int i = 0; i < ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.size(); i++) {
            Character character = ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(i).getItem1();
            assertEquals(i, _roundManager.getCharacterPositionInQueue(character));
            assertSame(ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(i).getItem2(),
                    _roundManager.characterRoundData(character));
        }

        assertEquals(3, ROUND_END_HANDLING.Timers.size());
        assertTrue(ROUND_END_HANDLING.Timers.contains(recurringTimer1));
        assertTrue(ROUND_END_HANDLING.Timers.contains(recurringTimer2));
        assertTrue(ROUND_END_HANDLING.Timers.contains(oneTimeTimer1));
        assertSame(oneTimeTimer1, ROUND_END_HANDLING.Timers.get(0));
        assertSame(recurringTimer2, ROUND_END_HANDLING.Timers.get(1));
        assertSame(recurringTimer1, ROUND_END_HANDLING.Timers.get(2));

        assertEquals(0, ROUND_END_HANDLING.TurnEnds.size());
        assertEquals(1, ROUND_END_HANDLING.TurnStarts.size());
        assertTrue(ROUND_END_HANDLING.TurnStarts.containsKey(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(0).getItem1()));
        assertEquals(0, ROUND_END_HANDLING.RoundEnds.size());
    }

    @Test
    void testEndOfRoundWhenActiveCharactersProviderProvidesNoCharacters() {
        ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.clear();

        _roundManager.endActiveCharacterTurn();

        assertNull(_roundManager.activeCharacter());
    }

    @Test
    void testAdvanceMultipleRoundsAtOnce() {
        final int roundNumber = 123;
        _roundManager.setRoundNumber(roundNumber);

        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();

        VariableCache roundData1 = new VariableCacheStub();
        VariableCache roundData2 = new VariableCacheStub();
        VariableCache roundData3 = new VariableCacheStub();

        _roundManager.setCharacterPositionInQueue(character1, 0, roundData1);
        _roundManager.setCharacterPositionInQueue(character2, 1, roundData2);
        _roundManager.setCharacterPositionInQueue(character3, 2, roundData3);

        // Should fire TWICE
        RecurringTimerStub recurringTimer1 = new RecurringTimerStub();
        recurringTimer1.setRoundModulo(2);
        recurringTimer1.setRoundOffset(0);
        recurringTimer1.setPriority(0);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer1);
        // Should fire
        RecurringTimerStub recurringTimer2 = new RecurringTimerStub();
        recurringTimer2.setRoundModulo(4);
        recurringTimer2.setRoundOffset(0);
        recurringTimer2.setPriority(1);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer2);
        // Should fire
        RecurringTimerStub recurringTimer3 = new RecurringTimerStub();
        recurringTimer3.setRoundModulo(4);
        recurringTimer3.setRoundOffset(1);
        recurringTimer3.setPriority(2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer3);
        // Should NOT fire
        RecurringTimerStub recurringTimer4 = new RecurringTimerStub();
        recurringTimer4.setRoundModulo(5);
        recurringTimer4.setRoundOffset(2);
        recurringTimer4.setPriority(3);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer4);

        // Should fire
        OneTimeTimerStub oneTimeTimer1 = new OneTimeTimerStub(
                ((RoundManagerImpl)_roundManager)::removeOneTimeTimer);
        oneTimeTimer1.setRoundWhenGoesOff(roundNumber+1);
        oneTimeTimer1.setPriority(4);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer1);
        // Should NOT fire
        OneTimeTimerStub oneTimeTimer2 = new OneTimeTimerStub(
                ((RoundManagerImpl)_roundManager)::removeOneTimeTimer);
        oneTimeTimer2.setRoundWhenGoesOff(roundNumber+4);
        oneTimeTimer2.setPriority(5);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer2);

        assertNull(_roundManager.activeCharacter());

        _roundManager.endActiveCharacterTurn();

        assertEquals(1, TURN_HANDLING.TurnStarts.size());
        assertSame(character1, TURN_HANDLING.TurnStarts.get(0).getItem1());
        assertEquals(1, TURN_HANDLING.TurnStarts.get(0).getItem2());

        assertEquals(3, _roundManager.queueSize());

        _roundManager.endActiveCharacterTurn();

        assertEquals(2, TURN_HANDLING.TurnStarts.size());
        assertSame(character1, TURN_HANDLING.TurnStarts.get(0).getItem1());
        assertEquals(1, TURN_HANDLING.TurnStarts.get(0).getItem2());
        assertSame(character2, TURN_HANDLING.TurnStarts.get(1).getItem1());
        assertEquals(1, TURN_HANDLING.TurnStarts.get(1).getItem2());

        assertEquals(1, TURN_HANDLING.TurnEnds.size());
        assertSame(character1, TURN_HANDLING.TurnEnds.get(0).getItem1());
        assertEquals(1, TURN_HANDLING.TurnEnds.get(0).getItem2());

        _roundManager.advanceRounds(3);

        assertEquals(roundNumber + 3, _roundManager.getRoundNumber());

        // These assertions test not only whether the correct number of Timers were sent to
        // RoundHandling, but also whether they were ordered properly by priority (i.e. desc)
        assertEquals(5, ROUND_END_HANDLING.Timers.size());
        assertTrue(ROUND_END_HANDLING.Timers.contains(recurringTimer1));
        assertTrue(ROUND_END_HANDLING.Timers.contains(recurringTimer2));
        assertTrue(ROUND_END_HANDLING.Timers.contains(recurringTimer3));
        assertTrue(ROUND_END_HANDLING.Timers.contains(oneTimeTimer1));
        assertSame(oneTimeTimer1, ROUND_END_HANDLING.Timers.get(0));
        assertSame(recurringTimer3, ROUND_END_HANDLING.Timers.get(1));
        assertSame(recurringTimer2, ROUND_END_HANDLING.Timers.get(2));
        assertSame(recurringTimer1, ROUND_END_HANDLING.Timers.get(3));
        assertSame(recurringTimer1, ROUND_END_HANDLING.Timers.get(4));

        assertEquals(2, TURN_HANDLING.TurnStarts.size());
        assertSame(character1, TURN_HANDLING.TurnStarts.get(0).getItem1());
        assertEquals(1, TURN_HANDLING.TurnStarts.get(0).getItem2());
        assertSame(character2, TURN_HANDLING.TurnStarts.get(1).getItem1());
        assertEquals(1, TURN_HANDLING.TurnStarts.get(1).getItem2());

        assertEquals(1, TURN_HANDLING.TurnEnds.size());
        assertSame(character1, TURN_HANDLING.TurnEnds.get(0).getItem1());
        assertEquals(1, TURN_HANDLING.TurnEnds.get(0).getItem2());

        assertEquals(6, ROUND_END_HANDLING.RoundEnds.size());
        assertEquals(1, ROUND_END_HANDLING.RoundEnds.get(character1));
        assertEquals(1, ROUND_END_HANDLING.RoundEnds.get(character2));
        assertEquals(1, ROUND_END_HANDLING.RoundEnds.get(character3));
        assertEquals(2, ROUND_END_HANDLING.RoundEnds.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(0).getItem1()));
        assertEquals(2, ROUND_END_HANDLING.RoundEnds.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(1).getItem1()));
        assertEquals(2, ROUND_END_HANDLING.RoundEnds.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(2).getItem1()));

        assertEquals(5, ROUND_END_HANDLING.TurnEnds.size());
        assertEquals(1, ROUND_END_HANDLING.TurnEnds.get(character2));
        assertEquals(1, ROUND_END_HANDLING.TurnEnds.get(character3));
        assertEquals(2, ROUND_END_HANDLING.TurnEnds.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(0).getItem1()));
        assertEquals(2, ROUND_END_HANDLING.TurnEnds.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(1).getItem1()));
        assertEquals(2, ROUND_END_HANDLING.TurnEnds.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(2).getItem1()));

        assertEquals(4, ROUND_END_HANDLING.TurnStarts.size());
        assertEquals(1, ROUND_END_HANDLING.TurnStarts.get(character3));
        assertEquals(3, ROUND_END_HANDLING.TurnStarts.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(0).getItem1()));
        assertEquals(2, ROUND_END_HANDLING.TurnStarts.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(1).getItem1()));
        assertEquals(2, ROUND_END_HANDLING.TurnStarts.get(
                ACTIVE_CHARACTERS_PROVIDER.ActiveCharacters.get(2).getItem1()));
    }

    @Test
    void testRecurringTimersFiredMultipleTimesWhenRoundsElapsedGreaterThanPeriod() {
        final int initialRoundNumber = 105;
        _roundManager.setRoundNumber(initialRoundNumber);
        final int roundsToAdvance = 37;

        RecurringTimer recurringTimer1 = new RecurringTimerStub();
        recurringTimer1.setRoundModulo(1);
        recurringTimer1.setRoundOffset(0);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer1);

        RecurringTimer recurringTimer2 = new RecurringTimerStub();
        recurringTimer2.setRoundModulo(10);
        recurringTimer2.setRoundOffset(2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer2);

        RecurringTimer recurringTimer3 = new RecurringTimerStub();
        recurringTimer3.setRoundModulo(10);
        recurringTimer3.setRoundOffset(3);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer3);

        _roundManager.advanceRounds(roundsToAdvance);

        assertEquals(roundsToAdvance, getCount(recurringTimer1, ROUND_END_HANDLING.Timers));
        assertEquals(4, getCount(recurringTimer2, ROUND_END_HANDLING.Timers));
        assertEquals(3, getCount(recurringTimer3, ROUND_END_HANDLING.Timers));
    }

    private <T> int getCount(T item, List<T> items) {
        int hits = 0;
        for (T t : items) {
            if (t == item) {
                hits++;
            }
        }
        return hits;
    }

    @Test
    void testAdvanceRoundsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _roundManager.advanceRounds(0));
    }

    @Test
    void testIterator() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();
        Character character3 = new CharacterStub();

        VariableCache roundData1 = new VariableCacheStub();
        VariableCache roundData2 = new VariableCacheStub();
        VariableCache roundData3 = new VariableCacheStub();

        _roundManager.setCharacterPositionInQueue(character1, 0, roundData1);
        _roundManager.setCharacterPositionInQueue(character2, 1, roundData2);
        _roundManager.setCharacterPositionInQueue(character3, 2, roundData3);

        ArrayList<ReadablePair<Character, VariableCache>> fromIterator = new ArrayList<>();

        _roundManager.forEach(fromIterator::add);

        assertEquals(3, fromIterator.size());
        assertSame(character1, fromIterator.get(0).getItem1());
        assertSame(roundData1, fromIterator.get(0).getItem2());
        assertSame(character2, fromIterator.get(1).getItem1());
        assertSame(roundData2, fromIterator.get(1).getItem2());
        assertSame(character3, fromIterator.get(2).getItem1());
        assertSame(roundData3, fromIterator.get(2).getItem2());
    }
}
