package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import java.util.function.Function;

public class OneTimeClockBasedTimerHandler extends AbstractTypeHandler<OneTimeClockBasedTimer> {
    private final ClockBasedTimerFactory CLOCK_BASED_TIMER_FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Action> GET_ACTION;

    public OneTimeClockBasedTimerHandler(ClockBasedTimerFactory clockBasedTimerFactory,
                                         @SuppressWarnings("rawtypes")
                                         Function<String, Action> getAction) {
        CLOCK_BASED_TIMER_FACTORY = clockBasedTimerFactory;
        GET_ACTION = getAction;
    }

    @SuppressWarnings("unchecked")
    @Override
    public OneTimeClockBasedTimer read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        OneTimeClockBasedTimerDTO dto = JSON.fromJson(data, OneTimeClockBasedTimerDTO.class);

        @SuppressWarnings("rawtypes") Action firingAction = GET_ACTION.apply(dto.actionId);

        //noinspection unchecked
        return CLOCK_BASED_TIMER_FACTORY.make(dto.id, dto.firingTime, firingAction, dto.pausedTime,
                dto.mostRecentTimestamp);
    }

    @Override
    public String write(OneTimeClockBasedTimer oneTimeClockBasedTimer) {
        Check.ifNull(oneTimeClockBasedTimer, "oneTimeClockBasedTimer");

        OneTimeClockBasedTimerDTO dto = new OneTimeClockBasedTimerDTO();

        dto.id = oneTimeClockBasedTimer.id();
        dto.actionId = oneTimeClockBasedTimer.actionId();
        dto.firingTime = oneTimeClockBasedTimer.firingTime();
        dto.pausedTime = oneTimeClockBasedTimer.pausedTimestamp();
        dto.mostRecentTimestamp = oneTimeClockBasedTimer.mostRecentTimestamp();

        return JSON.toJson(dto);
    }

    private static class OneTimeClockBasedTimerDTO {
        String id;
        String actionId;
        long firingTime;
        Long pausedTime;
        Long mostRecentTimestamp;
    }
}
