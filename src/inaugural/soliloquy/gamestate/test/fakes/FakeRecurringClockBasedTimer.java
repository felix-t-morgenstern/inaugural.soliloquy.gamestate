package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

import java.util.ArrayList;

public class FakeRecurringClockBasedTimer implements RecurringClockBasedTimer {
    public int FiringTimePeriod;
    public int FiringTimeModuloOffset;
    public long LastFiringTimestamp;
    public boolean FireMultipleTimesForMultiplePeriodsElapsed;
    public ArrayList<Long> FiredTimes = new ArrayList<>();

    public FakeRecurringClockBasedTimer() {
    }

    @Override
    public int firingTimePeriod() {
        return FiringTimePeriod;
    }

    @Override
    public int firingTimeModuloOffset() {
        return FiringTimeModuloOffset;
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
    public void fire(long timestamp) {
        FiredTimes.add(timestamp);
    }

    @Override
    public void reportPause(long timestamp) throws IllegalArgumentException {

    }

    @Override
    public void reportUnpause(long timestamp) throws IllegalArgumentException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
