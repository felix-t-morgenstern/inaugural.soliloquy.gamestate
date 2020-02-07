package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.RoundManagerImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
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
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoundManagerImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final PairFactory PAIR_FACTORY = new PairFactoryStub();
    private final VariableCacheFactoryStub VARIABLE_CACHE_FACTORY = new VariableCacheFactoryStub();
    private final ActiveCharactersProvider ACTIVE_CHARACTERS_PROVIDER =
            new ActiveCharactersProviderStub();
    private final Action<Character> ON_CHARACTER_TURN_START = new ActionStub<>();
    private final Action<Character> ON_CHARACTER_TURN_END = new ActionStub<>();
    private final Action<Void> ON_ROUND_START = new ActionStub<>();
    private final Action<Void> ON_ROUND_END = new ActionStub<>();

    public static final List<Object> ROUND_END_ACTIONS_FIRED = new LinkedList<>();

    private RoundManager _roundManager;

    @SuppressWarnings("rawtypes")
    @BeforeEach
    void setUp() {
        _roundManager = new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER, ON_CHARACTER_TURN_START::run,
                ON_CHARACTER_TURN_END::run, ON_ROUND_START::run, ON_ROUND_END::run);

        ((ActionStub) ON_ROUND_START)._roundManager = _roundManager;
        ((ActionStub) ON_ROUND_END)._roundManager = _roundManager;
    }

    @AfterEach
    void tearDown() {
        ActionStub.ACTIONS_FIRED.clear();
        ((ActiveCharactersProviderStub)ACTIVE_CHARACTERS_PROVIDER)
                ._numberOfTimesToProvideEmptyCollection = 0;

        ROUND_END_ACTIONS_FIRED.clear();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(null, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER,
                        ON_CHARACTER_TURN_START::run, ON_CHARACTER_TURN_END::run,
                        ON_ROUND_START::run, ON_ROUND_END::run));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, null,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER,
                        ON_CHARACTER_TURN_START::run, ON_CHARACTER_TURN_END::run,
                        ON_ROUND_START::run, ON_ROUND_END::run));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        null, ACTIVE_CHARACTERS_PROVIDER,
                        ON_CHARACTER_TURN_START::run, ON_CHARACTER_TURN_END::run,
                        ON_ROUND_START::run, ON_ROUND_END::run));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, null,
                        ON_CHARACTER_TURN_START::run, ON_CHARACTER_TURN_END::run,
                        ON_ROUND_START::run, ON_ROUND_END::run));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER,
                        null, ON_CHARACTER_TURN_END::run,
                        ON_ROUND_START::run, ON_ROUND_END::run));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER,
                        ON_CHARACTER_TURN_START::run, null,
                        ON_ROUND_START::run, ON_ROUND_END::run));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER,
                        ON_CHARACTER_TURN_START::run, ON_CHARACTER_TURN_END::run,
                        null, ON_ROUND_END::run));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerImpl(COLLECTION_FACTORY, PAIR_FACTORY,
                        VARIABLE_CACHE_FACTORY, ACTIVE_CHARACTERS_PROVIDER,
                        ON_CHARACTER_TURN_START::run, ON_CHARACTER_TURN_END::run,
                        ON_ROUND_START::run, null));
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

    @SuppressWarnings("rawtypes")
    @Test
    void testEndActiveCharacterTurnAndActiveCharacter() {
        Character character1 = new CharacterStub();
        Character character2 = new CharacterStub();

        _roundManager.setCharacterPositionInQueue(character1, Integer.MAX_VALUE);
        _roundManager.setCharacterPositionInQueue(character2, Integer.MAX_VALUE);

        _roundManager.endActiveCharacterTurn();

        assertSame(character1, _roundManager.activeCharacter());

        _roundManager.removeCharacterFromQueue(character1);

        ActionStub.ACTIONS_FIRED.clear();

        _roundManager.endActiveCharacterTurn();

        assertEquals(2, ActionStub.ACTIONS_FIRED.size());
        assertSame(ON_CHARACTER_TURN_END, ActionStub.ACTIONS_FIRED.get(0));
        assertSame(ON_CHARACTER_TURN_START, ActionStub.ACTIONS_FIRED.get(1));
        assertSame(character1, ((ActionStub)ON_CHARACTER_TURN_END)._mostRecentInput);
        assertSame(character2, ((ActionStub)ON_CHARACTER_TURN_START)._mostRecentInput);

        assertSame(character2, _roundManager.activeCharacter());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testEndActiveCharacterTurnWhenNoCharactersAreInQueue() {
        ((ActiveCharactersProviderStub)ACTIVE_CHARACTERS_PROVIDER)
                ._numberOfTimesToProvideEmptyCollection = 1;
        final int roundNumber = 123;
        _roundManager.setRoundNumber(roundNumber);

        _roundManager.endActiveCharacterTurn();

        assertNull(_roundManager.activeCharacter());
        assertEquals(roundNumber + 1, _roundManager.getRoundNumber());
        assertNull(((ActionStub)ON_CHARACTER_TURN_START)._mostRecentInput);
        assertFalse(ActionStub.ACTIONS_FIRED.contains(ON_CHARACTER_TURN_START));
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

    @SuppressWarnings("rawtypes")
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
        recurringTimer2.setPriority(2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer2);
        RecurringTimerStub recurringTimer3 = new RecurringTimerStub();
        recurringTimer3.setRoundModulo(4);
        recurringTimer3.setRoundOffset(1);
        recurringTimer3.setPriority(2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer3);
        RecurringTimerStub recurringTimer4 = new RecurringTimerStub();
        recurringTimer4.setRoundModulo(5);
        recurringTimer4.setRoundOffset(0);
        recurringTimer4.setPriority(2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer4);

        OneTimeTimerStub oneTimeTimer1 = new OneTimeTimerStub();
        oneTimeTimer1.setRoundWhenGoesOff(roundNumber+1);
        oneTimeTimer1.setPriority(1);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer1);
        OneTimeTimerStub oneTimeTimer2 = new OneTimeTimerStub();
        oneTimeTimer2.setRoundWhenGoesOff(roundNumber+2);
        oneTimeTimer2.setPriority(1);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer2);

        _roundManager.endActiveCharacterTurn();

        assertEquals(roundNumber + 1, _roundManager.getRoundNumber());
        assertEquals(ActiveCharactersProviderStub.ACTIVE_CHARACTERS.size(),
                _roundManager.queueSize());
        for(int i = 0; i < ActiveCharactersProviderStub.ACTIVE_CHARACTERS.size(); i++) {
            Character character = ActiveCharactersProviderStub.ACTIVE_CHARACTERS.get(i).getItem1();
            assertEquals(i, _roundManager.getCharacterPositionInQueue(character));
            assertSame(ActiveCharactersProviderStub.ACTIVE_CHARACTERS.get(i).getItem2(),
                    _roundManager.characterRoundData(character));
        }

        assertEquals(roundNumber, ((ActionStub) ON_ROUND_END)._roundNumberRan);
        assertEquals(roundNumber + 1, ((ActionStub) ON_ROUND_START)._roundNumberRan);

        assertTrue(recurringTimer1._fired);
        assertTrue(recurringTimer2._fired);
        assertFalse(recurringTimer3._fired);
        assertFalse(recurringTimer4._fired);
        assertTrue(oneTimeTimer1._fired);
        assertFalse(oneTimeTimer2._fired);

        assertEquals(7, ROUND_END_ACTIONS_FIRED.size());
        assertSame(ON_CHARACTER_TURN_END, ROUND_END_ACTIONS_FIRED.get(0));
        assertSame(ON_ROUND_END, ROUND_END_ACTIONS_FIRED.get(1));
        assertSame(recurringTimer1, ROUND_END_ACTIONS_FIRED.get(2));
        assertSame(oneTimeTimer1, ROUND_END_ACTIONS_FIRED.get(3));
        assertSame(recurringTimer2, ROUND_END_ACTIONS_FIRED.get(4));
        assertSame(ON_ROUND_START, ROUND_END_ACTIONS_FIRED.get(5));
        assertSame(ON_CHARACTER_TURN_START, ROUND_END_ACTIONS_FIRED.get(6));
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testAdvanceRounds() {
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
        recurringTimer2.setPriority(2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer2);
        RecurringTimerStub recurringTimer3 = new RecurringTimerStub();
        recurringTimer3.setRoundModulo(4);
        recurringTimer3.setRoundOffset(1);
        recurringTimer3.setPriority(0);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer3);
        RecurringTimerStub recurringTimer4 = new RecurringTimerStub();
        recurringTimer4.setRoundModulo(5);
        recurringTimer4.setRoundOffset(0);
        recurringTimer4.setPriority(2);
        ((RoundManagerImpl)_roundManager).addRecurringTimer(recurringTimer4);

        OneTimeTimerStub oneTimeTimer1 = new OneTimeTimerStub(
                ((RoundManagerImpl)_roundManager)::removeOneTimeTimer);
        oneTimeTimer1.setRoundWhenGoesOff(roundNumber+1);
        oneTimeTimer1.setPriority(1);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer1);
        OneTimeTimerStub oneTimeTimer2 = new OneTimeTimerStub(
                ((RoundManagerImpl)_roundManager)::removeOneTimeTimer);
        oneTimeTimer2.setRoundWhenGoesOff(roundNumber+2);
        oneTimeTimer2.setPriority(1);
        ((RoundManagerImpl)_roundManager).addOneTimeTimer(oneTimeTimer2);

        _roundManager.advanceRounds(2);

        assertEquals(roundNumber + 2, _roundManager.getRoundNumber());

        assertEquals(roundNumber + 1, ((ActionStub) ON_ROUND_END)._roundNumberRan);
        assertEquals(roundNumber + 2, ((ActionStub) ON_ROUND_START)._roundNumberRan);

        assertTrue(recurringTimer1._fired);
        assertTrue(recurringTimer2._fired);
        assertTrue(recurringTimer3._fired);
        assertTrue(recurringTimer4._fired);
        assertTrue(oneTimeTimer1._fired);
        assertTrue(oneTimeTimer2._fired);

        assertSame(ON_ROUND_END, ROUND_END_ACTIONS_FIRED.get(0));
        assertSame(recurringTimer1, ROUND_END_ACTIONS_FIRED.get(1));
        assertSame(oneTimeTimer1, ROUND_END_ACTIONS_FIRED.get(2));
        assertSame(recurringTimer2, ROUND_END_ACTIONS_FIRED.get(3));
        assertSame(ON_ROUND_START, ROUND_END_ACTIONS_FIRED.get(4));
        assertSame(ON_ROUND_END, ROUND_END_ACTIONS_FIRED.get(5));
        assertSame(recurringTimer3, ROUND_END_ACTIONS_FIRED.get(6));
        assertSame(oneTimeTimer2, ROUND_END_ACTIONS_FIRED.get(7));
        assertSame(recurringTimer4, ROUND_END_ACTIONS_FIRED.get(8));
        assertSame(ON_ROUND_START, ROUND_END_ACTIONS_FIRED.get(9));
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
