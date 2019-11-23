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

import java.util.*;
import java.util.function.Consumer;

public class RoundManagerImpl implements RoundManager {
    private final Collection<OneTimeTimer> ONE_TIME_TIMERS;
    private final Collection<RecurringTimer> RECURRING_TIMERS;
    private final CollectionFactory COLLECTION_FACTORY;
    private final PairFactory PAIR_FACTORY;
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY;
    private final ActiveCharactersProvider ACTIVE_CHARACTERS_PROVIDER;
    private final Consumer<Character> ON_CHARACTER_TURN_START;
    private final Consumer<Character> ON_CHARACTER_TURN_END;
    private final Consumer<Void> ON_ROUND_START;
    private final Consumer<Void> ON_ROUND_END;

    private final List<Character> QUEUE = new LinkedList<>();
    private final HashMap<Character, VariableCache> CHARACTERS_DATA = new HashMap<>();
    private final Pair<Character, VariableCache> QUEUE_ENTRY_ARCHETYPE =
            new CharacterQueueEntryArchetype();

    private int _roundNumber;
    private Character _activeCharacter;

    @SuppressWarnings("ConstantConditions")
    public RoundManagerImpl(CollectionFactory collectionFactory,
                            PairFactory pairFactory,
                            VariableCacheFactory variableCacheFactory,
                            ActiveCharactersProvider activeCharactersProvider,
                            Consumer<Character> onCharacterTurnStart,
                            Consumer<Character> onCharacterTurnEnd,
                            Consumer<Void> onRoundStart,
                            Consumer<Void> onRoundEnd) {
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
        if (onCharacterTurnStart == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl: onCharacterTurnStart cannot be null");
        }
        ON_CHARACTER_TURN_START = onCharacterTurnStart;
        if (onCharacterTurnEnd == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl: onCharacterTurnEnd cannot be null");
        }
        ON_CHARACTER_TURN_END = onCharacterTurnEnd;
        if (onRoundStart == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl: onRoundStart cannot be null");
        }
        ON_ROUND_START = onRoundStart;
        if (onRoundEnd == null) {
            throw new IllegalArgumentException(
                    "RoundManagerImpl: onRoundEnd cannot be null");
        }
        ON_ROUND_END = onRoundEnd;
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
        QUEUE.remove(character);
        QUEUE.add(Math.min(position,QUEUE.size()), character);
        if (!CHARACTERS_DATA.containsKey(character)) {
            CHARACTERS_DATA.put(character, VARIABLE_CACHE_FACTORY.make());
        }
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
    public ReadableCollection<Pair<Character, VariableCache>> characterQueueRepresentation() {
        Collection<Pair<Character,VariableCache>> data =
                COLLECTION_FACTORY.make(QUEUE_ENTRY_ARCHETYPE);
        for (Character character : QUEUE) {
            data.add(PAIR_FACTORY.make(character, CHARACTERS_DATA.get(character)));
        }
        return data.readOnlyRepresentation();
    }

    @Override
    public void endActiveCharacterTurn() {
        ON_CHARACTER_TURN_END.accept(_activeCharacter);
        if (QUEUE.isEmpty()) {
            advanceRounds(1);
        }
        _activeCharacter = QUEUE.isEmpty() ? null : QUEUE.get(0);
        if (_activeCharacter != null) {
            ON_CHARACTER_TURN_START.accept(_activeCharacter);
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
            return;
        }

        ON_ROUND_END.accept(null);
        _roundNumber++;
        ReadableCollection<Pair<Character,VariableCache>> activeCharacters =
                ACTIVE_CHARACTERS_PROVIDER.activeCharacters();
        for(Pair<Character,VariableCache> characterWithData : activeCharacters) {
            QUEUE.add(characterWithData.getItem1());
            CHARACTERS_DATA.put(characterWithData.getItem1(), characterWithData.getItem2());
        }
        fireTimers();
        ON_ROUND_START.accept(null);

        advanceRounds(--numberOfRounds);
    }

    private void fireTimers() {
        HashMap<Integer, List<Timer>> timersToFire = new HashMap<>();
        for(OneTimeTimer oneTimeTimer : ONE_TIME_TIMERS) {
            if (_roundNumber >= oneTimeTimer.getRoundWhenGoesOff()) {
                if (!timersToFire.containsKey(oneTimeTimer.getPriority())) {
                    timersToFire.put(oneTimeTimer.getPriority(), new ArrayList<>());
                }
                timersToFire.get(oneTimeTimer.getPriority()).add(oneTimeTimer);
            }
        }
        for(RecurringTimer recurringTimers : RECURRING_TIMERS) {
            if ((_roundNumber % recurringTimers.getRoundModulo()) -
                    recurringTimers.getRoundOffset() == 0) {
                if (!timersToFire.containsKey(recurringTimers.getPriority())) {
                    timersToFire.put(recurringTimers.getPriority(), new ArrayList<>());
                }
                timersToFire.get(recurringTimers.getPriority()).add(recurringTimers);
            }
        }
        List<Integer> priorities = new ArrayList<>(timersToFire.keySet());
        Collections.sort(priorities);
        priorities.forEach(i -> timersToFire.get(i).forEach(Timer::fire));
    }

    @Override
    public ReadableCollection<OneTimeTimer> oneTimeTimersRepresentation() {
        return ONE_TIME_TIMERS.readOnlyRepresentation();
    }

    @Override
    public ReadableCollection<RecurringTimer> recurringTimersRepresentation() {
        return RECURRING_TIMERS.readOnlyRepresentation();
    }

    @Override
    public String getInterfaceName() {
        return RoundManager.class.getCanonicalName();
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void addOneTimeTimer(OneTimeTimer oneTimeTimer) {
        ONE_TIME_TIMERS.add(oneTimeTimer);
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void removeOneTimeTimer(OneTimeTimer oneTimeTimer) {
        ONE_TIME_TIMERS.removeItem(oneTimeTimer);
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void addRecurringTimer(RecurringTimer recurringTimer) {
        RECURRING_TIMERS.add(recurringTimer);
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void removeRecurringTimer(RecurringTimer recurringTimer) {
        RECURRING_TIMERS.removeItem(recurringTimer);
    }
}
