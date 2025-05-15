package inaugural.soliloquy.gamestate.entities.timers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;

import java.util.function.Consumer;

public class RecurringRoundBasedTimerImpl extends AbstractRoundBasedTimer<RecurringRoundBasedTimer>
        implements RecurringRoundBasedTimer {
    private final int ROUND_MODULO;
    private final int ROUND_OFFSET;

    @SuppressWarnings("rawtypes")
    public RecurringRoundBasedTimerImpl(String timerId, Action action, int roundModulo,
                                        int roundOffset, int priority,
                                        Consumer<RecurringRoundBasedTimer>
                                                addRecurringRoundBasedTimerToRoundManager,
                                        Consumer<RecurringRoundBasedTimer>
                                                removeRecurringRoundBasedTimerFromRoundManager) {
        super(timerId, action, priority, addRecurringRoundBasedTimerToRoundManager,
                removeRecurringRoundBasedTimerFromRoundManager);
        ROUND_MODULO = Check.throwOnLteZero(roundModulo, "roundModulo");
        Check.throwOnLtValue(roundOffset, 0, "roundOffset");
        Check.throwOnSecondLte(roundOffset, roundModulo, "roundOffset", "roundModulo");
        ROUND_OFFSET = roundOffset;
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
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof RecurringRoundBasedTimer recurringTimer)) {
            return false;
        }
        return ROUND_MODULO == recurringTimer.roundModulo() &&
                ROUND_OFFSET == recurringTimer.roundOffset() &&
                equalz(recurringTimer);
    }
}
