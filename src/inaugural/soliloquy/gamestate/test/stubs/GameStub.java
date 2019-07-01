package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.valueobjects.IGameState;
import soliloquy.specs.logger.ILogger;
import soliloquy.specs.ruleset.IRuleset;

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
