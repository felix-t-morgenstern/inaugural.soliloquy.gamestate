package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.common.valueobjects.IPair;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.gamestate.entities.IRoundManager;

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
