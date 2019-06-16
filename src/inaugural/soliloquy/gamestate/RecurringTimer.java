package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.game.IGame;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.logger.ILogger;

public class RecurringTimer extends Timer implements IRecurringTimer {
    private int _roundModulo;
    private int _roundOffset;

    public RecurringTimer(String timerId, IAction<Void> action, int roundModulo, int roundOffset,
                          IGame game, ILogger logger) {
        super(timerId, action, game, logger);
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
    public String getInterfaceName() {
        return IRecurringTimer.class.getCanonicalName();
    }
}
