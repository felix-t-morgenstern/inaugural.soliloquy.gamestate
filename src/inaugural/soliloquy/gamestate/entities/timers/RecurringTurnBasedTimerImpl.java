package inaugural.soliloquy.gamestate.entities.timers;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;

import java.util.function.Consumer;

public class RecurringTurnBasedTimerImpl extends AbstractTurnBasedTimer
        implements RecurringTurnBasedTimer {
    private final int ROUND_MODULO;
    private final int ROUND_OFFSET;

    private final Consumer<RecurringTurnBasedTimer>
            REMOVE_RECURRING_TURN_BASED_TIMER_FROM_ROUND_MANAGER;

    @SuppressWarnings("rawtypes")
    public RecurringTurnBasedTimerImpl(String timerId, Action action, int roundModulo,
                                       int roundOffset, int priority,
                                       Consumer<RecurringTurnBasedTimer>
                                                   addRecurringTurnBasedTimerToRoundManager,
                                       Consumer<RecurringTurnBasedTimer>
                                                   removeRecurringTurnBasedTimerFromRoundManager) {
        super(timerId, action, priority);
        ROUND_MODULO = roundModulo;
        ROUND_OFFSET = roundOffset;
        REMOVE_RECURRING_TURN_BASED_TIMER_FROM_ROUND_MANAGER =
                removeRecurringTurnBasedTimerFromRoundManager;
        addRecurringTurnBasedTimerToRoundManager.accept(this);
    }

    @Override
    public int roundModulo() {
        enforceDeletionInvariants();
        return ROUND_MODULO;
    }

    @Override
    public int roundOffset() {
        enforceDeletionInvariants();
        return ROUND_OFFSET;
    }

    @Override
    public String getInterfaceName() {
        return RecurringTurnBasedTimer.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof RecurringTurnBasedTimer)) {
            return false;
        }
        RecurringTurnBasedTimer recurringTimer = (RecurringTurnBasedTimer) o;
        return ID.equals(recurringTimer.id());
    }

    @Override
    public void delete() throws IllegalStateException {
        REMOVE_RECURRING_TURN_BASED_TIMER_FROM_ROUND_MANAGER.accept(this);
        super.delete();
    }
}
