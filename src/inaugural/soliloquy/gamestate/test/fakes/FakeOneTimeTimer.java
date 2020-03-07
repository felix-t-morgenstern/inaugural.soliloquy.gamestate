package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;

import java.util.function.Consumer;

public class FakeOneTimeTimer extends FakeTimer implements OneTimeTimer {
    private final Consumer<OneTimeTimer> ON_DELETE;

    private long _roundWhenGoesOff;

    public FakeOneTimeTimer() {
        ON_DELETE = null;
    }

    public FakeOneTimeTimer(String id, Action action) {
        super(id, action);
        ON_DELETE = null;
    }

    public FakeOneTimeTimer(Consumer<OneTimeTimer> onDelete) {
        ON_DELETE = onDelete;
    }

    @Override
    public long getRoundWhenGoesOff() {
        return _roundWhenGoesOff;
    }

    @Override
    public void setRoundWhenGoesOff(long l) throws IllegalArgumentException {
        _roundWhenGoesOff = l;
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
