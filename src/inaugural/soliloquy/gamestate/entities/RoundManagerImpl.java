package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class RoundManagerImpl implements RoundManager {
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY;
    private final ArrayList<Character> QUEUE = new ArrayList<>();
    private final HashMap<Character, VariableCache> CHARACTER_ROUND_DATA = new HashMap<>();

    public RoundManagerImpl(VariableCacheFactory variableCacheFactory) {
        VARIABLE_CACHE_FACTORY = variableCacheFactory;
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
        CHARACTER_ROUND_DATA.put(character, VARIABLE_CACHE_FACTORY.make());
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
        return null;
    }

    @Override
    public void endActiveCharacterTurn() {

    }

    @Override
    public Character activeCharacter() {
        return null;
    }

    @Override
    public int getRoundNumber() {
        return 0;
    }

    @Override
    public void setRoundNumber(int i) {

    }

    @Override
    public void setActiveCharactersProvider(Supplier<List<Pair<Character, VariableCache>>>
                                                        activeCharactersProvider)
            throws IllegalArgumentException {

    }

    @Override
    public void advanceRounds(int i) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public String getInterfaceName() {
        return RoundManager.class.getCanonicalName();
    }
}
