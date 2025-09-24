package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OneTimeClockBasedTimerImplTests {
    private final String ID = randomString();
    private final Long MOST_RECENT_TIMESTAMP = randomLong();
    private final Long PAUSE_TIME = randomLongWithInclusiveFloor(MOST_RECENT_TIMESTAMP + 1);
    @SuppressWarnings("FieldCanBeLocal")
    private final Long UNPAUSE_TIME = randomLongWithInclusiveFloor(PAUSE_TIME + 1);
    private final long FIRING_TIME = randomLongWithInclusiveFloor(UNPAUSE_TIME + 1);
    private final String FIRING_ACTION_ID = randomString();

    @Mock private Action<Long> mockFiringAction;
    @Mock private TimestampValidator mockTimestampValidator;

    private OneTimeClockBasedTimer timer;

    @BeforeEach
    public void setUp() {
        lenient().when(mockTimestampValidator.mostRecentTimestamp())
                .thenReturn(MOST_RECENT_TIMESTAMP);
        lenient().when(mockFiringAction.id()).thenReturn(FIRING_ACTION_ID);

        timer = new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, mockFiringAction, null,
                mockTimestampValidator);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(null, FIRING_TIME, mockFiringAction,
                        null, mockTimestampValidator));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl("", FIRING_TIME, mockFiringAction,
                        null, mockTimestampValidator));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, mockFiringAction, FIRING_TIME,
                        mockTimestampValidator));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, null, FIRING_TIME - 1,
                        mockTimestampValidator));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, mockFiringAction, PAUSE_TIME,
                        null));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, mockFiringAction,
                        MOST_RECENT_TIMESTAMP + 1, mockTimestampValidator));
    }

    @Test
    public void testId() {
        assertEquals(ID, timer.id());
    }

    @Test
    public void testFiringTime() {
        assertEquals(FIRING_TIME, timer.firingTime());
    }

    @Test
    public void testActionId() {
        assertSame(FIRING_ACTION_ID, timer.actionId());
    }

    @Test
    public void testFire() {
        var firingTime = MOST_RECENT_TIMESTAMP + 1;

        timer.fire(firingTime);

        verify(mockFiringAction, once()).accept(firingTime);
    }

    @Test
    public void testReportPauseOrUnpauseAfterFired() {
        timer.fire(MOST_RECENT_TIMESTAMP);

        assertThrows(UnsupportedOperationException.class,
                () -> timer.reportUnpause(MOST_RECENT_TIMESTAMP));
        assertThrows(UnsupportedOperationException.class,
                () -> timer.reportPause(MOST_RECENT_TIMESTAMP));
    }

    @Test
    public void testReportPause() {
        assertNull(timer.pausedTimestamp());

        timer.reportPause(PAUSE_TIME);

        assertEquals(PAUSE_TIME, timer.pausedTimestamp());
        verify(mockTimestampValidator, once()).validateTimestamp(PAUSE_TIME);
    }

    @Test
    public void testReportUnpause() {
        timer.reportPause(PAUSE_TIME);
        timer.reportUnpause(UNPAUSE_TIME);

        assertEquals(FIRING_TIME + (UNPAUSE_TIME - PAUSE_TIME),
                timer.firingTime());
        verify(mockTimestampValidator, times(2)).validateTimestamp(anyLong());
        verify(mockTimestampValidator, once()).validateTimestamp(PAUSE_TIME);
        verify(mockTimestampValidator, once()).validateTimestamp(UNPAUSE_TIME);
    }

    @Test
    public void testReportPauseWhilePausedOrViceVersa() {
        assertThrows(UnsupportedOperationException.class,
                () -> timer.reportUnpause(MOST_RECENT_TIMESTAMP));

        timer.reportPause(MOST_RECENT_TIMESTAMP);

        assertThrows(UnsupportedOperationException.class,
                () -> timer.reportPause(MOST_RECENT_TIMESTAMP));
    }

    @Test
    public void testFireWhenPaused() {
        timer.reportPause(PAUSE_TIME);

        assertThrows(UnsupportedOperationException.class, () -> timer.fire(PAUSE_TIME));
    }
}
