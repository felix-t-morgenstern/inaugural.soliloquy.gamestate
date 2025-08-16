package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;


import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;

public class FakeRecurringClockBasedTimer implements RecurringClockBasedTimer {
    public int PeriodDuration;
    public int PeriodModuloOffset;
    public long LastFiringTimestamp;
    public boolean FireMultipleTimesForMultiplePeriodsElapsed;
    public List<Long> FiredTimes = listOf();
    public String Id = randomString();

    public FakeRecurringClockBasedTimer() {
    }

    @Override
    public int periodDuration() {
        return PeriodDuration;
    }

    @Override
    public int periodModuloOffset() {
        return PeriodModuloOffset;
    }

    @Override
    public long lastFiringTimestamp() {
        return LastFiringTimestamp;
    }

    @Override
    public boolean fireMultipleTimesForMultiplePeriodsElapsed() {
        return FireMultipleTimesForMultiplePeriodsElapsed;
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
    public String actionId() {
        return null;
    }

    @Override
    public void fire(long timestamp) {
        FiredTimes.add(timestamp);
    }

    @Override
    public String id() throws IllegalStateException {
        return Id;
    }
}
