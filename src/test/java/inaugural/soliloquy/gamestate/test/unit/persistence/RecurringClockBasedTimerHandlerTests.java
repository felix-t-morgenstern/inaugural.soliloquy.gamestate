package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RecurringClockBasedTimerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RecurringClockBasedTimerHandlerTests {
    private final String ID = "id";
    private final String ACTION_ID = "actionId";
    @SuppressWarnings("rawtypes")
    private final HashMap<String, Action> ACTIONS = new HashMap<>();
    private final int PERIOD_DURATION = 123;
    private final int PERIOD_MODULO_OFFSET = 456;
    private final boolean FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED = true;
    private final Long PAUSED_TIMESTAMP = 123123L;
    private final Long LAST_FIRED_TIMESTAMP = 456456L;
    private final Long MOST_RECENT_TIMESTAMP = 789789L;

    @SuppressWarnings("rawtypes")
    @Mock
    private Action _mockAction;

    @Mock
    private ClockBasedTimerFactory _mockClockBasedTimerFactory;

    @Mock
    private RecurringClockBasedTimer _mockRecurringClockBasedTimer;

    private TypeHandler<RecurringClockBasedTimer> _recurringClockBasedTimerHandler;

    private final String WRITTEN_VALUE =
            "{\"id\":\"id\",\"actionId\":\"actionId\",\"periodDuration\":123," +
                    "\"periodModuloOffset\":456,\"fireMultipleTimesPerPeriodElapsed\":true," +
                    "\"pausedTimestamp\":123123,\"lastFiredTimestamp\":456456," +
                    "\"mostRecentTimestamp\":789789}";

    @BeforeEach
    void setUp() {
        _mockAction = mock(Action.class);

        ACTIONS.put(ACTION_ID, _mockAction);

        _mockRecurringClockBasedTimer = mock(RecurringClockBasedTimer.class);
        when(_mockRecurringClockBasedTimer.id()).thenReturn(ID);
        when(_mockRecurringClockBasedTimer.actionId()).thenReturn(ACTION_ID);
        when(_mockRecurringClockBasedTimer.periodDuration()).thenReturn(PERIOD_DURATION);
        when(_mockRecurringClockBasedTimer.periodModuloOffset()).thenReturn(PERIOD_MODULO_OFFSET);
        when(_mockRecurringClockBasedTimer.fireMultipleTimesForMultiplePeriodsElapsed())
                .thenReturn(FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED);
        when(_mockRecurringClockBasedTimer.pausedTimestamp()).thenReturn(PAUSED_TIMESTAMP);
        when(_mockRecurringClockBasedTimer.lastFiringTimestamp()).thenReturn(LAST_FIRED_TIMESTAMP);
        when(_mockRecurringClockBasedTimer.mostRecentTimestamp())
                .thenReturn(MOST_RECENT_TIMESTAMP);

        _mockClockBasedTimerFactory = mock(ClockBasedTimerFactory.class);
        when(_mockClockBasedTimerFactory.make(anyString(), anyInt(), anyInt(), any(), anyBoolean(),
                anyLong(), anyLong(), anyLong())).thenReturn(_mockRecurringClockBasedTimer);

        _recurringClockBasedTimerHandler =
                new RecurringClockBasedTimerHandler(_mockClockBasedTimerFactory, ACTIONS::get);
    }

    @Test
    void testWrite() {
        String result = _recurringClockBasedTimerHandler.write(_mockRecurringClockBasedTimer);

        assertEquals(WRITTEN_VALUE, result);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _recurringClockBasedTimerHandler.write(null));
    }

    @Test
    void testRead() {
        RecurringClockBasedTimer result = _recurringClockBasedTimerHandler.read(WRITTEN_VALUE);

        assertSame(_mockRecurringClockBasedTimer, result);
        //noinspection unchecked
        verify(_mockClockBasedTimerFactory).make(ID, PERIOD_DURATION, PERIOD_MODULO_OFFSET,
                _mockAction, FIRE_MULTIPLE_TIMES_FOR_MULTIPLE_PERIODS_ELAPSED, PAUSED_TIMESTAMP,
                LAST_FIRED_TIMESTAMP, MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _recurringClockBasedTimerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                _recurringClockBasedTimerHandler.read(""));
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_recurringClockBasedTimerHandler.getArchetype());
        assertEquals(RecurringClockBasedTimer.class.getCanonicalName(),
                _recurringClockBasedTimerHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        RecurringClockBasedTimer.class.getCanonicalName() + ">",
                _recurringClockBasedTimerHandler.getInterfaceName());
    }
}
