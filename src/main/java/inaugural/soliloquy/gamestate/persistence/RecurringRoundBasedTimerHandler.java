package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.gamestate.entities.timers.RecurringRoundBasedTimerImpl;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import java.util.function.Function;

public class RecurringRoundBasedTimerHandler extends AbstractTypeHandler<RecurringRoundBasedTimer> {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Action> GET_ACTION;

    public RecurringRoundBasedTimerHandler(RoundBasedTimerFactory RoundBasedTimerFactory,
                                           @SuppressWarnings("rawtypes")
                                                   Function<String, Action> getAction) {
        TURN_BASED_TIMER_FACTORY = Check.ifNull(RoundBasedTimerFactory, "RoundBasedTimerFactory");
        GET_ACTION = Check.ifNull(getAction, "getAction");
    }

    @Override
    public String typeHandled() {
        return RecurringRoundBasedTimerImpl.class.getCanonicalName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public RecurringRoundBasedTimer read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "RecurringRoundBasedTimerHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException(
                    "RecurringRoundBasedTimerHandler.read: data cannot be empty");
        }
        RecurringTimerDTO dto = JSON.fromJson(data, RecurringTimerDTO.class);
        return TURN_BASED_TIMER_FACTORY.makeRecurringTimer(dto.id, GET_ACTION.apply(dto.actionId),
                dto.roundModulo, dto.roundOffset, dto.priority);
    }

    @Override
    public String write(RecurringRoundBasedTimer recurringRoundBasedTimer) {
        Check.ifNull(recurringRoundBasedTimer, "recurringRoundBasedTimer");
        RecurringTimerDTO dto = new RecurringTimerDTO();
        dto.id = recurringRoundBasedTimer.id();
        dto.actionId = recurringRoundBasedTimer.actionId();
        dto.roundModulo = recurringRoundBasedTimer.roundModulo();
        dto.roundOffset = recurringRoundBasedTimer.roundOffset();
        dto.priority = recurringRoundBasedTimer.priority();
        return JSON.toJson(dto, RecurringTimerDTO.class);
    }

    private static class RecurringTimerDTO {
        String id;
        String actionId;
        int roundModulo;
        int roundOffset;
        int priority;
    }
}
