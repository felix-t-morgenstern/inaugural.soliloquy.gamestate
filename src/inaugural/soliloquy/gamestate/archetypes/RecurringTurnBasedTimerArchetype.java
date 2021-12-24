package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;

public class RecurringTurnBasedTimerArchetype implements RecurringTurnBasedTimer {

    @Override
    public String getInterfaceName() {
        return RecurringTurnBasedTimer.class.getCanonicalName();
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void run() {

    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public Action action() {
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
    public int roundModulo() {
        return 0;
    }

    @Override
    public int roundOffset() {
        return 0;
    }
}
