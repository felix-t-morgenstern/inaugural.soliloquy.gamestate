package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.valueobjects.GameState;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.Ruleset;

public class GameStub implements Game {
    @Override
    public GameState gameState() {
        return null;
    }

    @Override
    public Ruleset ruleset() {
        return null;
    }

    @Override
    public Logger logger() {
        return null;
    }

    @Override
    public Map<String, Action> actionsLibrary() {
        return null;
    }

    @Override
    public Map<String, Function> functionsLibrary() {
        return null;
    }
}
