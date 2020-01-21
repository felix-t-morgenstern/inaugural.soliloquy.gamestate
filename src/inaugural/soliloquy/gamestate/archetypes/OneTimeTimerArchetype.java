package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;

public class OneTimeTimerArchetype implements OneTimeTimer {
    @Override
    public long getRoundWhenGoesOff() {
        return 0;
    }

    @Override
    public void setRoundWhenGoesOff(long l) throws IllegalArgumentException {

    }

    @Override
    public Action action() {
        return null;
    }

    @Override
    public void fire() {

    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setPriority(int i) {

    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return OneTimeTimer.class.getCanonicalName();
    }
}
