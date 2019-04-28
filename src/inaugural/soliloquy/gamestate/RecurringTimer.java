package inaugural.soliloquy.gamestate;

import soliloquy.game.primary.specs.IGame;
import soliloquy.gamestate.specs.IRecurringTimer;
import soliloquy.logger.specs.ILogger;

public class RecurringTimer extends Timer implements IRecurringTimer {
    private int _roundModulo;
    private int _roundOffset;

    public RecurringTimer(String timerId, String timerActionId, int roundModulo, int roundOffset,
                          IGame game, ILogger logger) {
        super(timerId, timerActionId, game, logger);
        _roundModulo = roundModulo;
        _roundOffset = roundOffset;
    }

    @Override
    public int getRoundModulo() {
        return _roundModulo;
    }

    @Override
    public void setRoundModulo(int roundModulo) throws IllegalArgumentException {
        _roundModulo = roundModulo;
    }

    @Override
    public int getRoundOffset() {
        return _roundOffset;
    }

    @Override
    public void setRoundOffset(int roundOffset) throws IllegalArgumentException {
        _roundOffset = roundOffset;
    }

    @Override
    public void read(String s, boolean b) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return IRecurringTimer.class.getCanonicalName();
    }
}
