package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterQueueEntryArchetype;
import inaugural.soliloquy.gamestate.archetypes.OneTimeTimerArchetype;
import inaugural.soliloquy.gamestate.archetypes.RecurringTimerArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Timer;
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;
import soliloquy.specs.ruleset.gameconcepts.RoundEndHandling;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;

import java.util.*;

public class RoundManagerImpl implements RoundManager {
    private final Collection<OneTimeTimer> ONE_TIME_TIMERS;
    private final Collection<RecurringTimer> RECURRING_TIMERS;
    private final CollectionFactory COLLECTION_FACTORY;
    private final PairFactory PAIR_FACTORY;
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY;
    private final ActiveCharactersProvider ACTIVE_CHARACTERS_PROVIDER;
    private final TurnHandling TURN_HANDLING;
    private final RoundEndHandling ROUND_END_HANDLING;

    private final List<Character> QUEUE = new LinkedList<>();
    private final HashMap<Character, VariableCache> CHARACTERS_DATA = new HashMap<>();
    private final Pair<Character, VariableCache> QUEUE_ENTRY_ARCHETYPE =
            new CharacterQueueEntryArchetype();
    HashMap<Character, Integer> ROUND_END_EVENTS_TO_FIRE = new HashMap<>();

    private int _roundNumber;
    private Character _activeCharacter;

    @SuppressWarnings("ConstantConditions")
    public RoundManagerImpl(CollectionFactory collectionFactory,
                            PairFactory pairFactory,
                            VariableCacheFactory variableCacheFactory,
                            ActiveCharactersProvider activeCharactersProvider,
                            TurnHandling turnHandling,
                            RoundEndHandling roundEndHandling) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (pairFactory == null) {
            throw new IllegalArgumentException("RoundManagerImpl: pairFactory cannot be null");
        }
        PAIR_FACTORY = pairFactory;
        if (variableCacheFactory == null) {
            throw new IllegalArgumentException("RoundManagerImpl: variableCacheFactory cannot be null");
        }
        VARIABLE_CACHE_FACTORY = variableCacheFactory;
        ONE_TIME_TIMERS = COLLECTION_FACTORY.make(new OneTimeTimerArchetype());
        RECURRING_TIMERS = COLLECTION_FACTORY.make(new RecurringTimerArchetype());
        if (activeCharactersProvider == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl: activeCharactersProvider cannot be null");
        }
        ACTIVE_CHARACTERS_PROVIDER = activeCharactersProvider;
        TURN_HANDLING = turnHandling;
        ROUND_END_HANDLING = roundEndHandling;
    }

    @Override
    public boolean characterIsInQueue(Character character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.characterIsInQueue: character cannot be null");
        }
        return QUEUE.contains(character);
    }

    @Override
    public Integer getCharacterPositionInQueue(Character character)
            throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.getCharacterPositionInQueue: character cannot be null");
        }
        int position = QUEUE.indexOf(character);
        return position >= 0 ? position : null;
    }

    @Override
    public int queueSize() {
        return QUEUE.size();
    }

    @Override
    public VariableCache characterRoundData(Character character) throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.characterRoundData: character cannot be null");
        }
        return CHARACTERS_DATA.get(character);
    }

    @Override
    public void setCharacterPositionInQueue(Character character, int position)
            throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.setCharacterPositionInQueue: character cannot be null");
        }
        if (position < 0) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.setCharacterPositionInQueue: position cannot be negative");
        }
        VariableCache roundData;
        if (CHARACTERS_DATA.containsKey(character)) {
            roundData = CHARACTERS_DATA.get(character);
        } else {
            roundData = VARIABLE_CACHE_FACTORY.make();
        }
        setCharacterPositionInQueue(character, position, roundData);
    }

    @Override
    public void setCharacterPositionInQueue(Character character, int position,
                                            VariableCache roundData)
            throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.setCharacterPositionInQueue: character cannot be null");
        }
        if (position < 0) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.setCharacterPositionInQueue: position cannot be negative");
        }
        if (roundData == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.setCharacterPositionInQueue: roundData cannot be null");
        }
        QUEUE.remove(character);
        QUEUE.add(Math.min(position,QUEUE.size()), character);
        CHARACTERS_DATA.put(character, roundData);
    }

    @Override
    public boolean removeCharacterFromQueue(Character character) throws IllegalArgumentException {
        if (QUEUE.contains(character)) {
            QUEUE.remove(character);
            CHARACTERS_DATA.remove(character);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clearQueue() {
        QUEUE.clear();
        CHARACTERS_DATA.clear();
    }

    @Override
    public ReadableCollection<ReadablePair<Character, VariableCache>>
        characterQueueRepresentation() {
        Collection<ReadablePair<Character,VariableCache>> data =
                COLLECTION_FACTORY.make(QUEUE_ENTRY_ARCHETYPE);
        for (Character character : QUEUE) {
            data.add(PAIR_FACTORY.make(character,
                    CHARACTERS_DATA.get(character)).representation());
        }
        return data.representation();
    }

    @Override
    public void endActiveCharacterTurn() {
        removeCharacterFromQueue(_activeCharacter);
        if (!QUEUE.isEmpty()) {
            if (_activeCharacter != null) {
                TURN_HANDLING.onTurnEnd(_activeCharacter, 1);
            }
            increaseEventsFired(ROUND_END_EVENTS_TO_FIRE, _activeCharacter, 1);
            _activeCharacter = QUEUE.get(0);
            if (_activeCharacter != null) {
                TURN_HANDLING.onTurnStart(_activeCharacter, 1);
            }
        }
        else {
            advanceRounds(1);
        }
    }

    private void increaseEventsFired(HashMap<Character, Integer> eventsFired, Character character,
                                     int numberOfEvents) {
        if (eventsFired.containsKey(character)) {
            eventsFired.put(character, eventsFired.get(character) + numberOfEvents);
        }
        else {
            eventsFired.put(character, numberOfEvents);
        }
    }

    @Override
    public Character activeCharacter() {
        return _activeCharacter;
    }

    @Override
    public int getRoundNumber() {
        return _roundNumber;
    }

    @Override
    public void setRoundNumber(int roundNumber) throws IllegalArgumentException {
        _roundNumber = roundNumber;
    }

    @Override
    public void advanceRounds(int numberOfRounds) throws IllegalArgumentException {
        if (numberOfRounds < 1) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl.advanceRounds: numberOfRounds cannot be less than 1");
        }

        HashMap<Character, Integer> turnStartsToFire = new HashMap<>();
        HashMap<Character, Integer> turnEndsToFire = new HashMap<>();

        turnEndsToFire.put(_activeCharacter, 1);
        ROUND_END_EVENTS_TO_FIRE.put(_activeCharacter, 1);

        removeCharacterFromQueue(_activeCharacter);

        if (!QUEUE.isEmpty()) {
            QUEUE.forEach(c -> {
                turnStartsToFire.put(c, 1);
                turnEndsToFire.put(c, 1);
                increaseEventsFired(ROUND_END_EVENTS_TO_FIRE, c, 1);
            });
        }

        Character previousActiveCharacter = _activeCharacter;

        QUEUE.clear();
        CHARACTERS_DATA.clear();

        boolean moreThanOneRoundElapsed = numberOfRounds > 1;
        ACTIVE_CHARACTERS_PROVIDER.activeCharacters().forEach(c -> {
            if (moreThanOneRoundElapsed) {
                increaseEventsFired(turnStartsToFire, c.getItem1(), numberOfRounds - 1);
                increaseEventsFired(turnEndsToFire, c.getItem1(), numberOfRounds - 1);
                increaseEventsFired(ROUND_END_EVENTS_TO_FIRE, c.getItem1(), numberOfRounds - 1);
            }
            setCharacterPositionInQueue(c.getItem1(), Integer.MAX_VALUE, c.getItem2());
        });

        // TODO: testEndOfRoundWhenActiveCharactersProviderProvidesNoCharacters; verify whether _activeCharacter is indeed set to null
        _activeCharacter = QUEUE.get(0);
        increaseEventsFired(turnStartsToFire, _activeCharacter, 1);

        // TODO: testTimersFiredAfterActiveCharacterReassignedAndBeforeEndOfRoundEvents; verify ordering of events at round end
        int prevRoundNumber = _roundNumber;
        _roundNumber += numberOfRounds;

        // TODO: testTimersSentToRoundEndHandlingInOrderOfPriority
        ROUND_END_HANDLING.runEndOfRoundEvents(turnStartsToFire, turnEndsToFire,
                ROUND_END_EVENTS_TO_FIRE, getTimersToFire(prevRoundNumber),
                previousActiveCharacter, _activeCharacter);

        ROUND_END_EVENTS_TO_FIRE.clear();
    }

    private List<Timer> getTimersToFire(int prevRoundNumber) {
        TreeMap<Integer, List<Timer>> timersToFire = new TreeMap<>();
        for(OneTimeTimer oneTimeTimer : ONE_TIME_TIMERS) {
            if (_roundNumber >= oneTimeTimer.getRoundWhenGoesOff()) {
                addToTimersToFire(timersToFire, oneTimeTimer, 1);
            }
        }
        for(RecurringTimer recurringTimer : RECURRING_TIMERS) {
            int modulo = recurringTimer.getRoundModulo();
            int offset = recurringTimer.getRoundOffset();

            // TODO: Consider breaking this into multiple functions
            // Calculate number of whole periods
            int numberOfTimesToAdd = 0;
            int numberOfPeriods = (_roundNumber - prevRoundNumber) / modulo;
            numberOfTimesToAdd += numberOfPeriods;
            // TODO: Consider more robust testing of this logic
            // Calculate whether any partial periods add one more
            int roundsElapsed = _roundNumber - prevRoundNumber;
            int prevRoundNumberLessOffset = prevRoundNumber - offset;
            int moduloPrevRoundNumberLessOffset = prevRoundNumberLessOffset % modulo;
            if (moduloPrevRoundNumberLessOffset + roundsElapsed >= modulo) {
                numberOfTimesToAdd++;
            }
            if (numberOfTimesToAdd > 0) {
                addToTimersToFire(timersToFire, recurringTimer, numberOfTimesToAdd);
            }
        }
        List<Timer> results = new ArrayList<>();
        timersToFire.keySet().forEach(i -> {
            timersToFire.get(i).forEach(t -> results.add(0, t));
        });
        return results;
    }

    private static void addToTimersToFire(TreeMap<Integer, List<Timer>> timersToFire,
                                          Timer timer, int numberOfTimesToAdd) {
        if (!timersToFire.containsKey(timer.getPriority())) {
            timersToFire.put(timer.getPriority(), new ArrayList<>());
        }
        timersToFire.get(timer.getPriority()).addAll(
                Collections.nCopies(numberOfTimesToAdd, timer));
    }

    @Override
    public ReadableCollection<OneTimeTimer> oneTimeTimersRepresentation() {
        return ONE_TIME_TIMERS.representation();
    }

    @Override
    public ReadableCollection<RecurringTimer> recurringTimersRepresentation() {
        return RECURRING_TIMERS.representation();
    }

    @Override
    public String getInterfaceName() {
        return RoundManager.class.getCanonicalName();
    }

    @Override
    public Iterator<ReadablePair<Character, VariableCache>> iterator() {
        return new Iterator<ReadablePair<Character, VariableCache>>() {
            private final Iterator<Character> iterator = QUEUE.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public ReadablePair<Character, VariableCache> next() {
                Character next = iterator.next();
                return PAIR_FACTORY.make(next, CHARACTERS_DATA.get(next)).representation();
            }
        };
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void addOneTimeTimer(OneTimeTimer oneTimeTimer) {
        ONE_TIME_TIMERS.add(oneTimeTimer);
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void removeOneTimeTimer(OneTimeTimer oneTimeTimer) {
        ONE_TIME_TIMERS.remove(oneTimeTimer);
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void addRecurringTimer(RecurringTimer recurringTimer) {
        RECURRING_TIMERS.add(recurringTimer);
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void removeRecurringTimer(RecurringTimer recurringTimer) {
        RECURRING_TIMERS.remove(recurringTimer);
    }
}
