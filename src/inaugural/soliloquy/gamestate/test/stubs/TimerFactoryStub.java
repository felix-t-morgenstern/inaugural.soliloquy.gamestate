package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.factories.TimerFactory;

public class TimerFactoryStub implements TimerFactory {
    @Override
    public OneTimeTimer makeOneTimeTimer(String id, Action action, long roundWhenGoesOff)
            throws IllegalArgumentException {
        OneTimeTimer result = new OneTimeTimerStub(id, action);
        result.setRoundWhenGoesOff(roundWhenGoesOff);
        return result;
    }

    @Override
    public RecurringTimer makeRecurringTimer(String id, Action action, int roundModulo,
                                             int roundOffset) throws IllegalArgumentException {
        RecurringTimer result = new RecurringTimerStub(id, action);
        result.setRoundModulo(roundModulo);
        result.setRoundOffset(roundOffset);
        return result;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
