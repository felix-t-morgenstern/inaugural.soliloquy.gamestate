package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;

import static org.junit.jupiter.api.Assertions.*;

class OneTimeClockBasedTimerImplTests {
    private final long FIRING_TIME = 789789L;
    private final long PAUSE_TIME = 123123L;
    @SuppressWarnings("FieldCanBeLocal")
    private final long UNPAUSE_TIME = 456456L;
    private final String FIRING_ACTION_ID = "firingActionId";
    private final FakeAction<Long> FIRING_ACTION = new FakeAction<>(FIRING_ACTION_ID);

    private OneTimeClockBasedTimer _oneTimeClockBasedTimer;

    @BeforeEach
    void setUp() {
        _oneTimeClockBasedTimer = new OneTimeClockBasedTimerImpl(FIRING_TIME, FIRING_ACTION, null);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(FIRING_TIME, FIRING_ACTION, FIRING_TIME));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(FIRING_TIME, null, FIRING_TIME - 1));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(OneTimeClockBasedTimer.class.getCanonicalName(),
                _oneTimeClockBasedTimer.getInterfaceName());
    }

    @Test
    void testFiringTime() {
        assertEquals(FIRING_TIME, _oneTimeClockBasedTimer.firingTime());
    }

    @Test
    void testActionId() {
        assertSame(FIRING_ACTION_ID, _oneTimeClockBasedTimer.actionId());
    }

    @Test
    void testFire() {
        long firingTime = 123123L;

        _oneTimeClockBasedTimer.fire(firingTime);

        assertEquals(firingTime, FIRING_ACTION._mostRecentInput);
        assertTrue(FIRING_ACTION._actionRun);
    }

    @Test
    void testReportPause() {
        assertNull(_oneTimeClockBasedTimer.pausedTimestamp());

        _oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertEquals(PAUSE_TIME, _oneTimeClockBasedTimer.pausedTimestamp());
    }

    @Test
    void testReportUnpause() {
        _oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        _oneTimeClockBasedTimer.reportUnpause(UNPAUSE_TIME);

        assertEquals(FIRING_TIME + (UNPAUSE_TIME - PAUSE_TIME),
                _oneTimeClockBasedTimer.firingTime());
    }

    // NB: No tests of out-of-date timestamps needed, since there is no timestamp validation needed
    //     for retrieving the Action, and out-of-date timestamps for pausing are already tested in
    //     following test case

    @Test
    void testReportPauseAndUnpauseWithInvalidParams() {
        long timestamp = 123L;

        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportUnpause(timestamp));

        _oneTimeClockBasedTimer.reportPause(timestamp);

        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportUnpause(timestamp - 1));
        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportPause(timestamp));

        _oneTimeClockBasedTimer.reportUnpause(timestamp);


        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportPause(timestamp - 1));
        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportUnpause(timestamp));
    }

    @Test
    void testFireWhenPaused() {
        long pausedTimestamp = 123123L;

        _oneTimeClockBasedTimer.reportPause(pausedTimestamp);

        _oneTimeClockBasedTimer.fire(pausedTimestamp - 1);
        assertThrows(UnsupportedOperationException.class, () ->
                _oneTimeClockBasedTimer.fire(pausedTimestamp));
    }
}
