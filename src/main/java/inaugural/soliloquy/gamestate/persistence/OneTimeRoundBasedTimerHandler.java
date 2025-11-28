package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.factories.RoundBasedTimerFactory;

import java.util.function.Function;

// TODO: Consider refactoring out duplicated code between this and RecurringRoundBasedTimerHandler
public class OneTimeRoundBasedTimerHandler extends AbstractTypeHandler<OneTimeRoundBasedTimer> {
    private final RoundBasedTimerFactory TURN_BASED_TIMER_FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Consumer> GET_CONSUMER;

    public OneTimeRoundBasedTimerHandler(RoundBasedTimerFactory RoundBasedTimerFactory,
                                         @SuppressWarnings("rawtypes")
                                         Function<String, Consumer> getConsumer) {
        TURN_BASED_TIMER_FACTORY = Check.ifNull(RoundBasedTimerFactory, "RoundBasedTimerFactory");
        GET_CONSUMER = Check.ifNull(getConsumer, "getConsumer");
    }

    @SuppressWarnings("unchecked")
    @Override
    public OneTimeRoundBasedTimer read(String data) throws IllegalArgumentException {
        OneTimeTimerDTO dto =
                JSON.fromJson(Check.ifNullOrEmpty(data, "data"), OneTimeTimerDTO.class);
        return TURN_BASED_TIMER_FACTORY.makeOneTimeTimer(dto.id, GET_CONSUMER.apply(dto.consumerId),
                dto.round, dto.priority);
    }

    @Override
    public String write(OneTimeRoundBasedTimer oneTimeTimer) {
        Check.ifNull(oneTimeTimer, "oneTimeTimer");
        OneTimeTimerDTO dto = new OneTimeTimerDTO();
        dto.id = oneTimeTimer.id();
        dto.consumerId = oneTimeTimer.consumerId();
        dto.round = oneTimeTimer.roundWhenGoesOff();
        dto.priority = oneTimeTimer.priority();
        return JSON.toJson(dto, OneTimeTimerDTO.class);
    }

    private static class OneTimeTimerDTO {
        String id;
        String consumerId;
        int round;
        int priority;
    }
}
