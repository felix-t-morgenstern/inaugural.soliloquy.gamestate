package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.logger.Logger;

public class GameCharacterEventArchetype implements GameCharacterEvent {
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
        return null;
    }

    @Override
    public String getInterfaceName() {
        return GameCharacterEvent.class.getCanonicalName();
    }
}
