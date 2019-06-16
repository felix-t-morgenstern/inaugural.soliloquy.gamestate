package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.logger.ILogger;

public class ActionStub<T> implements IAction<T> {
    @Override
    public void run(T t) throws IllegalArgumentException {

    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public IGame game() {
        return null;
    }

    @Override
    public ILogger logger() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public T getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
