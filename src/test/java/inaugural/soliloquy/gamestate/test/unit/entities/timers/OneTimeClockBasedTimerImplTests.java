package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeClockBasedTimerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeAction;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.timers.OneTimeClockBasedTimer;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.Assert.*;

public class OneTimeClockBasedTimerImplTests {
    private final String ID = randomString();
    private final Long MOST_RECENT_TIMESTAMP = randomLong();
    private final Long PAUSE_TIME = randomLongWithInclusiveFloor(MOST_RECENT_TIMESTAMP + 1);
    @SuppressWarnings("FieldCanBeLocal")
    private final Long UNPAUSE_TIME = randomLongWithInclusiveFloor(PAUSE_TIME + 1);
    private final long FIRING_TIME = randomLongWithInclusiveFloor(UNPAUSE_TIME + 1);
    private final String FIRING_ACTION_ID = randomString();
    private final FakeAction<Long> FIRING_ACTION = new FakeAction<>(FIRING_ACTION_ID);

    private OneTimeClockBasedTimer oneTimeClockBasedTimer;

    @Before
    public void setUp() {
        oneTimeClockBasedTimer = new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, FIRING_ACTION,
                null, MOST_RECENT_TIMESTAMP);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(null, FIRING_TIME, FIRING_ACTION,
                        null, MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl("", FIRING_TIME, FIRING_ACTION,
                        null, MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, FIRING_ACTION, FIRING_TIME,
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, null, FIRING_TIME - 1,
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, FIRING_ACTION, PAUSE_TIME,
                        null));
        assertThrows(IllegalArgumentException.class, () ->
                new OneTimeClockBasedTimerImpl(ID, FIRING_TIME, FIRING_ACTION,
                        MOST_RECENT_TIMESTAMP + 1, MOST_RECENT_TIMESTAMP));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(OneTimeClockBasedTimer.class.getCanonicalName(),
                oneTimeClockBasedTimer.getInterfaceName());
    }

    @Test
    public void testId() {
        assertEquals(ID, oneTimeClockBasedTimer.id());
    }

    @Test
    public void testFiringTime() {
        assertEquals(FIRING_TIME, oneTimeClockBasedTimer.firingTime());
    }

    @Test
    public void testActionId() {
        assertSame(FIRING_ACTION_ID, oneTimeClockBasedTimer.actionId());
    }

    @Test
    public void testFire() {
        var firingTime = MOST_RECENT_TIMESTAMP + 1;

        oneTimeClockBasedTimer.fire(firingTime);

        assertEquals((Long)firingTime, FIRING_ACTION.mostRecentInput);
        assertTrue(FIRING_ACTION.actionRun);
        assertEquals((Long)firingTime, oneTimeClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    public void testReportPauseOrUnpauseAfterFired() {
        oneTimeClockBasedTimer.fire(MOST_RECENT_TIMESTAMP);

        assertThrows(UnsupportedOperationException.class, () ->
                oneTimeClockBasedTimer.reportUnpause(MOST_RECENT_TIMESTAMP));
        assertThrows(UnsupportedOperationException.class, () ->
                oneTimeClockBasedTimer.reportPause(MOST_RECENT_TIMESTAMP));
    }

    @Test
    public void testReportPause() {
        assertNull(oneTimeClockBasedTimer.pausedTimestamp());

        oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertEquals(PAUSE_TIME, oneTimeClockBasedTimer.pausedTimestamp());
        assertEquals(PAUSE_TIME, oneTimeClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    public void testReportUnpause() {
        oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertEquals(PAUSE_TIME, oneTimeClockBasedTimer.mostRecentTimestamp());

        oneTimeClockBasedTimer.reportUnpause(UNPAUSE_TIME);

        assertEquals(UNPAUSE_TIME, oneTimeClockBasedTimer.mostRecentTimestamp());

        assertEquals(FIRING_TIME + (UNPAUSE_TIME - PAUSE_TIME),
                oneTimeClockBasedTimer.firingTime());
    }

    @Test
    public void testReportPauseOrProvideWithOutdatedTimestamp() {
        oneTimeClockBasedTimer.reportPause(MOST_RECENT_TIMESTAMP + 1);

        assertThrows(IllegalArgumentException.class, () ->
                oneTimeClockBasedTimer.reportUnpause(MOST_RECENT_TIMESTAMP));

        oneTimeClockBasedTimer.reportUnpause(MOST_RECENT_TIMESTAMP + 2);

        assertThrows(IllegalArgumentException.class, () ->
                oneTimeClockBasedTimer.reportPause(MOST_RECENT_TIMESTAMP + 1));
    }

    @Test
    public void testReportPauseWhilePausedOrViceVersa() {
        assertThrows(UnsupportedOperationException.class, () ->
                oneTimeClockBasedTimer.reportUnpause(MOST_RECENT_TIMESTAMP));

        oneTimeClockBasedTimer.reportPause(MOST_RECENT_TIMESTAMP);

        assertThrows(UnsupportedOperationException.class, () ->
                oneTimeClockBasedTimer.reportPause(MOST_RECENT_TIMESTAMP));
    }

    @Test
    public void testFireWhenPaused() {
        oneTimeClockBasedTimer.reportPause(PAUSE_TIME);

        assertThrows(UnsupportedOperationException.class, () ->
                oneTimeClockBasedTimer.fire(PAUSE_TIME));
        assertEquals(PAUSE_TIME, oneTimeClockBasedTimer.mostRecentTimestamp());
    }

    @Test
    public void testMostRecentTimestamp() {
        assertEquals(MOST_RECENT_TIMESTAMP, oneTimeClockBasedTimer.mostRecentTimestamp());
    }
}
