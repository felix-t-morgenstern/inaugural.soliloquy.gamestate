package inaugural.soliloquy.gamestate;

import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.ITimer;
import soliloquy.logger.specs.ILogger;

public abstract class Timer implements ITimer {
    protected final String ID;
    protected final IGame GAME;
    protected final ILogger LOGGER;

    protected String _timerActionId;
    protected int _priority;

    protected Timer(String timerId, String timerActionId, IGame game, ILogger logger) {
        ID = timerId;
        _timerActionId = timerActionId;
        GAME = game;
        LOGGER = logger;
    }

    @Override
    public String timerActionId() {
        return _timerActionId;
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
