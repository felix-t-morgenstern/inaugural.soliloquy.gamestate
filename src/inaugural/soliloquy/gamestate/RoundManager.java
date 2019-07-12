package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyCollection;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.gamestate.entities.IRoundManager;

import java.util.ArrayList;
import java.util.List;

public class RoundManager implements IRoundManager {
    private final List<IOneTimeTimer> ONE_TIME_TIMERS;
    private final List<IRecurringTimer> RECURRING_TIMERS;

    private int _roundNumber;

    public RoundManager() {
        ONE_TIME_TIMERS = new ArrayList<>();
        RECURRING_TIMERS = new ArrayList<>();
    }

    @Override
    public IReadOnlyMap<Integer, IPair<ICharacter, Integer>> characterOrder() {
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
        return null;
    }

    @Override
    public IReadOnlyCollection<IRecurringTimer> recurringTimersRepresentation() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void addOneTimeTimer(IOneTimeTimer oneTimeTimer) {

    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void removeOneTimeTimer(IOneTimeTimer oneTimeTimer) {

    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void addRecurringTimer(IRecurringTimer recurringTimer) {

    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void removeRecurringTimer(IRecurringTimer recurringTimer) {

    }
}
