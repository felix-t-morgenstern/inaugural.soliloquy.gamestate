package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.AbstractFinitePausableAtTime;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;

public class OneTimeClockBasedTimerImpl extends AbstractFinitePausableAtTime
        implements OneTimeClockBasedTimer {
    private final String ID;
    private final Action<Long> FIRING_ACTION;

    private boolean hasFired = false;

    public OneTimeClockBasedTimerImpl(String id, long firingTime, Action<Long> firingAction,
                                      Long pausedTimestamp, Long mostRecentTimestamp) {
        super(firingTime, pausedTimestamp, mostRecentTimestamp);
        ID = Check.ifNullOrEmpty(id, "id");
        if (pausedTimestamp != null && pausedTimestamp >= firingTime) {
            throw new IllegalArgumentException("OneTimeClockBasedTimerImpl: pausedTimestamp (" +
                    pausedTimestamp + ") cannot be greater than or equal to firingTime (" +
                    firingTime + ")");
        }
        FIRING_ACTION = Check.ifNull(firingAction, "firingAction");
    }

    @Override
    public long firingTime() {
        return anchorTime;
    }

    @Override
    public String actionId() {
        return FIRING_ACTION.id();
    }

    @Override
    public void fire(long timestamp) {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        if (pausedTimestamp != null && timestamp >= pausedTimestamp) {
            throw new UnsupportedOperationException("OneTimeClockBasedTimerImpl.fire: timestamp " +
                    "(" + timestamp + ") cannot be greater than current pausedTimestamp (" +
                    pausedTimestamp + ")");
        }
        FIRING_ACTION.run(timestamp);
        hasFired = true;
    }

    @Override
    public void reportPause(long timestamp) {
        if (hasFired) {
            throw new UnsupportedOperationException(
                    "OneTimeClockBasedTimerImpl.reportPause: Timer has already fired");
        }

        super.reportPause(timestamp);
    }

    @Override
    public void reportUnpause(long timestamp) {
        if (hasFired) {
            throw new UnsupportedOperationException(
                    "OneTimeClockBasedTimerImpl.reportUnpause: Timer has already fired");
        }

        super.reportUnpause(timestamp);
    }

    @Override
    public Long mostRecentTimestamp() {
        return TIMESTAMP_VALIDATOR.mostRecentTimestamp();
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }
}
