package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.entities.RoundManager;

public class RoundManagerStub implements RoundManager {
    public final Collection<OneTimeTimer> ONE_TIME_TIMERS = new CollectionStub<>();
    public final Collection<RecurringTimer> RECURRING_TIMERS = new CollectionStub<>();

    @Override
    public ReadableCollection<Pair<Character, VariableCache>> characterQueueRepresentation() {
        return null;
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
        return 0;
    }

    @Override
    public VariableCache characterRoundData(Character character) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void setCharacterPositionInQueue(Character character, int i) throws IllegalArgumentException {

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
        return 0;
    }

    @Override
    public void setRoundNumber(int i) throws IllegalArgumentException {

    }

    @Override
    public void advanceRounds(int i) throws IllegalArgumentException {

    }

    @Override
    public ReadableCollection<OneTimeTimer> oneTimeTimersRepresentation() {
        return ONE_TIME_TIMERS;
    }

    @Override
    public ReadableCollection<RecurringTimer> recurringTimersRepresentation() {
        return RECURRING_TIMERS;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
