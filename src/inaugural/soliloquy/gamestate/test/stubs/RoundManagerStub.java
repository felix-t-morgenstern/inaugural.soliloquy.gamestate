package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadOnlyCollection;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.entities.RoundManager;

public class RoundManagerStub implements RoundManager {
    public final Collection<OneTimeTimer> ONE_TIME_TIMERS = new CollectionStub<>();
    public final Collection<RecurringTimer> RECURRING_TIMERS = new CollectionStub<>();

    @Override
    public Map<Integer, Pair<Character, Integer>> characterOrder() {
        return null;
    }

    @Override
    public Pair<Character, Integer> endActiveCharacterTurn() {
        return null;
    }

    @Override
    public String activeCharacterId() {
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
    public ReadOnlyCollection<OneTimeTimer> oneTimeTimersRepresentation() {
        return ONE_TIME_TIMERS;
    }

    @Override
    public ReadOnlyCollection<RecurringTimer> recurringTimersRepresentation() {
        return RECURRING_TIMERS;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
