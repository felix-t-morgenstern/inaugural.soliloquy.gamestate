package inaugural.soliloquy.gamestate.test.stubs;

import inaugural.soliloquy.gamestate.test.unit.RoundManagerImplTests;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class ActionStub<T> implements Action<T> {
    private final String _id;
    public boolean _actionRun;
    public T _mostRecentInput;
    public int _roundNumberRan;

    public RoundManager _roundManager;

    public static final List<ActionStub> ACTIONS_FIRED = new ArrayList<>();

    public ActionStub() {
        _id = null;
    }

    public ActionStub(String id) {
        _id = id;
    }

    @Override
    public void run(T t) throws IllegalArgumentException {
        _actionRun = true;
        _mostRecentInput = t;
        if (_roundManager != null) {
            _roundNumberRan = _roundManager.getRoundNumber();
        }
        ACTIONS_FIRED.add(this);
        RoundManagerImplTests.ROUND_END_ACTIONS_FIRED.add(this);
    }

    @Override
    public Game game() {
        return null;
    }

    @Override
    public Logger logger() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return _id;
    }

    @Override
    public T getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
