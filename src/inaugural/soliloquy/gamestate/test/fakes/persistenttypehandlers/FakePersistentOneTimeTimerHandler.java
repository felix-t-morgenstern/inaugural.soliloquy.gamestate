package inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.fakes.FakeOneTimeTimer;
import soliloquy.specs.gamestate.entities.OneTimeTimer;

public class FakePersistentOneTimeTimerHandler
        extends FakePersistentValueTypeHandler<OneTimeTimer> {
    @Override
    public String typeName() {
        return "OneTimeTimer";
    }

    @Override
    protected OneTimeTimer generateInstance() {
        return new FakeOneTimeTimer();
    }
}
