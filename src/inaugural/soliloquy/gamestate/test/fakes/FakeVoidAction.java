package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;

public class FakeVoidAction implements Action<Void> {
    private String _id;

    public FakeVoidAction(String id) {
        _id = id;
    }

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
        return _id;
    }

    @Override
    public Void getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
