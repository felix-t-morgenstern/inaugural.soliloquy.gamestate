package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;

public class FakeOneTimeRoundBasedTimerHandler
        extends FakeTypeHandler<OneTimeRoundBasedTimer> {
    @Override
    public String typeName() {
        return "OneTimeTimer";
    }

    @Override
    protected OneTimeRoundBasedTimer generateInstance() {
        return new FakeOneTimeRoundBasedTimer();
    }
}
