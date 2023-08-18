package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;


import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;

public class FakeOneTimeClockBasedTimer implements OneTimeClockBasedTimer {
    public long FiringTime;
    public List<Long> FiredTimes = listOf();
    public String Id = randomString();

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
    public Long pausedTimestamp() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String actionId() {
        return null;
    }

    @Override
    public void fire(long timestamp) {
        FiredTimes.add(timestamp);
    }

    @Override
    public Long mostRecentTimestamp() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return Id;
    }
}
