package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyCollection;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.gamestate.entities.IRoundManager;

public class RoundManagerStub implements IRoundManager {
    public final ICollection<IOneTimeTimer> ONE_TIME_TIMERS = new CollectionStub<>();
    public final ICollection<IRecurringTimer> RECURRING_TIMERS = new CollectionStub<>();

    @Override
    public IMap<Integer, IPair<ICharacter, Integer>> characterOrder() {
        return null;
    }

    @Override
    public IPair<ICharacter, Integer> endActiveCharacterTurn() {
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
    public IReadOnlyCollection<IOneTimeTimer> oneTimeTimersRepresentation() {
        return ONE_TIME_TIMERS;
    }

    @Override
    public IReadOnlyCollection<IRecurringTimer> recurringTimersRepresentation() {
        return RECURRING_TIMERS;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
