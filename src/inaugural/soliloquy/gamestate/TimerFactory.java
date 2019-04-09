package inaugural.soliloquy.gamestate;

import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.IOneTimeTimer;
import soliloquy.gamestate.specs.IRecurringTimer;
import soliloquy.gamestate.specs.ITimerFactory;
import soliloquy.logger.specs.ILogger;

public class TimerFactory implements ITimerFactory {
    private final IGame GAME;
    private final ILogger LOGGER;

    public TimerFactory(IGame game, ILogger logger) {
        GAME = game;
        LOGGER = logger;
    }

    @Override
    public IOneTimeTimer makeOneTimeTimer(String timerId, String timerActionId,
                                          long roundWhenGoesOff)
            throws IllegalArgumentException {
        return new OneTimeTimer(timerId, timerActionId, roundWhenGoesOff, GAME, LOGGER);
    }

    @Override
    public IRecurringTimer makeRecurringTimer(String timerId, String timerActionId, int roundModulo,
                                              int roundOffset)
            throws IllegalArgumentException {
        return new RecurringTimer(timerId, timerActionId, roundModulo, roundOffset, GAME, LOGGER);
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.gamestate.specs.ITimerFactory";
    }
}
