package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.CharacterQueueEntryArchetype;
import inaugural.soliloquy.gamestate.archetypes.OneTimeTimerArchetype;
import inaugural.soliloquy.gamestate.archetypes.RecurringTimerArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Timer;
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;
import soliloquy.specs.ruleset.gameconcepts.RoundEndHandling;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;

import java.util.*;

public class RoundManagerImpl implements RoundManager {
    private final List<OneTimeTimer> ONE_TIME_TIMERS;
    private final List<RecurringTimer> RECURRING_TIMERS;
    private final ListFactory LIST_FACTORY;
    private final PairFactory PAIR_FACTORY;
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY;
    private final ActiveCharactersProvider ACTIVE_CHARACTERS_PROVIDER;
    private final TurnHandling TURN_HANDLING;
    private final RoundEndHandling ROUND_END_HANDLING;

    private final java.util.List<Character> QUEUE = new LinkedList<>();
    private final HashMap<Character, VariableCache> CHARACTERS_DATA = new HashMap<>();
    private final Pair<Character, VariableCache> QUEUE_ENTRY_ARCHETYPE =
            new CharacterQueueEntryArchetype();
    HashMap<Character, Integer> ROUND_END_EVENTS_TO_FIRE = new HashMap<>();

    private int _roundNumber;
    private Character _activeCharacter;

    @SuppressWarnings("ConstantConditions")
    public RoundManagerImpl(ListFactory listFactory,
                            PairFactory pairFactory,
                            VariableCacheFactory variableCacheFactory,
                            ActiveCharactersProvider activeCharactersProvider,
                            TurnHandling turnHandling,
                            RoundEndHandling roundEndHandling) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        PAIR_FACTORY = Check.ifNull(pairFactory, "pairFactory");
        VARIABLE_CACHE_FACTORY = Check.ifNull(variableCacheFactory, "variableCacheFactory");
        ONE_TIME_TIMERS = LIST_FACTORY.make(new OneTimeTimerArchetype());
        RECURRING_TIMERS = LIST_FACTORY.make(new RecurringTimerArchetype());
        ACTIVE_CHARACTERS_PROVIDER = Check.ifNull(activeCharactersProvider,
                "activeCharactersProvider");
        TURN_HANDLING = Check.ifNull(turnHandling, "turnHandling");
        ROUND_END_HANDLING = Check.ifNull(roundEndHandling, "roundEndHandling");
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
    public List<Pair<Character, VariableCache>> characterQueueRepresentation() {
        List<Pair<Character,VariableCache>> data = LIST_FACTORY.make(QUEUE_ENTRY_ARCHETYPE);
        for (Character character : QUEUE) {
            data.add(PAIR_FACTORY.make(character, CHARACTERS_DATA.get(character)));
        }
        return data;
    }

    @Override
    public void endActiveCharacterTurn() {
        removeCharacterFromQueue(_activeCharacter);
        if (!QUEUE.isEmpty()) {
            if (_activeCharacter != null) {
                TURN_HANDLING.onTurnEnd(_activeCharacter, 1);
                increaseEventsFired(ROUND_END_EVENTS_TO_FIRE, _activeCharacter, 1);
            }
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

        if (_activeCharacter != null) {
            turnEndsToFire.put(_activeCharacter, 1);
            ROUND_END_EVENTS_TO_FIRE.put(_activeCharacter, 1);

            removeCharacterFromQueue(_activeCharacter);
        }

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
        if (!QUEUE.isEmpty()) {
            _activeCharacter = QUEUE.get(0);
            increaseEventsFired(turnStartsToFire, _activeCharacter, 1);
        }
        else {
            _activeCharacter = null;
        }

        // TODO: testTimersFiredAfterActiveCharacterReassignedAndBeforeEndOfRoundEvents; verify ordering of events at round end
        int prevRoundNumber = _roundNumber;
        _roundNumber += numberOfRounds;

        // TODO: testTimersSentToRoundEndHandlingInOrderOfPriority
        ROUND_END_HANDLING.runEndOfRoundEvents(turnStartsToFire, turnEndsToFire,
                ROUND_END_EVENTS_TO_FIRE, getTimersToFire(prevRoundNumber),
                previousActiveCharacter, _activeCharacter);

        ROUND_END_EVENTS_TO_FIRE.clear();
    }

    private java.util.List<Timer> getTimersToFire(int prevRoundNumber) {
        TreeMap<Integer, java.util.List<Timer>> timersToFire = new TreeMap<>();
        for(OneTimeTimer oneTimeTimer : ONE_TIME_TIMERS) {
            if (_roundNumber >= oneTimeTimer.getRoundWhenGoesOff()) {
                addToTimersToFire(timersToFire, oneTimeTimer, 1);
            }
        }
        for(RecurringTimer recurringTimer : RECURRING_TIMERS) {
            int numberOfTimesToAdd = numberOfTimesToFireRecurringTimer(recurringTimer,
                    prevRoundNumber);
            if (numberOfTimesToAdd > 0) {
                addToTimersToFire(timersToFire, recurringTimer, numberOfTimesToAdd);
            }
        }
        java.util.List<Timer> results = new ArrayList<>();
        timersToFire.keySet().forEach(i -> timersToFire.get(i).forEach(t -> results.add(0, t)));
        return results;
    }

    private static void addToTimersToFire(TreeMap<Integer, java.util.List<Timer>> timersToFire,
                                          Timer timer, int numberOfTimesToAdd) {
        if (!timersToFire.containsKey(timer.getPriority())) {
            timersToFire.put(timer.getPriority(), new ArrayList<>());
        }
        timersToFire.get(timer.getPriority()).addAll(
                Collections.nCopies(numberOfTimesToAdd, timer));
    }

    private int numberOfTimesToFireRecurringTimer(RecurringTimer recurringTimer,
                                                  int prevRoundNumber) {
        int roundsElapsed = _roundNumber - prevRoundNumber;
        int modulo = recurringTimer.getRoundModulo();
        if (modulo == 1) {
            return roundsElapsed;
        }
        int offset = recurringTimer.getRoundOffset();

        // Calculate number of whole periods
        int numberOfPeriods = roundsElapsed / modulo;
        int numberOfTimesToAdd = numberOfPeriods;
        // Calculate whether any partial periods add one more
        int prevRoundNumberLessOffset = prevRoundNumber - offset;
        int moduloPrevRoundNumberLessOffset = prevRoundNumberLessOffset % modulo;
        if (moduloPrevRoundNumberLessOffset + roundsElapsed >=
                modulo * (numberOfPeriods + 1)) {
            numberOfTimesToAdd++;
        }
        return numberOfTimesToAdd;
    }

    @Override
    public List<OneTimeTimer> oneTimeTimersRepresentation() {
        return ONE_TIME_TIMERS.makeClone();
    }

    @Override
    public List<RecurringTimer> recurringTimersRepresentation() {
        return RECURRING_TIMERS.makeClone();
    }

    @Override
    public String getInterfaceName() {
        return RoundManager.class.getCanonicalName();
    }

    @Override
    public Iterator<Pair<Character, VariableCache>> iterator() {
        return new Iterator<>() {
            private final Iterator<Character> iterator = QUEUE.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Pair<Character, VariableCache> next() {
                Character next = iterator.next();
                return PAIR_FACTORY.make(next, CHARACTERS_DATA.get(next)).makeClone();
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
