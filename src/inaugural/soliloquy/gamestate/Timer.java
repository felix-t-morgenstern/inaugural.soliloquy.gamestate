package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.ITimer;
import soliloquy.specs.logger.ILogger;

public abstract class Timer implements ITimer {
    private final String ID;
    private final IGame GAME;
    private final ILogger LOGGER;

    private IAction<Void> _action;
    private int _priority;

    Timer(String timerId, IAction<Void> action, IGame game, ILogger logger) {
        ID = timerId;
        _action = action;
        GAME = game;
        LOGGER = logger;
    }

    @Override
    public IAction<Void> action() {
        return _action;
    }

    @Override
    public int getPriority() {
        return _priority;
    }

    @Override
    public void setPriority(int priority) {
        _priority = priority;
    }

    @Override
    public IGame game() {
        return GAME;
    }

    @Override
    public ILogger logger() {
        return LOGGER;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }
}
