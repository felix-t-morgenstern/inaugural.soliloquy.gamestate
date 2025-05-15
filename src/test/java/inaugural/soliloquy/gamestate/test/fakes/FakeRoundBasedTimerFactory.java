package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

public class FakeRoundBasedTimerFactory implements RoundBasedTimerFactory {
    @Override
    public OneTimeRoundBasedTimer makeOneTimeTimer(String id, Action action, int roundWhenGoesOff,
                                                   int priority)
            throws IllegalArgumentException {
        return new FakeOneTimeRoundBasedTimer(id, action, roundWhenGoesOff, priority);
    }

    @Override
    public RecurringRoundBasedTimer makeRecurringTimer(String id, Action action, int roundModulo,
                                                       int roundOffset, int priority)
            throws IllegalArgumentException {
        return new FakeRecurringRoundBasedTimer(id, action, roundModulo, roundOffset, priority);
    }
}
