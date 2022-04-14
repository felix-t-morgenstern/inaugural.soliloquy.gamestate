package inaugural.soliloquy.gamestate.persistence;

import com.google.gson.Gson;
import inaugural.soliloquy.gamestate.archetypes.RecurringTurnBasedTimerArchetype;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.RecurringTurnBasedTimer;
import soliloquy.specs.gamestate.factories.TurnBasedTimerFactory;

import java.util.function.Function;

public class RecurringTurnBasedTimerHandler extends AbstractTypeHandler<RecurringTurnBasedTimer> {
    private final TurnBasedTimerFactory TURN_BASED_TIMER_FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Action> GET_ACTION;

    private static final RecurringTurnBasedTimer ARCHETYPE =
            new RecurringTurnBasedTimerArchetype();

    public RecurringTurnBasedTimerHandler(TurnBasedTimerFactory turnBasedTimerFactory,
                                          @SuppressWarnings("rawtypes")
                                                            Function<String, Action> getAction) {
        super(ARCHETYPE);
        TURN_BASED_TIMER_FACTORY = Check.ifNull(turnBasedTimerFactory, "turnBasedTimerFactory");
        GET_ACTION = Check.ifNull(getAction, "getAction");
    }

    @Override
    public RecurringTurnBasedTimer read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "RecurringTurnBasedTimerHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException(
                    "RecurringTurnBasedTimerHandler.read: data cannot be empty");
        }
        RecurringTimerDTO dto = new Gson().fromJson(data, RecurringTimerDTO.class);
        return TURN_BASED_TIMER_FACTORY.makeRecurringTimer(dto.id, GET_ACTION.apply(dto.actionId),
                dto.roundModulo, dto.roundOffset, dto.priority);
    }

    @Override
    public String write(RecurringTurnBasedTimer recurringTurnBasedTimer) {
        Check.ifNull(recurringTurnBasedTimer, "recurringTurnBasedTimer");
        RecurringTimerDTO dto = new RecurringTimerDTO();
        dto.id = recurringTurnBasedTimer.id();
        dto.actionId = recurringTurnBasedTimer.action().id();
        dto.roundModulo = recurringTurnBasedTimer.roundModulo();
        dto.roundOffset = recurringTurnBasedTimer.roundOffset();
        dto.priority = recurringTurnBasedTimer.priority();
        return new Gson().toJson(dto, RecurringTimerDTO.class);
    }

    private static class RecurringTimerDTO {
        String id;
        String actionId;
        int roundModulo;
        int roundOffset;
        int priority;
    }
}
