package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.gamestate.archetypes.OneTimeTurnBasedTimerArchetype;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeTurnBasedTimer;
import soliloquy.specs.gamestate.factories.TurnBasedTimerFactory;

import java.util.function.Function;

// TODO: Consider refactoring out duplicated code between this and PersistentRecurringTurnBasedTimerHandler
public class PersistentOneTimeTimerHandler extends PersistentTypeHandler<OneTimeTurnBasedTimer> {
    private final TurnBasedTimerFactory TURN_BASED_TIMER_FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Action> GET_ACTION;

    private static final OneTimeTurnBasedTimer ARCHETYPE = new OneTimeTurnBasedTimerArchetype();

    public PersistentOneTimeTimerHandler(TurnBasedTimerFactory turnBasedTimerFactory,
                                         @SuppressWarnings("rawtypes") Function<String, Action>
                                                 getAction) {
        TURN_BASED_TIMER_FACTORY = Check.ifNull(turnBasedTimerFactory, "turnBasedTimerFactory");
        GET_ACTION = Check.ifNull(getAction, "getAction");
    }

    @Override
    public OneTimeTurnBasedTimer getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public OneTimeTurnBasedTimer read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "PersistentOneTimeTimerHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentOneTimeTimerHandler.read: data cannot be empty");
        }
        OneTimeTimerDTO dto = new Gson().fromJson(data, OneTimeTimerDTO.class);
        return TURN_BASED_TIMER_FACTORY.makeOneTimeTimer(dto.id, GET_ACTION.apply(dto.actionId),
                dto.round, dto.priority);
    }

    @Override
    public String write(OneTimeTurnBasedTimer oneTimeTimer) {
        if (oneTimeTimer == null) {
            throw new IllegalArgumentException(
                    "PersistentOneTimeTimerHandler.write: oneTimeTimer cannot be null");
        }
        OneTimeTimerDTO dto = new OneTimeTimerDTO();
        dto.id = oneTimeTimer.id();
        dto.actionId = oneTimeTimer.action().id();
        dto.round = oneTimeTimer.roundWhenGoesOff();
        dto.priority = oneTimeTimer.priority();
        return new Gson().toJson(dto, OneTimeTimerDTO.class);
    }

    private static class OneTimeTimerDTO {
        String id;
        String actionId;
        long round;
        int priority;
    }
}
