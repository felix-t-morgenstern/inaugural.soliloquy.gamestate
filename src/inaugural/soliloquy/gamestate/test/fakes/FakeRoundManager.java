package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Supplier;

public class FakeRoundManager implements RoundManager {
    private final java.util.List<Character> QUEUE = new LinkedList<>();
    private final HashMap<Character, VariableCache> CHARACTERS_DATA = new HashMap<>();

    private int _roundNumber;

    @Override
    public List<Pair<Character, VariableCache>> characterQueueRepresentation() {
        List<Pair<Character, VariableCache>> collection = new FakeList<>();
        QUEUE.forEach(c -> collection.add(new FakePair<>(c, CHARACTERS_DATA.get(c))));
        return collection;
    }

    @Override
    public boolean characterIsInQueue(Character character) throws IllegalArgumentException {
        return false;
    }

    @Override
    public Integer getCharacterPositionInQueue(Character character) throws IllegalArgumentException {
        return null;
    }

    @Override
    public int queueSize() {
        return QUEUE.size();
    }

    @Override
    public VariableCache characterRoundData(Character character) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void setCharacterPositionInQueue(Character character, int position) throws IllegalArgumentException {
        QUEUE.add(Math.min(position,QUEUE.size()), character);
        VariableCache roundData;
        if (QUEUE.contains(character)) {
            roundData = CHARACTERS_DATA.get(character);
        } else {
            roundData = new VariableCacheStub();
        }
        CHARACTERS_DATA.put(character, roundData);
    }

    @Override
    public void setCharacterRoundData(Character character, VariableCache roundData) throws IllegalArgumentException {
        CHARACTERS_DATA.put(character, roundData);
    }

    @Override
    public boolean removeCharacterFromQueue(Character character) throws IllegalArgumentException {
        return false;
    }

    @Override
    public void clearQueue() {

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
        return _roundNumber;
    }

    @Override
    public void setRoundNumber(int i) throws IllegalArgumentException {
        _roundNumber = i;
    }

    @Override
    public void advanceRounds(int i) throws IllegalArgumentException {

    }

    @Override
    public void setActiveCharactersProvider(Supplier<java.util.List<Pair<Character, VariableCache>>> supplier) throws IllegalArgumentException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
