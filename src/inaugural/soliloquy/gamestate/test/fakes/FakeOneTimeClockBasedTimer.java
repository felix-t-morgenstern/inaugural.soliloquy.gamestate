package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;

import java.util.ArrayList;

public class FakeOneTimeClockBasedTimer implements OneTimeClockBasedTimer {
    public long FiringTime;
    public ArrayList<Long> FiredTimes = new ArrayList<>();

    public FakeOneTimeClockBasedTimer() {
    }

    @Override
    public long firingTime() {
        return FiringTime;
    }

    @Override
    public void reportPause(long timestamp) throws IllegalArgumentException {

    }

    @Override
    public void reportUnpause(long timestamp) throws IllegalArgumentException {

    }

    @Override
    public void fire(long timestamp) {
        FiredTimes.add(timestamp);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
