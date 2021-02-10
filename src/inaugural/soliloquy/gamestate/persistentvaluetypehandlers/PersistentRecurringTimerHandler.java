package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.gamestate.archetypes.RecurringTimerArchetype;
import inaugural.soliloquy.tools.persistentvaluetypehandlers.PersistentTypeHandler;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.RecurringTimer;
import soliloquy.specs.gamestate.factories.TimerFactory;

import java.util.function.Function;

public class PersistentRecurringTimerHandler extends PersistentTypeHandler<RecurringTimer> {
    private final TimerFactory TIMER_FACTORY;
    private final Function<String, Action> GET_ACTION;

    private static final RecurringTimer ARCHETYPE = new RecurringTimerArchetype();

    @SuppressWarnings("ConstantConditions")
    public PersistentRecurringTimerHandler(TimerFactory timerFactory,
                                         Function<String, Action> getAction) {
        if (timerFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentRecurringTimerHandler: timerFactory cannot be null");
        }
        TIMER_FACTORY = timerFactory;
        if (getAction == null) {
            throw new IllegalArgumentException(
                    "PersistentRecurringTimerHandler: getAction cannot be null");
        }
        GET_ACTION = getAction;
    }
    @Override
    public RecurringTimer getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public RecurringTimer read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "PersistentRecurringTimerHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentRecurringTimerHandler.read: data cannot be empty");
        }
        RecurringTimerDTO dto = new Gson().fromJson(data, RecurringTimerDTO.class);
        return TIMER_FACTORY.makeRecurringTimer(dto.id, GET_ACTION.apply(dto.actionId),
                dto.roundModulo, dto.roundOffset);
    }

    @Override
    public String write(RecurringTimer recurringTimer) {
        if (recurringTimer == null) {
            throw new IllegalArgumentException(
                    "PersistentRecurringTimerHandler.write: recurringTimer cannot be null");
        }
        RecurringTimerDTO dto = new RecurringTimerDTO();
        dto.id = recurringTimer.id();
        dto.actionId = recurringTimer.action().id();
        dto.roundModulo = recurringTimer.getRoundModulo();
        dto.roundOffset = recurringTimer.getRoundOffset();
        return new Gson().toJson(dto, RecurringTimerDTO.class);
    }

    private class RecurringTimerDTO {
        String id;
        String actionId;
        int roundModulo;
        int roundOffset;
    }
}
