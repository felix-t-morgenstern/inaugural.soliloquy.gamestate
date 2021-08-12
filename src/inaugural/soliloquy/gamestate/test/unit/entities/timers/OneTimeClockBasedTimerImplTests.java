package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;

import static org.junit.jupiter.api.Assertions.*;

class OneTimeClockBasedTimerImplTests {
    private final long FIRING_TIME = 789789L;
    private final long PAUSE_TIME = 345345L;
    @SuppressWarnings("FieldCanBeLocal")
    private final long UNPAUSE_TIME = 456456L;
    private final long MOST_RECENT_TIMESTAMP = 234234L;
    private final String FIRING_ACTION_ID = "firingActionId";
    private final FakeAction<Long> FIRING_ACTION = new FakeAction<>(FIRING_ACTION_ID);

    private OneTimeClockBasedTimer _oneTimeClockBasedTimer;

    @BeforeEach
    void setUp() {
        _oneTimeClockBasedTimer = new OneTimeClockBasedTimerImpl(FIRING_TIME, FIRING_ACTION, null,
                MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(FIRING_TIME, FIRING_ACTION, FIRING_TIME,
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(FIRING_TIME, null, FIRING_TIME - 1,
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(FIRING_TIME, FIRING_ACTION, PAUSE_TIME,
                        null));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(FIRING_TIME, FIRING_ACTION,
                        MOST_RECENT_TIMESTAMP + 1, MOST_RECENT_TIMESTAMP));
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
        long firingTime = MOST_RECENT_TIMESTAMP + 1;

        _oneTimeClockBasedTimer.fire(firingTime);

        assertEquals(firingTime, FIRING_ACTION._mostRecentInput);
        assertTrue(FIRING_ACTION._actionRun);
        assertEquals(firingTime, _oneTimeClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    void testReportPause() {
        assertNull(_oneTimeClockBasedTimer.pausedTimestamp());

        _oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertEquals(PAUSE_TIME, _oneTimeClockBasedTimer.pausedTimestamp());
        assertEquals(PAUSE_TIME, _oneTimeClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    void testReportUnpause() {
        _oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertEquals(PAUSE_TIME, _oneTimeClockBasedTimer.mostRecentTimestamp());

        _oneTimeClockBasedTimer.reportUnpause(UNPAUSE_TIME);

        assertEquals(UNPAUSE_TIME, _oneTimeClockBasedTimer.mostRecentTimestamp());

        assertEquals(FIRING_TIME + (UNPAUSE_TIME - PAUSE_TIME),
                _oneTimeClockBasedTimer.firingTime());
    }

    // NB: No tests of out-of-date timestamps needed, since there is no timestamp validation needed
    //     for retrieving the Action, and out-of-date timestamps for pausing are already tested in
    //     following test case

    @Test
    void testReportPauseAndUnpauseWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportUnpause(PAUSE_TIME));

        _oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportUnpause(PAUSE_TIME - 1));
        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportPause(PAUSE_TIME));
        assertEquals(PAUSE_TIME, _oneTimeClockBasedTimer.mostRecentTimestamp());

        _oneTimeClockBasedTimer.reportUnpause(PAUSE_TIME);


        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportPause(PAUSE_TIME - 1));
        assertThrows(IllegalArgumentException.class,
                () -> _oneTimeClockBasedTimer.reportUnpause(PAUSE_TIME));
        assertEquals(PAUSE_TIME, _oneTimeClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    void testFireWhenPaused() {
        _oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertThrows(UnsupportedOperationException.class, () ->
                _oneTimeClockBasedTimer.fire(PAUSE_TIME));
        assertEquals(PAUSE_TIME, _oneTimeClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    void testMostRecentTimestamp() {
        assertEquals(MOST_RECENT_TIMESTAMP, _oneTimeClockBasedTimer.mostRecentTimestamp());
    }
}
