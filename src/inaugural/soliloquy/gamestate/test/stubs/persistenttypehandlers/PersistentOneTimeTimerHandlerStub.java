package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.OneTimeTimerStub;
import soliloquy.specs.gamestate.entities.OneTimeTimer;

public class PersistentOneTimeTimerHandlerStub
        extends PersistentValueTypeHandlerStub<OneTimeTimer> {
    @Override
    public String typeName() {
        return "OneTimeTimer";
    }

    @Override
    protected OneTimeTimer generateInstance() {
        return new OneTimeTimerStub();
    }
}
