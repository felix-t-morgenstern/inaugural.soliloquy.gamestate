package inaugural.soliloquy.gamestate.test.fakes;


import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;

import java.util.function.Consumer;

public class FakeOneTimeTurnBasedTimer extends FakeTurnBasedTimer
        implements OneTimeTurnBasedTimer {
    private final Consumer<OneTimeTurnBasedTimer> ON_DELETE;

    private long _roundWhenGoesOff;

    public FakeOneTimeTurnBasedTimer() {
        ON_DELETE = null;
    }

    public FakeOneTimeTurnBasedTimer(String id, @SuppressWarnings("rawtypes") Action action,
                                     long roundWhenGoesOff, int priority) {
        super(id, action, priority);
        _roundWhenGoesOff = roundWhenGoesOff;
        ON_DELETE = null;
    }

    public FakeOneTimeTurnBasedTimer(String id, @SuppressWarnings("rawtypes") Action action,
                                     long roundWhenGoesOff, int priority,
                                     Consumer<OneTimeTurnBasedTimer> onDelete) {
        super(id, action, priority);
        _roundWhenGoesOff = roundWhenGoesOff;
        ON_DELETE = onDelete;
    }

    @Override
    public long roundWhenGoesOff() {
        return _roundWhenGoesOff;
    }

    @Override
    public void fire() {
        super.fire();
        delete();
    }

    @Override
    public void delete() {
        if (ON_DELETE != null) {
            ON_DELETE.accept(this);
        }
        super.delete();
    }
}
