package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.AbstractFinitePausableAtTime;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;

public class OneTimeClockBasedTimerImpl extends AbstractFinitePausableAtTime
        implements OneTimeClockBasedTimer {
    private final Action<Long> FIRING_ACTION;

    public OneTimeClockBasedTimerImpl(long firingTime, Action<Long> firingAction,
                                      Long pausedTimestamp, Long mostRecentTimestamp) {
        super(firingTime, pausedTimestamp, mostRecentTimestamp);
        if (pausedTimestamp != null && pausedTimestamp >= firingTime) {
            throw new IllegalArgumentException("OneTimeClockBasedTimerImpl: pausedTimestamp (" +
                    pausedTimestamp + ") cannot be greater than or equal to firingTime (" +
                    firingTime + ")");
        }
        FIRING_ACTION = Check.ifNull(firingAction, "firingAction");
    }

    @Override
    public long firingTime() {
        return _anchorTime;
    }

    @Override
    public String getInterfaceName() {
        return OneTimeClockBasedTimer.class.getCanonicalName();
    }

    @Override
    public String actionId() {
        return FIRING_ACTION.id();
    }

    @Override
    public void fire(long timestamp) {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        if (_pausedTimestamp != null && timestamp >= _pausedTimestamp) {
            throw new UnsupportedOperationException("OneTimeClockBasedTimerImpl.fire: timestamp " +
                    "(" + timestamp + ") cannot be greater than current pausedTimestamp (" +
                    _pausedTimestamp + ")");
        }
        FIRING_ACTION.run(timestamp);
    }

    @Override
    public Long mostRecentTimestamp() {
        return TIMESTAMP_VALIDATOR.mostRecentTimestamp();
    }
}
