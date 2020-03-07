package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.factories.TimerFactory;

public class FakeTimerFactory implements TimerFactory {
    @Override
    public OneTimeTimer makeOneTimeTimer(String id, Action action, long roundWhenGoesOff)
            throws IllegalArgumentException {
        OneTimeTimer result = new FakeOneTimeTimer(id, action);
        result.setRoundWhenGoesOff(roundWhenGoesOff);
        return result;
    }

    @Override
    public RecurringTimer makeRecurringTimer(String id, Action action, int roundModulo,
                                             int roundOffset) throws IllegalArgumentException {
        RecurringTimer result = new FakeRecurringTimer(id, action);
        result.setRoundModulo(roundModulo);
        result.setRoundOffset(roundOffset);
        return result;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
