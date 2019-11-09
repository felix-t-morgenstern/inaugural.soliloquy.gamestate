package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.GameState;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
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
    public Registry<Action> actions() {
        return null;
    }

    @Override
    public Registry<Function> functions() {
        return null;
    }

    @Override
    public Registry<GameMovementEvent> gameEvents() {
        return null;
    }
}
