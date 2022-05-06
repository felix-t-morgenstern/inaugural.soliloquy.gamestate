package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;

import java.util.function.Consumer;

public class FakeOneTimeRoundBasedTimer extends FakeRoundBasedTimer
        implements OneTimeRoundBasedTimer {
    private final Consumer<OneTimeRoundBasedTimer> ON_DELETE;

    private long _roundWhenGoesOff;

    public FakeOneTimeRoundBasedTimer() {
        ON_DELETE = null;
    }

    public FakeOneTimeRoundBasedTimer(String id, @SuppressWarnings("rawtypes") Action action,
                                     long roundWhenGoesOff, int priority) {
        super(id, action, priority);
        _roundWhenGoesOff = roundWhenGoesOff;
        ON_DELETE = null;
    }

    public FakeOneTimeRoundBasedTimer(String id, @SuppressWarnings("rawtypes") Action action,
                                     long roundWhenGoesOff, int priority,
                                     Consumer<OneTimeRoundBasedTimer> onDelete) {
        super(id, action, priority);
        _roundWhenGoesOff = roundWhenGoesOff;
        ON_DELETE = onDelete;
    }

    @Override
    public long roundWhenGoesOff() {
        return _roundWhenGoesOff;
    }

    @Override
    public void run() {
        super.run();
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
