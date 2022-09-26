package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.logger.Logger;

public class GameMovementEventArchetype implements GameMovementEvent {
    @Override
    public Boolean fire(Character character, GameEventTarget gameEventTarget)
            throws IllegalArgumentException {
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
        return GameMovementEvent.class.getCanonicalName();
    }
}
