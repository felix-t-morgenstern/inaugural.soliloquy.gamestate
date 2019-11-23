package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.RecurringTimer;

public class RecurringTimerArchetype implements RecurringTimer {
    @Override
    public int getRoundModulo() {
        return 0;
    }

    @Override
    public void setRoundModulo(int i) throws IllegalArgumentException {

    }

    @Override
    public int getRoundOffset() {
        return 0;
    }

    @Override
    public void setRoundOffset(int i) throws IllegalArgumentException {

    }

    @Override
    public String actionTypeId() {
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
        return RecurringTimer.class.getCanonicalName();
    }
}
