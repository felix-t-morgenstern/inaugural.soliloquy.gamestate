package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.gamestate.entities.timers.RoundBasedTimerManagerImpl;
import inaugural.soliloquy.gamestate.persistence.RoundBasedTimerManagerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    @BeforeEach
    public void setUp() {
        lenient().when(mockOneTimeRoundBasedTimerHandler.read(anyString()))
                .thenReturn(mockOneTimeRoundBasedTimer);
        lenient().when(mockOneTimeRoundBasedTimerHandler.write(any())).thenReturn(ONE_TIME_ROUND_BASED_TIMER);
        lenient().when(mockRecurringRoundBasedTimerHandler.read(anyString()))
                .thenReturn(mockRecurringRoundBasedTimer);
        lenient().when(mockRecurringRoundBasedTimerHandler.write(any()))
                .thenReturn(RECURRING_ROUND_BASED_TIMER);
        lenient().when(mockRoundBasedTimerManager.oneTimeRoundBasedTimersRepresentation()).thenReturn(
                listOf(mockOneTimeRoundBasedTimer));
        lenient().when(mockRoundBasedTimerManager.recurringRoundBasedTimersRepresentation()).thenReturn(
                listOf(mockRecurringRoundBasedTimer));

        handler = new RoundBasedTimerManagerHandler(mockRoundBasedTimerManager,
                mockOneTimeRoundBasedTimerHandler, mockRecurringRoundBasedTimerHandler);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
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
    public void testTypeHandled() {
        assertEquals(RoundBasedTimerManagerImpl.class.getCanonicalName(), handler.typeHandled());
    }

    @Test
    public void testWrite() {
        var writtenValue = handler.write(mockRoundBasedTimerManager);

        assertEquals(DATA, writtenValue);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var output = handler.read(DATA);

        assertNull(output);
        verify(mockRoundBasedTimerManager, once()).clear();
        verify(mockOneTimeRoundBasedTimerHandler, once()).read(ONE_TIME_ROUND_BASED_TIMER);
        verify(mockRoundBasedTimerManager, once()).registerOneTimeRoundBasedTimer(
                mockOneTimeRoundBasedTimer);
        verify(mockRecurringRoundBasedTimerHandler, once()).read(RECURRING_ROUND_BASED_TIMER);
        verify(mockRoundBasedTimerManager, once()).registerRecurringRoundBasedTimer(
                mockRecurringRoundBasedTimer);
    }
}
