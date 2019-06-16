package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.logger.ILogger;

public class OneTimeTimer extends Timer implements IOneTimeTimer {
    private long _roundWhenGoesOff;

    public OneTimeTimer(String timerId, IAction<Void> timerActionId, long roundWhenGoesOff, IGame game,
                        ILogger logger) {
        super(timerId, timerActionId, game, logger);
        _roundWhenGoesOff = roundWhenGoesOff;
    }

    @Override
    public long getRoundWhenGoesOff() {
        return _roundWhenGoesOff;
    }

    @Override
    public void setRoundWhenGoesOff(long roundWhenGoesOff) throws IllegalArgumentException {
        _roundWhenGoesOff = roundWhenGoesOff;
    }

    @Override
    public String getInterfaceName() {
        return IOneTimeTimer.class.getCanonicalName();
    }
}
