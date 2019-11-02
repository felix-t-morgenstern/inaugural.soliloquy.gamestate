package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;

public class VoidActionArchetype implements Action<Void> {
    @Override
    public void run(Void aVoid) throws IllegalArgumentException {

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
    public Void getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return Action.class.getCanonicalName() + "<" + Void.class.getCanonicalName() + ">";
    }
}
