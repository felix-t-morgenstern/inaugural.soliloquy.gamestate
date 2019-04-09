package inaugural.soliloquy.gamestate;

import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.IOneTimeTimer;
import soliloquy.logger.specs.ILogger;

public class OneTimeTimer extends Timer implements IOneTimeTimer {
    private long _roundWhenGoesOff;

    public OneTimeTimer(String timerId, String timerActionId, long roundWhenGoesOff, IGame game,
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
    public void read(String s, boolean b) throws IllegalArgumentException {
        // TODO: Implement and test soon!
    }

    @Override
    public String write() throws IllegalArgumentException {
        // TODO: Implement and test soon!
        return null;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.gamestate.specs.IOneTimeTimer";
    }
}
