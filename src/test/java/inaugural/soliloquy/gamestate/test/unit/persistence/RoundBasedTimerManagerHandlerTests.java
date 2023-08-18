package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RoundBasedTimerManagerHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoundBasedTimerManagerHandlerTests {
    private final String ONE_TIME_ROUND_BASED_TIMER = randomString();
    private final String RECURRING_ROUND_BASED_TIMER = randomString();

    @Mock private OneTimeRoundBasedTimer mockOneTimeRoundBasedTimer;
    @Mock private RecurringRoundBasedTimer mockRecurringRoundBasedTimer;
    @Mock private TypeHandler<OneTimeRoundBasedTimer> mockOneTimeRoundBasedTimerHandler;
    @Mock private TypeHandler<RecurringRoundBasedTimer> mockRecurringRoundBasedTimerHandler;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;

    private TypeHandler<RoundBasedTimerManager> handler;

    private final String DATA = String.format(
            "{\"oneTimeRoundBasedTimers\":[\"%s\"],\"recurringRoundBasedTimers\":[\"%s\"]}",
            ONE_TIME_ROUND_BASED_TIMER, RECURRING_ROUND_BASED_TIMER);

    @Before
    public void setUp() {
        when(mockOneTimeRoundBasedTimerHandler.read(anyString()))
                .thenReturn(mockOneTimeRoundBasedTimer);
        when(mockOneTimeRoundBasedTimerHandler.write(any())).thenReturn(ONE_TIME_ROUND_BASED_TIMER);
        when(mockRecurringRoundBasedTimerHandler.read(anyString()))
                .thenReturn(mockRecurringRoundBasedTimer);
        when(mockRecurringRoundBasedTimerHandler.write(any()))
                .thenReturn(RECURRING_ROUND_BASED_TIMER);
        when(mockRoundBasedTimerManager.oneTimeRoundBasedTimersRepresentation()).thenReturn(
                listOf(mockOneTimeRoundBasedTimer));
        when(mockRoundBasedTimerManager.recurringRoundBasedTimersRepresentation()).thenReturn(
                listOf(mockRecurringRoundBasedTimer));

        handler = new RoundBasedTimerManagerHandler(mockRoundBasedTimerManager,
                mockOneTimeRoundBasedTimerHandler, mockRecurringRoundBasedTimerHandler);
    }

    @Test
    public void testConstructorWithInvalidParams() {
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
    public void testWrite() {
        var writtenValue = handler.write(mockRoundBasedTimerManager);

        assertEquals(DATA, writtenValue);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var output = handler.read(DATA);

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
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        RoundBasedTimerManager.class.getCanonicalName() + ">",
                handler.getInterfaceName());
    }
}
