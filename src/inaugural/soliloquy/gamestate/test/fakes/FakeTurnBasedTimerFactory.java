package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;
import soliloquy.specs.gamestate.factories.TurnBasedTimerFactory;

public class FakeTurnBasedTimerFactory implements TurnBasedTimerFactory {
    @Override
    public OneTimeTurnBasedTimer makeOneTimeTimer(String id, Action action, long roundWhenGoesOff,
                                                  int priority)
            throws IllegalArgumentException {
        return new FakeOneTimeTurnBasedTimer(id, action, roundWhenGoesOff, priority);
    }

    @Override
    public RecurringTurnBasedTimer makeRecurringTimer(String id, Action action, int roundModulo,
                                                      int roundOffset, int priority)
            throws IllegalArgumentException {
        return new FakeRecurringTurnBasedTimer(id, action, roundModulo, roundOffset, priority);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
