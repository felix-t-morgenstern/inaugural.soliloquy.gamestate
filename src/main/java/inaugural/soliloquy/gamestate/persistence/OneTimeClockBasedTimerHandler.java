package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import java.util.function.Function;

public class OneTimeClockBasedTimerHandler extends AbstractTypeHandler<OneTimeClockBasedTimer> {
    private final ClockBasedTimerFactory FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Consumer> GET_CONSUMER;

    public OneTimeClockBasedTimerHandler(ClockBasedTimerFactory factory,
                                         @SuppressWarnings("rawtypes")
                                         Function<String, Consumer> getConsumer) {
        FACTORY = Check.ifNull(factory, "factory");
        GET_CONSUMER = Check.ifNull(getConsumer, "getConsumer");
    }

    @SuppressWarnings("unchecked")
    @Override
    public OneTimeClockBasedTimer read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        OneTimeClockBasedTimerDTO dto = JSON.fromJson(data, OneTimeClockBasedTimerDTO.class);

        var firingAction = GET_CONSUMER.apply(dto.consumerId);

        return FACTORY.make(dto.id, dto.firingTime, firingAction, dto.pausedTime);
    }

    @Override
    public String write(OneTimeClockBasedTimer oneTimeClockBasedTimer) {
        Check.ifNull(oneTimeClockBasedTimer, "oneTimeClockBasedTimer");

        OneTimeClockBasedTimerDTO dto = new OneTimeClockBasedTimerDTO();

        dto.id = oneTimeClockBasedTimer.id();
        dto.consumerId = oneTimeClockBasedTimer.consumerId();
        dto.firingTime = oneTimeClockBasedTimer.firingTime();
        dto.pausedTime = oneTimeClockBasedTimer.pausedTimestamp();

        return JSON.toJson(dto);
    }

    private static class OneTimeClockBasedTimerDTO {
        String id;
        String consumerId;
        long firingTime;
        Long pausedTime;
    }
}
