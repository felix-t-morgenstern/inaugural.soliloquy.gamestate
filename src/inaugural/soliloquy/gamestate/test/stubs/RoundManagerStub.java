package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPair;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.IOneTimeTimer;
import soliloquy.gamestate.specs.IRecurringTimer;
import soliloquy.gamestate.specs.IRoundManager;

public class RoundManagerStub implements IRoundManager {
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
    public ICollection<IOneTimeTimer> oneTimeTimers() {
        return null;
    }

    @Override
    public ICollection<IRecurringTimer> recurringTimers() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
