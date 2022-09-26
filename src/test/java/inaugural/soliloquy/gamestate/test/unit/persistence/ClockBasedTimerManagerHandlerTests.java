package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.ClockBasedTimerManagerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ClockBasedTimerManagerHandlerTests {
    private final String ONE_TIME_CLOCK_BASED_TIMER_ID = "oneTimeClockBasedTimerId";

    private final String RECURRING_CLOCK_BASED_TIMER_ID = "recurringClockBasedTimerId";

    private final String ONE_TIME_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA =
            "oneTimeClockBasedTimerMockWrittenData";
    private final String RECURRING_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA =
            "recurringClockBasedTimerMockWrittenData";

    @Mock
    private ClockBasedTimerManager _mockClockBasedTimerManager;

    @Mock
    private TypeHandler<OneTimeClockBasedTimer> _mockOneTimeClockBasedTimerHandler;
    @Mock
    private TypeHandler<RecurringClockBasedTimer> _mockRecurringClockBasedTimerHandler;

    @Mock
    private OneTimeClockBasedTimer _mockOneTimeClockBasedTimer;
    @Mock
    private RecurringClockBasedTimer _mockRecurringClockBasedTimer;

    private TypeHandler<ClockBasedTimerManager> _clockBasedTimerManagerHandler;

    private final String WRITTEN_DATA =
            "{\"oneTimeClockBasedTimers\":[\"oneTimeClockBasedTimerMockWrittenData\"]," +
                    "\"recurringClockBasedTimers\":[\"recurringClockBasedTimerMockWrittenData\"]}";

    @BeforeEach
    void setUp() {
        _mockOneTimeClockBasedTimer = mock(OneTimeClockBasedTimer.class);
        when(_mockOneTimeClockBasedTimer.id()).thenReturn(ONE_TIME_CLOCK_BASED_TIMER_ID);

        _mockRecurringClockBasedTimer = mock(RecurringClockBasedTimer.class);
        when(_mockRecurringClockBasedTimer.id()).thenReturn(RECURRING_CLOCK_BASED_TIMER_ID);

        //noinspection unchecked
        _mockOneTimeClockBasedTimerHandler = mock(TypeHandler.class);
        when(_mockOneTimeClockBasedTimerHandler.read(anyString()))
                .thenReturn(_mockOneTimeClockBasedTimer);
        when(_mockOneTimeClockBasedTimerHandler.write(any()))
                .thenReturn(ONE_TIME_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);

        //noinspection unchecked
        _mockRecurringClockBasedTimerHandler = mock(TypeHandler.class);
        when(_mockRecurringClockBasedTimerHandler.read(anyString()))
                .thenReturn(_mockRecurringClockBasedTimer);
        when(_mockRecurringClockBasedTimerHandler.write(any()))
                .thenReturn(RECURRING_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);

        _mockClockBasedTimerManager = mock(ClockBasedTimerManager.class);

        _clockBasedTimerManagerHandler =
                new ClockBasedTimerManagerHandler(_mockClockBasedTimerManager,
                        _mockOneTimeClockBasedTimerHandler, _mockRecurringClockBasedTimerHandler);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ClockBasedTimerManagerHandler(
                null, _mockOneTimeClockBasedTimerHandler,
                _mockRecurringClockBasedTimerHandler
        ));
        assertThrows(IllegalArgumentException.class, () -> new ClockBasedTimerManagerHandler(
                _mockClockBasedTimerManager, null,
                _mockRecurringClockBasedTimerHandler
        ));
        assertThrows(IllegalArgumentException.class, () -> new ClockBasedTimerManagerHandler(
                _mockClockBasedTimerManager, _mockOneTimeClockBasedTimerHandler,
                null
        ));
    }

    @Test
    void testWrite() {
        when(_mockClockBasedTimerManager.oneTimeTimersRepresentation())
                .thenReturn(new ArrayList<>() {{
                    add(_mockOneTimeClockBasedTimer);
                }});
        when(_mockClockBasedTimerManager.recurringTimersRepresentation())
                .thenReturn(new ArrayList<>() {{
                    add(_mockRecurringClockBasedTimer);
                }});

        String writtenValue = _clockBasedTimerManagerHandler.write(null);

        assertEquals(WRITTEN_DATA, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerManagerHandler.write(_mockClockBasedTimerManager));
    }

    @Test
    void testRead() {
        InOrder inOrder = inOrder(_mockClockBasedTimerManager);

        ClockBasedTimerManager unusedValue = _clockBasedTimerManagerHandler.read(WRITTEN_DATA);

        assertNull(unusedValue);
        // Test whether EXISTING manager received values, AND whether it cleared out OLD values
        inOrder.verify(_mockClockBasedTimerManager).clear();
        inOrder.verify(_mockClockBasedTimerManager)
                .registerOneTimeTimer(_mockOneTimeClockBasedTimer);
        inOrder.verify(_mockClockBasedTimerManager)
                .registerRecurringTimer(_mockRecurringClockBasedTimer);

        verify(_mockOneTimeClockBasedTimerHandler)
                .read(ONE_TIME_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);
        verify(_mockRecurringClockBasedTimerHandler)
                .read(RECURRING_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerManagerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                _clockBasedTimerManagerHandler.read(""));
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_clockBasedTimerManagerHandler.getArchetype());
        assertEquals(ClockBasedTimerManager.class.getCanonicalName(),
                _clockBasedTimerManagerHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        ClockBasedTimerManager.class.getCanonicalName() + ">",
                _clockBasedTimerManagerHandler.getInterfaceName());
    }
}
