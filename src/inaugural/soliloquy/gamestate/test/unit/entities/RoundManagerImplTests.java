package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.RoundManagerImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.spydoubles.RoundEndHandlingSpyDouble;
import inaugural.soliloquy.gamestate.test.spydoubles.TurnHandlingSpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RoundManagerImplTests {
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final PairFactory PAIR_FACTORY = new FakePairFactory();
    private final FakeVariableCacheFactory VARIABLE_CACHE_FACTORY = new FakeVariableCacheFactory();
    private final FakeActiveCharactersProvider ACTIVE_CHARACTERS_PROVIDER =
            new FakeActiveCharactersProvider();
    private final TurnHandlingSpyDouble TURN_HANDLING = new TurnHandlingSpyDouble();
    private final RoundEndHandlingSpyDouble ROUND_END_HANDLING = new RoundEndHandlingSpyDouble();

    private RoundManager _roundManager;

    @BeforeEach
    void setUp() {
        _roundManager = new RoundManagerImpl(LIST_FACTORY, PAIR_FACTORY,
                VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                ROUND_END_HANDLING);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(null, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(LIST_FACTORY, null,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(LIST_FACTORY, PAIR_FACTORY,
                        null, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(LIST_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, null, TURN_HANDLING,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(LIST_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, null,
                        ROUND_END_HANDLING));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(LIST_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, TURN_HANDLING,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RoundManager.class.getCanonicalName(), _roundManager.getInterfaceName());
    }

    @Test
    void testSetAndGetNewCharacterPositionInQueue() {
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();

        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, 2);

        assertEquals(0, _roundManager.getCharacterPositionInQueue(character1));
        assertEquals(1, _roundManager.getCharacterPositionInQueue(character2));
        assertEquals(2, _roundManager.getCharacterPositionInQueue(character3));
    }

    @Test
    void testSetCharacterPositionWithRoundData() {
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();

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
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();
        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, 2);

        int queueSize = _roundManager.queueSize();

        assertEquals(3, queueSize);
    }

    @Test
    void testCharacterRoundData() {
        Character character = new FakeCharacter();

        _roundManager.setCharacterPositionInQueue(character, 0);

        assertNotNull(_roundManager.characterRoundData(character));
    }

    @Test
    void testCharacterRoundDataPairArchetypes() {
        Character character = new FakeCharacter();

        _roundManager.setCharacterPositionInQueue(character, 0);

        VariableCache data = _roundManager.characterRoundData(character);
        assertNotNull(data);
        assertEquals(VariableCache.class.getCanonicalName(), data.getInterfaceName());
    }

    @Test
    void testSetCharacterPositionDisplacesOtherCharacters() {
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();

        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, 1);

        assertEquals(0, _roundManager.getCharacterPositionInQueue(character1));
    }

    @Test
    void testPlaceCharacterAtBackOfQueue() {
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();

        _roundManager.setCharacterPositionInQueue(character1, 0);
        _roundManager.setCharacterPositionInQueue(character2, 1);
        _roundManager.setCharacterPositionInQueue(character3, Integer.MAX_VALUE);

        assertEquals(2, _roundManager.getCharacterPositionInQueue(character3));
    }

    @Test
    void testMoveCharacterToBackOfQueue() {
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();

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
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();

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
        Character character = new FakeCharacter();
        _roundManager.setCharacterPositionInQueue(character, 0);
        VariableCache initialData = VARIABLE_CACHE_FACTORY.Created.get(0);

        _roundManager.setCharacterPositionInQueue(character, 0);

        assertSame(initialData, VARIABLE_CACHE_FACTORY.Created.get(0));
    }

    @Test
    void testSetAndGetCharacterPositionWithInvalidParameters() {
        VariableCache roundData = new VariableCacheStub();

        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(null, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(new FakeCharacter(), -1));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.getCharacterPositionInQueue(null));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(null, 0, roundData));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(new FakeCharacter(), -1,
                        roundData));
        assertThrows(IllegalArgumentException.class,
                () -> _roundManager.setCharacterPositionInQueue(new FakeCharacter(), 0, null));
    }

    @Test
    void testCharacterRoundDataWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _roundManager.characterRoundData(null));
    }

    @Test
    void testCharacterIsInQueue() {
        Character character = new FakeCharacter();
        _roundManager.setCharacterPositionInQueue(character, 0);

        assertTrue(_roundManager.characterIsInQueue(character));
    }

    @Test
    void testCharacterIsInQueueWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _roundManager.characterIsInQueue(null));
    }

    @Test
    void testRemoveCharacterFromQueue() {
        Character character = new FakeCharacter();
        _roundManager.setCharacterPositionInQueue(character, 0);
        VariableCache initialData = VARIABLE_CACHE_FACTORY.Created.get(0);

        assertTrue(_roundManager.removeCharacterFromQueue(character));
        assertFalse(_roundManager.removeCharacterFromQueue(character));

        _roundManager.setCharacterPositionInQueue(character, 0);
        VariableCache dataAfterSecondAdd = VARIABLE_CACHE_FACTORY.Created.get(1);

        assertNotEquals(initialData, dataAfterSecondAdd);
    }

    @Test
    void testClearQueue() {
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();
        _roundManager.setCharacterPositionInQueue(character1, 0);
        VariableCache initialData1 = VARIABLE_CACHE_FACTORY.Created.get(0);
        _roundManager.setCharacterPositionInQueue(character2, 0);
        VariableCache initialData2 = VARIABLE_CACHE_FACTORY.Created.get(1);
        _roundManager.setCharacterPositionInQueue(character3, 0);
        VariableCache initialData3 = VARIABLE_CACHE_FACTORY.Created.get(2);

        _roundManager.clearQueue();

        assertEquals(0, _roundManager.queueSize());
        assertFalse(_roundManager.characterIsInQueue(character1));
        assertFalse(_roundManager.characterIsInQueue(character2));
        assertFalse(_roundManager.characterIsInQueue(character3));

        _roundManager.setCharacterPositionInQueue(character1, Integer.MAX_VALUE);
        VariableCache dataAfterSecondAdd1 = VARIABLE_CACHE_FACTORY.Created.get(3);
        _roundManager.setCharacterPositionInQueue(character2, Integer.MAX_VALUE);
        VariableCache dataAfterSecondAdd2 = VARIABLE_CACHE_FACTORY.Created.get(4);
        _roundManager.setCharacterPositionInQueue(character3, Integer.MAX_VALUE);
        VariableCache dataAfterSecondAdd3 = VARIABLE_CACHE_FACTORY.Created.get(5);

        assertNotEquals(initialData1, dataAfterSecondAdd1);
        assertNotEquals(initialData2, dataAfterSecondAdd2);
        assertNotEquals(initialData3, dataAfterSecondAdd3);
    }

    @Test
    void testCharacterQueueRepresentation() {
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();
        _roundManager.setCharacterPositionInQueue(character1, Integer.MAX_VALUE);
        VariableCache initialData1 = VARIABLE_CACHE_FACTORY.Created.get(0);
        _roundManager.setCharacterPositionInQueue(character2, Integer.MAX_VALUE);
        VariableCache initialData2 = VARIABLE_CACHE_FACTORY.Created.get(1);
        _roundManager.setCharacterPositionInQueue(character3, Integer.MAX_VALUE);
        VariableCache initialData3 = VARIABLE_CACHE_FACTORY.Created.get(2);

        List<Pair<Character,VariableCache>> representation =
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
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();

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
    void testOneTimeTurnBasedTimersRepresentation() {
        OneTimeTurnBasedTimer oneTimeTurnBasedTimer1 = new FakeOneTimeTurnBasedTimer();
        OneTimeTurnBasedTimer oneTimeTurnBasedTimer2 = new FakeOneTimeTurnBasedTimer();
        OneTimeTurnBasedTimer oneTimeTurnBasedTimer3 = new FakeOneTimeTurnBasedTimer();

        ((RoundManagerImpl)_roundManager).addOneTimeTurnBasedTimer(oneTimeTurnBasedTimer1);
        ((RoundManagerImpl)_roundManager).addOneTimeTurnBasedTimer(oneTimeTurnBasedTimer2);
        ((RoundManagerImpl)_roundManager).addOneTimeTurnBasedTimer(oneTimeTurnBasedTimer3);

        List<OneTimeTurnBasedTimer> representation =
                _roundManager.oneTimeTurnBasedTimersRepresentation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(oneTimeTurnBasedTimer1));
        assertTrue(representation.contains(oneTimeTurnBasedTimer2));
        assertTrue(representation.contains(oneTimeTurnBasedTimer3));

        ((RoundManagerImpl)_roundManager).removeOneTimeTurnBasedTimer(oneTimeTurnBasedTimer1);

        representation = _roundManager.oneTimeTurnBasedTimersRepresentation();

        assertFalse(representation.contains(oneTimeTurnBasedTimer1));
    }

    @Test
    void testRecurringTurnBasedTimersRepresentation() {
        RecurringTurnBasedTimer recurringTurnBasedTimer1 = new FakeRecurringTurnBasedTimer();
        RecurringTurnBasedTimer recurringTurnBasedTimer2 = new FakeRecurringTurnBasedTimer();
        RecurringTurnBasedTimer recurringTurnBasedTimer3 = new FakeRecurringTurnBasedTimer();

        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer1);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer2);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer3);

        List<RecurringTurnBasedTimer> representation =
                _roundManager.recurringTurnBasedTimersRepresentation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(recurringTurnBasedTimer1));
        assertTrue(representation.contains(recurringTurnBasedTimer2));
        assertTrue(representation.contains(recurringTurnBasedTimer3));

        ((RoundManagerImpl)_roundManager).removeRecurringTurnBasedTimer(recurringTurnBasedTimer1);

        representation = _roundManager.recurringTurnBasedTimersRepresentation();

        assertFalse(representation.contains(recurringTurnBasedTimer1));
    }

    @Test
    void testEndActiveCharacterTurnAdvancesRound() {
        final int roundNumber = 123;
        _roundManager.setRoundNumber(roundNumber);

        FakeRecurringTurnBasedTimer recurringTurnBasedTimer1 =
                new FakeRecurringTurnBasedTimer(null, null, 2, 0, 0);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer1);
        FakeRecurringTurnBasedTimer recurringTurnBasedTimer2 =
                new FakeRecurringTurnBasedTimer(null, null, 4, 0, 1);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer2);
        FakeRecurringTurnBasedTimer recurringTurnBasedTimer3 =
                new FakeRecurringTurnBasedTimer(null, null, 4, 1, 2);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer3);
        FakeRecurringTurnBasedTimer recurringTurnBasedTimer4 =
                new FakeRecurringTurnBasedTimer(null, null, 5, 0, 3);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer4);

        FakeOneTimeTurnBasedTimer oneTimeTurnBasedTimer1 =
                new FakeOneTimeTurnBasedTimer(null, null, roundNumber + 1, 4);
        ((RoundManagerImpl)_roundManager).addOneTimeTurnBasedTimer(oneTimeTurnBasedTimer1);
        FakeOneTimeTurnBasedTimer oneTimeTurnBasedTimer2 =
                new FakeOneTimeTurnBasedTimer(null, null, roundNumber + 2, 5);
        ((RoundManagerImpl)_roundManager).addOneTimeTurnBasedTimer(oneTimeTurnBasedTimer2);

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

        assertEquals(3, ROUND_END_HANDLING.TurnBasedTimers.size());
        assertTrue(ROUND_END_HANDLING.TurnBasedTimers.contains(recurringTurnBasedTimer1));
        assertTrue(ROUND_END_HANDLING.TurnBasedTimers.contains(recurringTurnBasedTimer2));
        assertTrue(ROUND_END_HANDLING.TurnBasedTimers.contains(oneTimeTurnBasedTimer1));
        assertSame(oneTimeTurnBasedTimer1, ROUND_END_HANDLING.TurnBasedTimers.get(0));
        assertSame(recurringTurnBasedTimer2, ROUND_END_HANDLING.TurnBasedTimers.get(1));
        assertSame(recurringTurnBasedTimer1, ROUND_END_HANDLING.TurnBasedTimers.get(2));

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

        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();

        VariableCache roundData1 = new VariableCacheStub();
        VariableCache roundData2 = new VariableCacheStub();
        VariableCache roundData3 = new VariableCacheStub();

        _roundManager.setCharacterPositionInQueue(character1, 0, roundData1);
        _roundManager.setCharacterPositionInQueue(character2, 1, roundData2);
        _roundManager.setCharacterPositionInQueue(character3, 2, roundData3);

        // Should fire TWICE
        FakeRecurringTurnBasedTimer recurringTurnBasedTimer1 =
                new FakeRecurringTurnBasedTimer(null, null, 2, 0, 0);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer1);
        // Should fire
        FakeRecurringTurnBasedTimer recurringTurnBasedTimer2 =
                new FakeRecurringTurnBasedTimer(null, null, 4, 0, 1);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer2);
        // Should fire
        FakeRecurringTurnBasedTimer recurringTurnBasedTimer3 =
                new FakeRecurringTurnBasedTimer(null, null, 4, 1, 2);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer3);
        // Should NOT fire
        FakeRecurringTurnBasedTimer recurringTurnBasedTimer4 =
                new FakeRecurringTurnBasedTimer(null, null, 5, 2, 3);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer4);

        // Should fire
        FakeOneTimeTurnBasedTimer oneTimeTurnBasedTimer1 =
                new FakeOneTimeTurnBasedTimer(null, null, roundNumber + 1, 4,
                        ((RoundManagerImpl)_roundManager)::removeOneTimeTurnBasedTimer);
        ((RoundManagerImpl)_roundManager).addOneTimeTurnBasedTimer(oneTimeTurnBasedTimer1);
        // Should NOT fire
        FakeOneTimeTurnBasedTimer oneTimeTurnBasedTimer2 =
                new FakeOneTimeTurnBasedTimer(null, null, roundNumber + 4, 5,
                        ((RoundManagerImpl)_roundManager)::removeOneTimeTurnBasedTimer);
        ((RoundManagerImpl)_roundManager).addOneTimeTurnBasedTimer(oneTimeTurnBasedTimer2);

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
        assertEquals(5, ROUND_END_HANDLING.TurnBasedTimers.size());
        assertTrue(ROUND_END_HANDLING.TurnBasedTimers.contains(recurringTurnBasedTimer1));
        assertTrue(ROUND_END_HANDLING.TurnBasedTimers.contains(recurringTurnBasedTimer2));
        assertTrue(ROUND_END_HANDLING.TurnBasedTimers.contains(recurringTurnBasedTimer3));
        assertTrue(ROUND_END_HANDLING.TurnBasedTimers.contains(oneTimeTurnBasedTimer1));
        assertSame(oneTimeTurnBasedTimer1, ROUND_END_HANDLING.TurnBasedTimers.get(0));
        assertSame(recurringTurnBasedTimer3, ROUND_END_HANDLING.TurnBasedTimers.get(1));
        assertSame(recurringTurnBasedTimer2, ROUND_END_HANDLING.TurnBasedTimers.get(2));
        assertSame(recurringTurnBasedTimer1, ROUND_END_HANDLING.TurnBasedTimers.get(3));
        assertSame(recurringTurnBasedTimer1, ROUND_END_HANDLING.TurnBasedTimers.get(4));

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

        RecurringTurnBasedTimer recurringTurnBasedTimer1 =
                new FakeRecurringTurnBasedTimer(null, null, 1, 0, 0);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer1);

        RecurringTurnBasedTimer recurringTurnBasedTimer2 =
                new FakeRecurringTurnBasedTimer(null, null, 10, 2, 0);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer2);

        RecurringTurnBasedTimer recurringTurnBasedTimer3 =
                new FakeRecurringTurnBasedTimer(null, null, 10, 3, 0);
        ((RoundManagerImpl)_roundManager).addRecurringTurnBasedTimer(recurringTurnBasedTimer3);

        _roundManager.advanceRounds(roundsToAdvance);

        assertEquals(roundsToAdvance, getCount(recurringTurnBasedTimer1, ROUND_END_HANDLING.TurnBasedTimers));
        assertEquals(4, getCount(recurringTurnBasedTimer2, ROUND_END_HANDLING.TurnBasedTimers));
        assertEquals(3, getCount(recurringTurnBasedTimer3, ROUND_END_HANDLING.TurnBasedTimers));
    }

    private <T> int getCount(T item, java.util.List<T> items) {
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
        Character character1 = new FakeCharacter();
        Character character2 = new FakeCharacter();
        Character character3 = new FakeCharacter();

        VariableCache roundData1 = new VariableCacheStub();
        VariableCache roundData2 = new VariableCacheStub();
        VariableCache roundData3 = new VariableCacheStub();

        _roundManager.setCharacterPositionInQueue(character1, 0, roundData1);
        _roundManager.setCharacterPositionInQueue(character2, 1, roundData2);
        _roundManager.setCharacterPositionInQueue(character3, 2, roundData3);

        ArrayList<Pair<Character, VariableCache>> fromIterator = new ArrayList<>();

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
