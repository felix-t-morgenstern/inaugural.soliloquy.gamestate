package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.List;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class RoundBasedTimerManagerHandler extends AbstractTypeHandler<RoundBasedTimerManager> {
    private final RoundBasedTimerManager ROUND_BASED_TIMER_MANAGER;
    private final TypeHandler<OneTimeRoundBasedTimer> ONE_TIME_ROUND_BASED_TIMER_HANDLER;
    private final TypeHandler<RecurringRoundBasedTimer> RECURRING_ROUND_BASED_TIMER_HANDLER;

    public RoundBasedTimerManagerHandler(RoundBasedTimerManager roundBasedTimerManager,
                                         TypeHandler<OneTimeRoundBasedTimer>
                                                 oneTimeRoundBasedTimerTypeHandler,
                                         TypeHandler<RecurringRoundBasedTimer>
                                                 recurringRoundBasedTimerTypeHandler) {
        super(generateSimpleArchetype(RoundBasedTimerManager.class));
        ROUND_BASED_TIMER_MANAGER = Check.ifNull(roundBasedTimerManager, "roundBasedTimerManager");
        ONE_TIME_ROUND_BASED_TIMER_HANDLER = Check.ifNull(oneTimeRoundBasedTimerTypeHandler,
                "oneTimeRoundBasedTimerTypeHandler");
        RECURRING_ROUND_BASED_TIMER_HANDLER = Check.ifNull(recurringRoundBasedTimerTypeHandler,
                "recurringRoundBasedTimerTypeHandler");
    }

    @Override
    public RoundBasedTimerManager read(String writtenValue) throws IllegalArgumentException {
        ROUND_BASED_TIMER_MANAGER.clear();

        RoundBasedTimerManagerDTO dto =
                JSON.fromJson(writtenValue, RoundBasedTimerManagerDTO.class);

        for (String oneTimeRoundBasedTimer : dto.oneTimeRoundBasedTimers) {
            ROUND_BASED_TIMER_MANAGER.registerOneTimeRoundBasedTimer(
                    ONE_TIME_ROUND_BASED_TIMER_HANDLER.read(oneTimeRoundBasedTimer));
        }

        for (String recurringRoundBasedTimer : dto.recurringRoundBasedTimers) {
            ROUND_BASED_TIMER_MANAGER.registerRecurringRoundBasedTimer(
                    RECURRING_ROUND_BASED_TIMER_HANDLER.read(recurringRoundBasedTimer));
        }

        return null;
    }

    @Override
    public String write(RoundBasedTimerManager roundBasedTimerManager) {
        Check.ifNull(roundBasedTimerManager, "roundBasedTimerManager");

        RoundBasedTimerManagerDTO dto = new RoundBasedTimerManagerDTO();

        List<OneTimeRoundBasedTimer> oneTimeRoundBasedTimers =
                roundBasedTimerManager.oneTimeRoundBasedTimersRepresentation();
        dto.oneTimeRoundBasedTimers = new String[oneTimeRoundBasedTimers.size()];
        for (int i = 0; i < oneTimeRoundBasedTimers.size(); i++) {
            dto.oneTimeRoundBasedTimers[i] =
                    ONE_TIME_ROUND_BASED_TIMER_HANDLER.write(oneTimeRoundBasedTimers.get(i));
        }

        List<RecurringRoundBasedTimer> recurringRoundBasedTimers =
                roundBasedTimerManager.recurringRoundBasedTimersRepresentation();
        dto.recurringRoundBasedTimers = new String[recurringRoundBasedTimers.size()];
        for (int i = 0; i < recurringRoundBasedTimers.size(); i++) {
            dto.recurringRoundBasedTimers[i] =
                    RECURRING_ROUND_BASED_TIMER_HANDLER.write(recurringRoundBasedTimers.get(i));
        }

        return JSON.toJson(dto, RoundBasedTimerManagerDTO.class);
    }

    private static class RoundBasedTimerManagerDTO {
        String[] oneTimeRoundBasedTimers;
        String[] recurringRoundBasedTimers;
    }
}
