package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadOnlyCollection;
import soliloquy.specs.common.infrastructure.ReadOnlyMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.ArrayList;
import java.util.List;

public class RoundManagerImpl implements RoundManager {
    private final List<OneTimeTimer> ONE_TIME_TIMERS;
    private final List<RecurringTimer> RECURRING_TIMERS;

    private int _roundNumber;

    public RoundManagerImpl() {
        ONE_TIME_TIMERS = new ArrayList<>();
        RECURRING_TIMERS = new ArrayList<>();
    }

    @Override
    public ReadOnlyMap<Integer, Pair<Character, Integer>> characterOrder() {
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
        return null;
    }

    @Override
    public ReadOnlyCollection<RecurringTimer> recurringTimersRepresentation() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void addOneTimeTimer(OneTimeTimer oneTimeTimer) {

    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void removeOneTimeTimer(OneTimeTimer oneTimeTimer) {

    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void addRecurringTimer(RecurringTimer recurringTimer) {

    }

    // NB: These are NOT exposed by the interface; calling these directly is strongly discouraged
    public void removeRecurringTimer(RecurringTimer recurringTimer) {

    }
}
