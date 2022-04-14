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
                                         @SuppressWarnings("rawtypes") Function<String, Action>
                                                 getAction) {
        super(new OneTimeClockBasedTimerArchetype());
        CLOCK_BASED_TIMER_FACTORY = clockBasedTimerFactory;
        GET_ACTION = getAction;
    }

    @Override
    public OneTimeClockBasedTimer read(String data) throws IllegalArgumentException {
        Check.ifNullOrEmpty(data, "data");

        OneTimeClockBasedTimerDTO dto = GSON.fromJson(data, OneTimeClockBasedTimerDTO.class);

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

        return GSON.toJson(dto);
    }

    private static class OneTimeClockBasedTimerDTO {
        String id;
        String actionId;
        long firingTime;
        Long pausedTime;
        Long mostRecentTimestamp;
    }

    private static class OneTimeClockBasedTimerArchetype implements OneTimeClockBasedTimer {

        @Override
        public long firingTime() {
            return 0;
        }

        @Override
        public String actionId() {
            return null;
        }

        @Override
        public void fire(long l) throws UnsupportedOperationException, IllegalArgumentException {

        }

        @Override
        public void reportPause(long l) throws IllegalArgumentException {

        }

        @Override
        public void reportUnpause(long l) throws IllegalArgumentException {

        }

        @Override
        public Long pausedTimestamp() {
            return null;
        }

        @Override
        public Long mostRecentTimestamp() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return OneTimeClockBasedTimer.class.getCanonicalName();
        }

        @Override
        public String id() throws IllegalStateException {
            return null;
        }
    }
}
