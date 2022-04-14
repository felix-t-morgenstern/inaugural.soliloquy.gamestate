package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.OneTimeClockBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import java.util.HashMap;

import static inaugural.soliloquy.tools.random.Random.randomLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OneTimeClockBasedTimerHandlerTests {
    private final String ONE_TIME_CLOCK_TIMER_ID = "oneTimeClockBasedTimerId";
    private final String ACTION_ID = "actionId";
    @SuppressWarnings("rawtypes")
    private final Action ACTION = new FakeAction(ACTION_ID);
    private long FIRING_TIME = 123123L;
    private long PAUSE_TIME = 456456L;
    private long MOST_RECENT_TIMESTAMP = 789789;
    @SuppressWarnings("rawtypes")
    private HashMap<String, Action> ACTIONS = new HashMap<>();

    @Mock
    private OneTimeClockBasedTimer _mockOneTimeClockBasedTimer;
    @Mock
    private ClockBasedTimerFactory _mockClockBasedTimerFactory;

    private TypeHandler<OneTimeClockBasedTimer> _oneTimeClockBasedTimerHandler;

    private final String WRITTEN_VALUE = "{\"id\":\"oneTimeClockBasedTimerId\",\"actionId\":\"actionId\",\"firingTime\":123123,\"pausedTime\":456456,\"mostRecentTimestamp\":789789}";

    @BeforeEach
    void setUp() {
        ACTIONS.put(ACTION_ID, ACTION);

        _mockOneTimeClockBasedTimer = mock(OneTimeClockBasedTimer.class);
        _mockClockBasedTimerFactory = mock(ClockBasedTimerFactory.class);
        when(_mockClockBasedTimerFactory.make(anyString(), anyLong(), any(), anyLong(), anyLong()))
                .thenReturn(_mockOneTimeClockBasedTimer);

        _oneTimeClockBasedTimerHandler =
                new OneTimeClockBasedTimerHandler(_mockClockBasedTimerFactory, ACTIONS::get);
    }

    @Test
    void testWrite() {
        when(_mockOneTimeClockBasedTimer.id()).thenReturn(ONE_TIME_CLOCK_TIMER_ID);
        when(_mockOneTimeClockBasedTimer.firingTime()).thenReturn(FIRING_TIME);
        when(_mockOneTimeClockBasedTimer.pausedTimestamp()).thenReturn(PAUSE_TIME);
        when(_mockOneTimeClockBasedTimer.actionId()).thenReturn(ACTION_ID);
        when(_mockOneTimeClockBasedTimer.mostRecentTimestamp()).thenReturn(MOST_RECENT_TIMESTAMP);

        String result = _oneTimeClockBasedTimerHandler.write(_mockOneTimeClockBasedTimer);

        assertEquals(WRITTEN_VALUE, result);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _oneTimeClockBasedTimerHandler.write(null));
    }

    @Test
    void testRead() {
        OneTimeClockBasedTimer result = _oneTimeClockBasedTimerHandler.read(WRITTEN_VALUE);

        assertSame(_mockOneTimeClockBasedTimer, result);
        //noinspection unchecked
        verify(_mockClockBasedTimerFactory).make(ONE_TIME_CLOCK_TIMER_ID, FIRING_TIME, ACTION,
                PAUSE_TIME, MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _oneTimeClockBasedTimerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                _oneTimeClockBasedTimerHandler.read(""));
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_oneTimeClockBasedTimerHandler.getArchetype());
        assertEquals(OneTimeClockBasedTimer.class.getCanonicalName(),
                _oneTimeClockBasedTimerHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                OneTimeClockBasedTimer.class.getCanonicalName() + ">",
                _oneTimeClockBasedTimerHandler.getInterfaceName());
    }
}
