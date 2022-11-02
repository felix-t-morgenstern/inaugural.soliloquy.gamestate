package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RoundBasedTimerManagerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RoundBasedTimerManagerHandlerTests {
    private final String ONE_TIME_ROUND_BASED_TIMER = "oneTimeRoundBasedTimer";
    private final String RECURRING_ROUND_BASED_TIMER = "recurringRoundBasedTimer";

    @Mock private OneTimeRoundBasedTimer mockOneTimeRoundBasedTimer;
    @Mock private RecurringRoundBasedTimer mockRecurringRoundBasedTimer;
    @Mock private TypeHandler<OneTimeRoundBasedTimer> mockOneTimeRoundBasedTimerHandler;
    @Mock private TypeHandler<RecurringRoundBasedTimer> mockRecurringRoundBasedTimerHandler;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;

    private TypeHandler<RoundBasedTimerManager> roundBasedTimerManagerHandler;

    private final String DATA = "{\"oneTimeRoundBasedTimers\":[\"oneTimeRoundBasedTimer\"]," +
            "\"recurringRoundBasedTimers\":[\"recurringRoundBasedTimer\"]}";

    @BeforeEach
    void setUp() {
        mockOneTimeRoundBasedTimer = mock(OneTimeRoundBasedTimer.class);
        mockRecurringRoundBasedTimer = mock(RecurringRoundBasedTimer.class);
        //noinspection unchecked
        mockOneTimeRoundBasedTimerHandler = mock(TypeHandler.class);
        when(mockOneTimeRoundBasedTimerHandler.read(anyString()))
                .thenReturn(mockOneTimeRoundBasedTimer);
        when(mockOneTimeRoundBasedTimerHandler.write(any())).thenReturn(ONE_TIME_ROUND_BASED_TIMER);
        //noinspection unchecked
        mockRecurringRoundBasedTimerHandler = mock(TypeHandler.class);
        when(mockRecurringRoundBasedTimerHandler.read(anyString()))
                .thenReturn(mockRecurringRoundBasedTimer);
        when(mockRecurringRoundBasedTimerHandler.write(any()))
                .thenReturn(RECURRING_ROUND_BASED_TIMER);
        mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);
        when(mockRoundBasedTimerManager.oneTimeRoundBasedTimersRepresentation()).thenReturn(
                new ArrayList<>() {{
                    add(mockOneTimeRoundBasedTimer);
                }});
        when(mockRoundBasedTimerManager.recurringRoundBasedTimersRepresentation()).thenReturn(
                new ArrayList<>() {{
                    add(mockRecurringRoundBasedTimer);
                }});

        roundBasedTimerManagerHandler =
                new RoundBasedTimerManagerHandler(mockRoundBasedTimerManager,
                        mockOneTimeRoundBasedTimerHandler, mockRecurringRoundBasedTimerHandler);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoundBasedTimerManagerHandler(null, mockOneTimeRoundBasedTimerHandler,
                        mockRecurringRoundBasedTimerHandler));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundBasedTimerManagerHandler(mockRoundBasedTimerManager, null,
                        mockRecurringRoundBasedTimerHandler));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundBasedTimerManagerHandler(mockRoundBasedTimerManager,
                        mockOneTimeRoundBasedTimerHandler, null));
    }

    @Test
    void testWrite() {
        String writtenValue = roundBasedTimerManagerHandler.write(mockRoundBasedTimerManager);

        assertEquals(DATA, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> roundBasedTimerManagerHandler.write(null));
    }

    @Test
    void testRead() {
        RoundBasedTimerManager output = roundBasedTimerManagerHandler.read(DATA);

        assertNull(output);
        verify(mockRoundBasedTimerManager, times(1)).clear();
        verify(mockOneTimeRoundBasedTimerHandler, times(1)).read(ONE_TIME_ROUND_BASED_TIMER);
        verify(mockRoundBasedTimerManager, times(1)).registerOneTimeRoundBasedTimer(
                mockOneTimeRoundBasedTimer);
        verify(mockRecurringRoundBasedTimerHandler, times(1)).read(RECURRING_ROUND_BASED_TIMER);
        verify(mockRoundBasedTimerManager, times(1)).registerRecurringRoundBasedTimer(
                mockRecurringRoundBasedTimer);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        RoundBasedTimerManager.class.getCanonicalName() + ">",
                roundBasedTimerManagerHandler.getInterfaceName());
    }
}
