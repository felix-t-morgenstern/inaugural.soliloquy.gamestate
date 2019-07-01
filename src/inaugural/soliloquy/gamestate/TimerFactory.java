package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.IAction;
import soliloquy.specs.gamestate.entities.IOneTimeTimer;
import soliloquy.specs.gamestate.entities.IRecurringTimer;
import soliloquy.specs.gamestate.factories.ITimerFactory;

public class TimerFactory implements ITimerFactory {
    // TODO: Ensure that this is tested

    public TimerFactory() {
        // TODO: Test nulls not allowed
    }

    @Override
    public IOneTimeTimer makeOneTimeTimer(String timerId, IAction<Void> action,
                                          long roundWhenGoesOff)
            throws IllegalArgumentException {
        // TODO: Refactor class to accept Consumers in constructor
        //return new OneTimeTimer(timerId, action, roundWhenGoesOff, ROUND_MANAGER);
        return null;
    }

    @Override
    public IRecurringTimer makeRecurringTimer(String timerId, IAction<Void> action, int roundModulo,
                                              int roundOffset)
            throws IllegalArgumentException {
        // TODO: Refactor class to accept Consumers in constructor
        //return new RecurringTimer(timerId, action, roundModulo, roundOffset, ROUND_MANAGER);
        return null;
    }

    @Override
    public String getInterfaceName() {
        return ITimerFactory.class.getCanonicalName();
    }
}
