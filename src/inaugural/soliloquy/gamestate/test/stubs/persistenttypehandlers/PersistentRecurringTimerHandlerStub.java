package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.RecurringTimerStub;
import soliloquy.specs.gamestate.entities.RecurringTimer;

public class PersistentRecurringTimerHandlerStub
        extends PersistentValueTypeHandlerStub<RecurringTimer> {
    @Override
    public String typeName() {
        return "RecurringTimer";
    }

    @Override
    protected RecurringTimer generateInstance() {
        return new RecurringTimerStub();
    }
}
