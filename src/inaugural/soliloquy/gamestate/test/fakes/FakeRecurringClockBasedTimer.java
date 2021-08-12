package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

import java.util.ArrayList;

public class FakeRecurringClockBasedTimer implements RecurringClockBasedTimer {
    public int PeriodDuration;
    public int PeriodModuloOffset;
    public long LastFiringTimestamp;
    public boolean FireMultipleTimesForMultiplePeriodsElapsed;
    public ArrayList<Long> FiredTimes = new ArrayList<>();

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
}
