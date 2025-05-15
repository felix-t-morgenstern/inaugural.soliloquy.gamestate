package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeRoundBasedTimerImpl;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import java.util.function.Function;

// TODO: Consider refactoring out duplicated code between this and RecurringRoundBasedTimerHandler
public class OneTimeRoundBasedTimerHandler extends AbstractTypeHandler<OneTimeRoundBasedTimer> {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Action> GET_ACTION;

    public OneTimeRoundBasedTimerHandler(RoundBasedTimerFactory RoundBasedTimerFactory,
                                         @SuppressWarnings("rawtypes") Function<String, Action>
                                                 getAction) {
        TURN_BASED_TIMER_FACTORY = Check.ifNull(RoundBasedTimerFactory, "RoundBasedTimerFactory");
        GET_ACTION = Check.ifNull(getAction, "getAction");
    }

    @Override
    public String typeHandled() {
        return OneTimeRoundBasedTimerImpl.class.getCanonicalName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public OneTimeRoundBasedTimer read(String data) throws IllegalArgumentException {
        OneTimeTimerDTO dto =
                JSON.fromJson(Check.ifNullOrEmpty(data, "data"), OneTimeTimerDTO.class);
        return TURN_BASED_TIMER_FACTORY.makeOneTimeTimer(dto.id, GET_ACTION.apply(dto.actionId),
                dto.round, dto.priority);
    }

    @Override
    public String write(OneTimeRoundBasedTimer oneTimeTimer) {
        Check.ifNull(oneTimeTimer, "oneTimeTimer");
        OneTimeTimerDTO dto = new OneTimeTimerDTO();
        dto.id = oneTimeTimer.id();
        dto.actionId = oneTimeTimer.actionId();
        dto.round = oneTimeTimer.roundWhenGoesOff();
        dto.priority = oneTimeTimer.priority();
        return JSON.toJson(dto, OneTimeTimerDTO.class);
    }

    private static class OneTimeTimerDTO {
        String id;
        String actionId;
        int round;
        int priority;
    }
}
