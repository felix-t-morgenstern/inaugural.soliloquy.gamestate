package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.OneTimeTimer;

import java.util.function.Consumer;

public class OneTimeTimerStub extends TimerStub implements OneTimeTimer {
    private final Consumer<OneTimeTimer> ON_DELETE;

    private long _roundWhenGoesOff;

    public OneTimeTimerStub() {
        this(null);
    }

    public OneTimeTimerStub(Consumer<OneTimeTimer> onDelete) {
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
    public String actionTypeId() {
        return null;
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
