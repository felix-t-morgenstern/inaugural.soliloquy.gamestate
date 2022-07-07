package inaugural.soliloquy.gamestate.persistence;

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
        super(new OneTimeRoundBasedTimerArchetype());
        TURN_BASED_TIMER_FACTORY = Check.ifNull(RoundBasedTimerFactory, "RoundBasedTimerFactory");
        GET_ACTION = Check.ifNull(getAction, "getAction");
    }

    @Override
    public OneTimeRoundBasedTimer read(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "OneTimeRoundBasedTimerHandler.read: data cannot be null");
        }
        if (data.equals("")) {
            throw new IllegalArgumentException(
                    "OneTimeRoundBasedTimerHandler.read: data cannot be empty");
        }
        OneTimeTimerDTO dto = JSON.fromJson(data, OneTimeTimerDTO.class);
        return TURN_BASED_TIMER_FACTORY.makeOneTimeTimer(dto.id, GET_ACTION.apply(dto.actionId),
                dto.round, dto.priority);
    }

    @Override
    public String write(OneTimeRoundBasedTimer oneTimeTimer) {
        if (oneTimeTimer == null) {
            throw new IllegalArgumentException(
                    "OneTimeRoundBasedTimerHandler.write: oneTimeTimer cannot be null");
        }
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

    private static class OneTimeRoundBasedTimerArchetype implements OneTimeRoundBasedTimer {

        @Override
        public int roundWhenGoesOff() {
            return 0;
        }

        @Override
        public String id() throws IllegalStateException {
            return null;
        }

        @Override
        public void delete() throws IllegalStateException {

        }

        @Override
        public boolean isDeleted() {
            return false;
        }

        @Override
        public int priority() {
            return 0;
        }

        @Override
        public void run() {

        }

        @Override
        public String getInterfaceName() {
            return OneTimeRoundBasedTimer.class.getCanonicalName();
        }

        @Override
        public String actionId() {
            return null;
        }
    }
}
