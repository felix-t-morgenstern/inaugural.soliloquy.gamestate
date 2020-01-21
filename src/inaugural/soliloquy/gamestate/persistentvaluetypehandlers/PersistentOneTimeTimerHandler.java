package inaugural.soliloquy.gamestate.persistentvaluetypehandlers;

import com.google.gson.Gson;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentTypeHandler;
import inaugural.soliloquy.gamestate.archetypes.OneTimeTimerArchetype;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.OneTimeTimer;
import soliloquy.specs.gamestate.factories.TimerFactory;

import java.util.function.Function;

public class PersistentOneTimeTimerHandler extends PersistentTypeHandler<OneTimeTimer> {
    private final TimerFactory TIMER_FACTORY;
    private final Function<String, Action> GET_ACTION;

    private static final OneTimeTimer ARCHETYPE = new OneTimeTimerArchetype();

    @SuppressWarnings("ConstantConditions")
    public PersistentOneTimeTimerHandler(TimerFactory timerFactory,
                                         Function<String, Action> getAction) {
        if (timerFactory == null) {
            throw new IllegalArgumentException(
                    "PersistentOneTimeTimerHandler: timerFactory cannot be null");
        }
        TIMER_FACTORY = timerFactory;
        if (getAction == null) {
            throw new IllegalArgumentException(
                    "PersistentOneTimeTimerHandler: getAction cannot be null");
        }
        GET_ACTION = getAction;
    }

    @Override
    public OneTimeTimer getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public OneTimeTimer read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "PersistentOneTimeTimerHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException(
                    "PersistentOneTimeTimerHandler.read: data cannot be empty");
        }
        OneTimeTimerDTO dto = new Gson().fromJson(data, OneTimeTimerDTO.class);
        return TIMER_FACTORY.makeOneTimeTimer(dto.id, GET_ACTION.apply(dto.actionId), dto.round);
    }

    @Override
    public String write(OneTimeTimer oneTimeTimer) {
        if (oneTimeTimer == null) {
            throw new IllegalArgumentException(
                    "PersistentOneTimeTimerHandler.write: oneTimeTimer cannot be null");
        }
        OneTimeTimerDTO dto = new OneTimeTimerDTO();
        dto.id = oneTimeTimer.id();
        dto.actionId = oneTimeTimer.action().id();
        dto.round = oneTimeTimer.getRoundWhenGoesOff();
        return new Gson().toJson(dto, OneTimeTimerDTO.class);
    }

    private class OneTimeTimerDTO {
        String id;
        String actionId;
        long round;
    }
}
