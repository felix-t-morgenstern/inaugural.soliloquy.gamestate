package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.gamestate.factories.ITimerFactory;
import soliloquy.specs.logger.ILogger;

public class TimerFactory implements ITimerFactory {
    private final IGame GAME;
    private final ILogger LOGGER;

    public TimerFactory(IGame game, ILogger logger) {
        GAME = game;
        LOGGER = logger;
    }

    @Override
    public IOneTimeTimer makeOneTimeTimer(String timerId, IAction<Void> action,
                                          long roundWhenGoesOff)
            throws IllegalArgumentException {
        return new OneTimeTimer(timerId, action, roundWhenGoesOff, GAME, LOGGER);
    }

    @Override
    public IRecurringTimer makeRecurringTimer(String timerId, IAction<Void> action, int roundModulo,
                                              int roundOffset)
            throws IllegalArgumentException {
        return new RecurringTimer(timerId, action, roundModulo, roundOffset, GAME, LOGGER);
    }

    @Override
    public String getInterfaceName() {
        return ITimerFactory.class.getCanonicalName();
    }
}
