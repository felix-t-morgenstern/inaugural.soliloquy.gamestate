package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
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

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;

public class RoundManagerImpl implements RoundManager {
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY;
    private final RoundBasedTimerManager ROUND_BASED_TIMER_MANAGER;
    private final ActiveCharactersProvider ACTIVE_CHARACTERS_PROVIDER;
    private final Supplier<GameZone> GET_CURRENT_GAME_ZONE;
    private final TurnHandling TURN_HANDLING;
    private final RoundEndHandling ROUND_END_HANDLING;
    private final List<Character> QUEUE = listOf();
    private final Map<Character, VariableCache> CHARACTER_ROUND_DATA = mapOf();

    private int roundNumber;

    public RoundManagerImpl(VariableCacheFactory variableCacheFactory,
                            RoundBasedTimerManager roundBasedTimerManager,
                            ActiveCharactersProvider activeCharactersProvider,
                            Supplier<GameZone> getCurrentGameZone, TurnHandling turnHandling,
                            RoundEndHandling roundEndHandling) {
        VARIABLE_CACHE_FACTORY = Check.ifNull(variableCacheFactory, "variableCacheFactory");
        ROUND_BASED_TIMER_MANAGER = Check.ifNull(roundBasedTimerManager, "roundBasedTimerManager");
        ACTIVE_CHARACTERS_PROVIDER = Check.ifNull(activeCharactersProvider, "activeCharactersProvider");
        GET_CURRENT_GAME_ZONE = Check.ifNull(getCurrentGameZone, "getCurrentGameZone");
        TURN_HANDLING = Check.ifNull(turnHandling, "turnHandling");
        ROUND_END_HANDLING = Check.ifNull(roundEndHandling, "roundEndHandling");
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
        List<Pair<Character, VariableCache>> output = listOf();

        QUEUE.forEach(character -> output.add(pairOf(character, CHARACTER_ROUND_DATA.get(character))));

        return output;
    }

    @Override
    public void runActiveCharacterTurn() {
        if (!QUEUE.isEmpty()) {
            var character = QUEUE.remove(0);
            var turnData = CHARACTER_ROUND_DATA.remove(character);
            TURN_HANDLING.runTurn(character, turnData, false);
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
        return roundNumber;
    }

    @Override
    public void setRoundNumber(int newRoundNumber) {
        roundNumber = newRoundNumber;
    }

    @Override
    public void advanceRounds(int numberOfRounds)
            throws IllegalArgumentException, IllegalStateException {
        Check.throwOnLteZero(numberOfRounds, "numberOfRounds");

        QUEUE.clear();
        CHARACTER_ROUND_DATA.clear();

        ACTIVE_CHARACTERS_PROVIDER.generateInTurnOrder(GET_CURRENT_GAME_ZONE.get()).forEach(ac -> {
            QUEUE.add(ac.item1());
            CHARACTER_ROUND_DATA.put(ac.item1(), ac.item2());
        });

        var newRoundNumber = roundNumber + numberOfRounds;

        ROUND_BASED_TIMER_MANAGER.fireTimersForRoundsElapsed(roundNumber, newRoundNumber);

        roundNumber = newRoundNumber;
    }

    @Override
    public String getInterfaceName() {
        return RoundManager.class.getCanonicalName();
    }
}
