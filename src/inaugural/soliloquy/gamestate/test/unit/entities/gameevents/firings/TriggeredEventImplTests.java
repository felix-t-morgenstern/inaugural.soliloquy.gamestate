package inaugural.soliloquy.gamestate.test.unit.entities.gameevents.firings;

import inaugural.soliloquy.gamestate.entities.gameevents.firings.TriggeredEventImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameSaveBlocker;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.gameevents.firings.TriggeredEvent;

import static org.junit.jupiter.api.Assertions.*;

class TriggeredEventImplTests {
    private final int PRIORITY = 123;
    private final Runnable FIRE = () ->
    {
        _runnableFired = true;
        while (_continueRunnableLoop) {
            CheckedExceptionWrapper.sleep(5);
        }
    };
    private final FakeGameSaveBlocker GAME_SAVE_BLOCKER = new FakeGameSaveBlocker();

    private static boolean _runnableFired = false;
    private static volatile boolean _continueRunnableLoop = true;

    private TriggeredEvent _triggeredEvent;

    @BeforeEach
    void setUp() {
        _triggeredEvent = new TriggeredEventImpl(PRIORITY, FIRE, GAME_SAVE_BLOCKER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TriggeredEventImpl(PRIORITY, null, GAME_SAVE_BLOCKER));
        assertThrows(IllegalArgumentException.class,
                () -> new TriggeredEventImpl(PRIORITY, FIRE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TriggeredEvent.class.getCanonicalName(), _triggeredEvent.getInterfaceName());
    }

    @Test
    void testPriority() {
        assertEquals(PRIORITY, _triggeredEvent.priority());
    }

    // NB: This test case requires parallelization, since it must be tested whether the block
    //     placed on the GameSaveBlocker _ONLY_ occurs _AFTER_ the conclusion of firing
    @Test
    void testRunAndPlacingBlocksOnGameSaveBlocker() {
        assertEquals(1, GAME_SAVE_BLOCKER.PlaceTriggeredEventBlockInputs.size());
        assertSame(_triggeredEvent, GAME_SAVE_BLOCKER.PlaceTriggeredEventBlockInputs.get(0));
        assertEquals(0, GAME_SAVE_BLOCKER.ReleaseTriggeredEventBlockInputs.size());
        assertFalse(_runnableFired);

        new Thread(_triggeredEvent).start();
        CheckedExceptionWrapper.sleep(5);

        assertEquals(1, GAME_SAVE_BLOCKER.PlaceTriggeredEventBlockInputs.size());
        assertSame(_triggeredEvent, GAME_SAVE_BLOCKER.PlaceTriggeredEventBlockInputs.get(0));
        assertEquals(0, GAME_SAVE_BLOCKER.ReleaseTriggeredEventBlockInputs.size());
        assertTrue(_runnableFired);

        _continueRunnableLoop = false;

        CheckedExceptionWrapper.sleep(10);

        assertEquals(1, GAME_SAVE_BLOCKER.PlaceTriggeredEventBlockInputs.size());
        assertSame(_triggeredEvent, GAME_SAVE_BLOCKER.PlaceTriggeredEventBlockInputs.get(0));
        assertEquals(1, GAME_SAVE_BLOCKER.ReleaseTriggeredEventBlockInputs.size());
        assertSame(_triggeredEvent, GAME_SAVE_BLOCKER.ReleaseTriggeredEventBlockInputs.get(0));
    }
}
