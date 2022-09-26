package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class RoundManagerImpl implements RoundManager {
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY;
    private final RoundBasedTimerManager ROUND_BASED_TIMER_MANAGER;
    private final ArrayList<Character> QUEUE = new ArrayList<>();
    private final HashMap<Character, VariableCache> CHARACTER_ROUND_DATA = new HashMap<>();

    private int _roundNumber;
    private Supplier<List<Pair<Character, VariableCache>>> _activeCharactersProvider;

    public RoundManagerImpl(VariableCacheFactory variableCacheFactory,
                            RoundBasedTimerManager roundBasedTimerManager) {
        VARIABLE_CACHE_FACTORY = Check.ifNull(variableCacheFactory, "variableCacheFactory");
        ROUND_BASED_TIMER_MANAGER = Check.ifNull(roundBasedTimerManager, "roundBasedTimerManager");
    }

    @Override
    public boolean characterIsInQueue(Character character) throws IllegalArgumentException {
        return QUEUE.contains(Check.ifNull(character, "character"));
    }

    @Override
    public Integer getCharacterPositionInQueue(Character character)
            throws IllegalArgumentException {
        Check.ifNull(character, "character");
        int index = QUEUE.indexOf(character);
        return index == -1 ? null : index;
    }

    @Override
    public int queueSize() {
        return QUEUE.size();
    }

    @Override
    public VariableCache characterRoundData(Character character) throws IllegalArgumentException {
        return CHARACTER_ROUND_DATA.get(Check.ifNull(character, "character"));
    }

    @Override
    public void setCharacterPositionInQueue(Character character, int position)
            throws IllegalArgumentException {
        Check.ifNull(character, "character");
        Check.ifNonNegative(position, "position");
        QUEUE.add(Math.min(QUEUE.size(), position), character);
        if (!CHARACTER_ROUND_DATA.containsKey(character)) {
            CHARACTER_ROUND_DATA.put(character, VARIABLE_CACHE_FACTORY.make());
        }
    }

    @Override
    public void setCharacterRoundData(Character character, VariableCache roundData)
            throws IllegalArgumentException {
        Check.ifNull(character, "character");
        Check.ifNull(roundData, "roundData");
        if (CHARACTER_ROUND_DATA.containsKey(character)) {
            CHARACTER_ROUND_DATA.put(character, roundData);
        }
    }

    @Override
    public boolean removeCharacterFromQueue(Character character) throws IllegalArgumentException {
        Check.ifNull(character, "character");
        CHARACTER_ROUND_DATA.remove(character);
        return QUEUE.remove(character);
    }

    @Override
    public void clearQueue() {
        QUEUE.clear();
        CHARACTER_ROUND_DATA.clear();
    }

    @Override
    public List<Pair<Character, VariableCache>> characterQueueRepresentation() {
        ArrayList<Pair<Character, VariableCache>> output = new ArrayList<>();

        QUEUE.forEach(character ->
                output.add(new Pair<>(character, CHARACTER_ROUND_DATA.get(character))));

        return output;
    }

    @Override
    public void endActiveCharacterTurn() {
        if (_activeCharactersProvider == null) {
            throw new IllegalStateException("RoundManagerImpl.endActiveCharacterTurn: " +
                    "active characters provider is undefined");
        }

        if (!QUEUE.isEmpty()) {
            CHARACTER_ROUND_DATA.remove(QUEUE.remove(0));
        }

        if (QUEUE.isEmpty()) {
            advanceRounds(1);
        }
    }

    @Override
    public Character activeCharacter() {
        return QUEUE.isEmpty() ? null : QUEUE.get(0);
    }

    @Override
    public int getRoundNumber() {
        return _roundNumber;
    }

    @Override
    public void setRoundNumber(int newRoundNumber) {
        _roundNumber = newRoundNumber;
    }

    @Override
    public void setActiveCharactersProvider(Supplier<List<Pair<Character, VariableCache>>>
                                                    activeCharactersProvider)
            throws IllegalArgumentException {
        _activeCharactersProvider =
                Check.ifNull(activeCharactersProvider, "activeCharactersProvider");
    }

    @Override
    public void advanceRounds(int numberOfRounds)
            throws IllegalArgumentException, IllegalStateException {
        Check.throwOnLteZero(numberOfRounds, "numberOfRounds");

        if (_activeCharactersProvider == null) {
            throw new IllegalStateException(
                    "RoundManagerImpl.advanceRounds: active characters provider is undefined");
        }

        QUEUE.clear();
        CHARACTER_ROUND_DATA.clear();

        _activeCharactersProvider.get().forEach(ac -> {
            QUEUE.add(ac.getItem1());
            CHARACTER_ROUND_DATA.put(ac.getItem1(), ac.getItem2());
        });

        int newRoundNumber = _roundNumber + numberOfRounds;

        ROUND_BASED_TIMER_MANAGER.fireTimersForRoundsElapsed(_roundNumber, newRoundNumber);

        _roundNumber = newRoundNumber;
    }

    @Override
    public String getInterfaceName() {
        return RoundManager.class.getCanonicalName();
    }
}
