package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.logger.Logger;

public class FakeGameCharacterEvent implements GameCharacterEvent {
    private final String ID;

    public Character _characterFired;

    public FakeGameCharacterEvent(String id) {
        ID = id;
    }

    @Override
    public Boolean fire(Character character) throws IllegalArgumentException {
        _characterFired = character;
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
