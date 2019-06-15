package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IAction;
import soliloquy.common.specs.IFunction;
import soliloquy.common.specs.IMap;
import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.IGameState;
import soliloquy.logger.specs.ILogger;
import soliloquy.ruleset.primary.specs.IRuleset;

public class GameStub implements IGame {
    @Override
    public IGameState gameState() {
        return null;
    }

    @Override
    public IRuleset ruleset() {
        return null;
    }

    @Override
    public ILogger logger() {
        return null;
    }

    @Override
    public IMap<String, IAction> actionsLibrary() {
        return null;
    }

    @Override
    public IMap<String, IFunction> functionsLibrary() {
        return null;
    }
}
