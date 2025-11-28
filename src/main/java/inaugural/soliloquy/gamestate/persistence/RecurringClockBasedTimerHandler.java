package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import java.util.function.Function;

public class RecurringClockBasedTimerHandler
        extends AbstractTypeHandler<RecurringClockBasedTimer> {
    private final ClockBasedTimerFactory CLOCK_BASED_TIMER_FACTORY;
    @SuppressWarnings("rawtypes")
    private final Function<String, Consumer> GET_CONSUMER;

    public RecurringClockBasedTimerHandler(ClockBasedTimerFactory clockBasedTimerFactory,
                                           @SuppressWarnings("rawtypes")
                                           Function<String, Consumer> getConsumer) {
        CLOCK_BASED_TIMER_FACTORY = Check.ifNull(clockBasedTimerFactory, "clockBasedTimerFactory");
        GET_CONSUMER = Check.ifNull(getConsumer, "getConsumer");
    }

    @SuppressWarnings("unchecked")
    @Override
    public RecurringClockBasedTimer read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        RecurringClockBasedTimerDTO dto = JSON.fromJson(data, RecurringClockBasedTimerDTO.class);

        var firingAction = GET_CONSUMER.apply(dto.consumerId);

        //noinspection unchecked
        return CLOCK_BASED_TIMER_FACTORY.make(dto.id, dto.periodDuration, dto.periodModuloOffset,
                firingAction, dto.fireMultipleTimesPerPeriodElapsed, dto.pausedTimestamp,
                dto.lastFiredTimestamp);
    }

    @Override
    public String write(RecurringClockBasedTimer recurringClockBasedTimer) {
        Check.ifNull(recurringClockBasedTimer, "recurringClockBasedTimer");

        RecurringClockBasedTimerDTO dto = new RecurringClockBasedTimerDTO();

        dto.id = recurringClockBasedTimer.id();
        dto.consumerId = recurringClockBasedTimer.consumerId();
        dto.periodDuration = recurringClockBasedTimer.periodDuration();
        dto.periodModuloOffset = recurringClockBasedTimer.periodModuloOffset();
        dto.fireMultipleTimesPerPeriodElapsed =
                recurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed();
        dto.pausedTimestamp = recurringClockBasedTimer.pausedTimestamp();
        dto.lastFiredTimestamp = recurringClockBasedTimer.lastFiringTimestamp();

        return JSON.toJson(dto);
    }

    private static class RecurringClockBasedTimerDTO {
        String id;
        String consumerId;
        int periodDuration;
        int periodModuloOffset;
        boolean fireMultipleTimesPerPeriodElapsed;
        Long pausedTimestamp;
        Long lastFiredTimestamp;
    }
}
