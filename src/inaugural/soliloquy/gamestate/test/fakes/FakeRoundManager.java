package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FakeRoundManager implements RoundManager {
    private final List<Character> QUEUE = new LinkedList<>();
    private final HashMap<Character, VariableCache> CHARACTERS_DATA = new HashMap<>();

    public final Collection<OneTimeTimer> OneTimeTimers = new FakeCollection<>();
    public final Collection<RecurringTimer> RecurringTimers = new FakeCollection<>();

    private int _roundNumber;

    @Override
    public ReadableCollection<ReadablePair<Character, VariableCache>> characterQueueRepresentation() {
        Collection<ReadablePair<Character, VariableCache>> collection = new FakeCollection<>();
        QUEUE.forEach(c -> {
            collection.add(new FakeReadablePair<>(c, CHARACTERS_DATA.get(c)));
        });
        return collection.representation();
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
    public void setCharacterPositionInQueue(Character character, int position,
                                            VariableCache roundData)
            throws IllegalArgumentException {
        QUEUE.add(Math.min(position,QUEUE.size()), character);
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
    public ReadableCollection<OneTimeTimer> oneTimeTimersRepresentation() {
        return OneTimeTimers;
    }

    @Override
    public ReadableCollection<RecurringTimer> recurringTimersRepresentation() {
        return RecurringTimers;
    }

    @Override
    public String getInterfaceName() {
        return null;
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
                return new FakePair<>(next, CHARACTERS_DATA.get(next)).representation();
            }
        };
    }
}
