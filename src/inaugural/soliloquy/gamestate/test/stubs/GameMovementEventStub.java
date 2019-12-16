package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.logger.Logger;

public class GameMovementEventStub implements GameMovementEvent {
    private String _id;

    public GameMovementEventStub(String id) {
        _id = id;
    }

    @Override
    public Boolean fire(Character character, GameEventTarget gameEventTarget) throws IllegalArgumentException {
        return null;
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
    public String getInterfaceName() {
        return null;
    }
}
