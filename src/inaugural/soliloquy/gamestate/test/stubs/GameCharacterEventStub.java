package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.logger.Logger;

public class GameCharacterEventStub implements GameCharacterEvent {
    private final String ID;

    public GameCharacterEventStub(String id) {
        ID = id;
    }

    @Override
    public Boolean fire(Character character) throws IllegalArgumentException {
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
        return ID;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
