package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;

public class OneTimeTurnBasedTimerArchetype implements OneTimeTurnBasedTimer {

    @Override
    public Action action() {
        return null;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void run() {

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
        return OneTimeTurnBasedTimer.class.getCanonicalName();
    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public long roundWhenGoesOff() {
        return 0;
    }
}
