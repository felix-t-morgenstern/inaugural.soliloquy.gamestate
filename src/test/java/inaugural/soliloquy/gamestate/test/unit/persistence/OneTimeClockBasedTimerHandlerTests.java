package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.OneTimeClockBasedTimerHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;
import soliloquy.specs.gamestate.factories.ClockBasedTimerFactory;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomLong;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OneTimeClockBasedTimerHandlerTests {
    private final String ONE_TIME_CLOCK_TIMER_ID = randomString();
    private final String ACTION_ID = randomString();
    @SuppressWarnings("rawtypes")
    private final Action ACTION = new FakeAction(ACTION_ID);
    private final long FIRING_TIME = randomLong();
    private final long PAUSE_TIME = randomLong();
    private final long MOST_RECENT_TIMESTAMP = randomLong();
    @SuppressWarnings("rawtypes")
    private final Map<String, Action> ACTIONS = mapOf(pairOf(ACTION_ID, ACTION));

    @Mock private OneTimeClockBasedTimer mockOneTimeClockBasedTimer;
    @Mock private ClockBasedTimerFactory mockClockBasedTimerFactory;

    private TypeHandler<OneTimeClockBasedTimer> handler;

    private final String WRITTEN_VALUE = String.format(
            "{\"id\":\"%s\",\"actionId\":\"%s\",\"firingTime\":%d,\"pausedTime\":%d," +
                    "\"mostRecentTimestamp\":%d}",
            ONE_TIME_CLOCK_TIMER_ID, ACTION_ID, FIRING_TIME, PAUSE_TIME, MOST_RECENT_TIMESTAMP);

    @Before
    public void setUp() {
        when(mockClockBasedTimerFactory.make(anyString(), anyLong(), any(), anyLong(), anyLong()))
                .thenReturn(mockOneTimeClockBasedTimer);

        handler = new OneTimeClockBasedTimerHandler(mockClockBasedTimerFactory, ACTIONS::get);
    }

    @Test
    public void testWrite() {
        when(mockOneTimeClockBasedTimer.id()).thenReturn(ONE_TIME_CLOCK_TIMER_ID);
        when(mockOneTimeClockBasedTimer.firingTime()).thenReturn(FIRING_TIME);
        when(mockOneTimeClockBasedTimer.pausedTimestamp()).thenReturn(PAUSE_TIME);
        when(mockOneTimeClockBasedTimer.actionId()).thenReturn(ACTION_ID);
        when(mockOneTimeClockBasedTimer.mostRecentTimestamp()).thenReturn(MOST_RECENT_TIMESTAMP);

        var result = handler.write(mockOneTimeClockBasedTimer);

        assertEquals(WRITTEN_VALUE, result);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var result = handler.read(WRITTEN_VALUE);

        assertSame(mockOneTimeClockBasedTimer, result);
        //noinspection unchecked
        verify(mockClockBasedTimerFactory).make(ONE_TIME_CLOCK_TIMER_ID, FIRING_TIME, ACTION,
                PAUSE_TIME, MOST_RECENT_TIMESTAMP);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.read(null));
        assertThrows(IllegalArgumentException.class, () ->
                handler.read(""));
    }

    @Test
    public void testArchetype() {
        assertNotNull(handler.archetype());
        assertEquals(OneTimeClockBasedTimer.class.getCanonicalName(),
                handler.archetype().getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        OneTimeClockBasedTimer.class.getCanonicalName() + ">",
                handler.getInterfaceName());
    }
}
