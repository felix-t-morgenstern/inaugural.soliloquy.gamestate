package inaugural.soliloquy.gamestate.persistence;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.persistence.AbstractTypeHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

import java.util.List;

public class ClockBasedTimerManagerHandler extends AbstractTypeHandler<ClockBasedTimerManager> {
    private final ClockBasedTimerManager CLOCK_BASED_TIMER_MANAGER;
    private final TypeHandler<OneTimeClockBasedTimer> ONE_TIME_CLOCK_BASED_TIMER_HANDLER;
    private final TypeHandler<RecurringClockBasedTimer> RECURRING_CLOCK_BASED_TIMER_HANDLER;

    public ClockBasedTimerManagerHandler(ClockBasedTimerManager clockBasedTimerManager,
                                         TypeHandler<OneTimeClockBasedTimer>
                                                 oneTimeClockBasedTimerHandler,
                                         TypeHandler<RecurringClockBasedTimer>
                                                 recurringClockBasedTimerHandler) {
        super(new ClockBasedTimerManagerArchetype());
        CLOCK_BASED_TIMER_MANAGER = Check.ifNull(clockBasedTimerManager, "clockBasedTimerManager");
        ONE_TIME_CLOCK_BASED_TIMER_HANDLER = Check.ifNull(oneTimeClockBasedTimerHandler,
                "oneTimeClockBasedTimerHandler");
        RECURRING_CLOCK_BASED_TIMER_HANDLER = Check.ifNull(recurringClockBasedTimerHandler,
                "recurringClockBasedTimerHandler");
    }

    @Override
    public ClockBasedTimerManager read(String data) throws IllegalArgumentException {
        CLOCK_BASED_TIMER_MANAGER.clear();

        ClockBasedTimerManagerDTO dto = JSON.fromJson(Check.ifNullOrEmpty(data, "data"),
                ClockBasedTimerManagerDTO.class);

        for (String oneTimeClockBasedTimerDTO : dto.oneTimeClockBasedTimers) {
            CLOCK_BASED_TIMER_MANAGER.registerOneTimeTimer(
                    ONE_TIME_CLOCK_BASED_TIMER_HANDLER.read(oneTimeClockBasedTimerDTO));
        }

        for (String recurringClockBasedTimerDTO : dto.recurringClockBasedTimers) {
            CLOCK_BASED_TIMER_MANAGER.registerRecurringTimer(
                    RECURRING_CLOCK_BASED_TIMER_HANDLER.read(recurringClockBasedTimerDTO));
        }

        return null;
    }

    @Override
    public String write(ClockBasedTimerManager clockBasedTimerManager) {
        if (clockBasedTimerManager != null) {
            throw new IllegalArgumentException("ClockBasedTimerManagerHandler.write: " +
                    "clockBasedTimerManager must be null");
        }

        ClockBasedTimerManagerDTO dto = new ClockBasedTimerManagerDTO();

        List<OneTimeClockBasedTimer> oneTimeClockBasedTimers =
                CLOCK_BASED_TIMER_MANAGER.oneTimeTimersRepresentation();
        dto.oneTimeClockBasedTimers = new String[oneTimeClockBasedTimers.size()];
        for (int i = 0; i < oneTimeClockBasedTimers.size(); i++) {
            dto.oneTimeClockBasedTimers[i] =
                    ONE_TIME_CLOCK_BASED_TIMER_HANDLER.write(oneTimeClockBasedTimers.get(i));
        }

        List<RecurringClockBasedTimer> recurringClockBasedTimers =
                CLOCK_BASED_TIMER_MANAGER.recurringTimersRepresentation();
        dto.recurringClockBasedTimers = new String[recurringClockBasedTimers.size()];
        for (int i = 0; i < recurringClockBasedTimers.size(); i++) {
            dto.recurringClockBasedTimers[i] =
                    RECURRING_CLOCK_BASED_TIMER_HANDLER.write(recurringClockBasedTimers.get(i));
        }

        return JSON.toJson(dto);
    }

    private static class ClockBasedTimerManagerDTO {
        String[] oneTimeClockBasedTimers;
        String[] recurringClockBasedTimers;
    }

    private static class ClockBasedTimerManagerArchetype implements ClockBasedTimerManager {

        @Override
        public void registerOneTimeTimer(OneTimeClockBasedTimer oneTimeClockBasedTimer)
                throws IllegalArgumentException {

        }

        @Override
        public void registerRecurringTimer(RecurringClockBasedTimer recurringClockBasedTimer)
                throws IllegalArgumentException {

        }

        @Override
        public void deregisterOneTimeTimer(String s) throws IllegalArgumentException {

        }

        @Override
        public void deregisterRecurringTimer(String s) throws IllegalArgumentException {

        }

        @Override
        public void clear() {

        }

        @Override
        public void fireTimers(long l) throws IllegalArgumentException {

        }

        @Override
        public List<OneTimeClockBasedTimer> oneTimeTimersRepresentation() {
            return null;
        }

        @Override
        public List<RecurringClockBasedTimer> recurringTimersRepresentation() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return ClockBasedTimerManager.class.getCanonicalName();
        }
    }
}
