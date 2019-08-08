package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventSource;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.logger.Logger;

public class GameEventArchetype implements GameEvent {
    @Override
    public void fire(GameEventSource gameEventSource, GameEventTarget gameEventTarget) throws IllegalArgumentException {

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
        return GameEvent.class.getCanonicalName();
    }
}
