package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.ClockBasedTimerManagerHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringClockBasedTimer;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClockBasedTimerManagerHandlerTests {
    private final String ONE_TIME_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA = randomString();
    private final String RECURRING_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA = randomString();

    @Mock
    private ClockBasedTimerManager mockClockBasedTimerManager;

    @Mock
    private TypeHandler<OneTimeClockBasedTimer> mockOneTimeClockBasedTimerHandler;
    @Mock
    private TypeHandler<RecurringClockBasedTimer> mockRecurringClockBasedTimerHandler;

    @Mock
    private OneTimeClockBasedTimer mockOneTimeClockBasedTimer;
    @Mock
    private RecurringClockBasedTimer mockRecurringClockBasedTimer;

    private TypeHandler<ClockBasedTimerManager> clockBasedTimerManagerHandler;

    private final String WRITTEN_DATA = String.format(
            "{\"oneTimeClockBasedTimers\":[\"%s\"],\"recurringClockBasedTimers\":[\"%s\"]}",
            ONE_TIME_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA,
            RECURRING_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);

    @Before
    public void setUp() {
        when(mockOneTimeClockBasedTimerHandler.read(anyString()))
                .thenReturn(mockOneTimeClockBasedTimer);
        when(mockOneTimeClockBasedTimerHandler.write(any()))
                .thenReturn(ONE_TIME_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);

        when(mockRecurringClockBasedTimerHandler.read(anyString()))
                .thenReturn(mockRecurringClockBasedTimer);
        when(mockRecurringClockBasedTimerHandler.write(any()))
                .thenReturn(RECURRING_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);


        clockBasedTimerManagerHandler =
                new ClockBasedTimerManagerHandler(mockClockBasedTimerManager,
                        mockOneTimeClockBasedTimerHandler, mockRecurringClockBasedTimerHandler);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ClockBasedTimerManagerHandler(
                null, mockOneTimeClockBasedTimerHandler,
                mockRecurringClockBasedTimerHandler
        ));
        assertThrows(IllegalArgumentException.class, () -> new ClockBasedTimerManagerHandler(
                mockClockBasedTimerManager, null,
                mockRecurringClockBasedTimerHandler
        ));
        assertThrows(IllegalArgumentException.class, () -> new ClockBasedTimerManagerHandler(
                mockClockBasedTimerManager, mockOneTimeClockBasedTimerHandler,
                null
        ));
    }

    @Test
    public void testWrite() {
        when(mockClockBasedTimerManager.oneTimeTimersRepresentation())
                .thenReturn(listOf(mockOneTimeClockBasedTimer));
        when(mockClockBasedTimerManager.recurringTimersRepresentation())
                .thenReturn(listOf(mockRecurringClockBasedTimer));

        String writtenValue = clockBasedTimerManagerHandler.write(null);

        assertEquals(WRITTEN_DATA, writtenValue);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                clockBasedTimerManagerHandler.write(mockClockBasedTimerManager));
    }

    @Test
    public void testRead() {
        var inOrder = inOrder(mockClockBasedTimerManager);

        ClockBasedTimerManager unusedValue = clockBasedTimerManagerHandler.read(WRITTEN_DATA);

        assertNull(unusedValue);
        // Test whether EXISTING manager received values, AND whether it cleared out OLD values
        inOrder.verify(mockClockBasedTimerManager).clear();
        inOrder.verify(mockClockBasedTimerManager)
                .registerOneTimeTimer(mockOneTimeClockBasedTimer);
        inOrder.verify(mockClockBasedTimerManager)
                .registerRecurringTimer(mockRecurringClockBasedTimer);

        verify(mockOneTimeClockBasedTimerHandler)
                .read(ONE_TIME_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);
        verify(mockRecurringClockBasedTimerHandler)
                .read(RECURRING_CLOCK_BASED_TIMER_MOCK_WRITTEN_DATA);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                clockBasedTimerManagerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                clockBasedTimerManagerHandler.read(""));
    }

    @Test
    public void testArchetype() {
        assertNotNull(clockBasedTimerManagerHandler.archetype());
        assertEquals(ClockBasedTimerManager.class.getCanonicalName(),
                clockBasedTimerManagerHandler.archetype().getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        ClockBasedTimerManager.class.getCanonicalName() + ">",
                clockBasedTimerManagerHandler.getInterfaceName());
    }
}
