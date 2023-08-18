package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeRoundBasedTimerImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class OneTimeRoundBasedTimerImplTests {
    private final String TIMER_ID = randomString();
    private final String ACTION_ID = randomString();
    private final int ROUND_WHEN_GOES_OFF = randomInt();
    private final int PRIORITY = randomInt();

    @Mock
    private Action<Long> _mockAction;
    @Mock
    private RoundBasedTimerManager _mockRoundBasedTimerManager;

    private OneTimeRoundBasedTimer _oneTimeRoundBasedTimer;

    @Before
    public void setUp() {
        //noinspection unchecked
        _mockAction = mock(Action.class);

        when(_mockAction.id()).thenReturn(ACTION_ID);
        _mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        _oneTimeRoundBasedTimer = new OneTimeRoundBasedTimerImpl(TIMER_ID, _mockAction,
                ROUND_WHEN_GOES_OFF, PRIORITY,
                _mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                _mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                null, _mockAction, ROUND_WHEN_GOES_OFF, PRIORITY,
                _mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                _mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer
        ));
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                "", _mockAction, ROUND_WHEN_GOES_OFF, PRIORITY,
                _mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                _mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer
        ));
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                TIMER_ID, null, ROUND_WHEN_GOES_OFF, PRIORITY,
                _mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                _mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer
        ));
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                TIMER_ID, _mockAction, ROUND_WHEN_GOES_OFF, PRIORITY,
                null,
                _mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer
        ));
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                TIMER_ID, _mockAction, ROUND_WHEN_GOES_OFF, PRIORITY,
                _mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                null
        ));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(OneTimeRoundBasedTimer.class.getCanonicalName(),
                _oneTimeRoundBasedTimer.getInterfaceName());
    }

    @Test
    public void testEquals() {
        OneTimeRoundBasedTimer mockEqualTimer = mock(OneTimeRoundBasedTimer.class);
        when(mockEqualTimer.id()).thenReturn(TIMER_ID);
        when(mockEqualTimer.actionId()).thenReturn(ACTION_ID);
        when(mockEqualTimer.priority()).thenReturn(PRIORITY);
        when(mockEqualTimer.roundWhenGoesOff()).thenReturn(ROUND_WHEN_GOES_OFF);

        assertEquals(_oneTimeRoundBasedTimer, mockEqualTimer);
    }

    @Test
    public void testRoundWhenGoesOff() {
        assertEquals(ROUND_WHEN_GOES_OFF, _oneTimeRoundBasedTimer.roundWhenGoesOff());
    }

    @Test
    public void testActionId() {
        assertEquals(ACTION_ID, _oneTimeRoundBasedTimer.actionId());
    }

    @Test
    public void testRun() {
        _oneTimeRoundBasedTimer.run();

        verify(_mockAction).run(null);
    }

    @Test
    public void testPriority() {
        assertEquals(PRIORITY, _oneTimeRoundBasedTimer.priority());
    }

    @Test
    public void testId() {
        assertEquals(TIMER_ID, _oneTimeRoundBasedTimer.id());
    }

    @Test
    public void testDelete() {
        _oneTimeRoundBasedTimer.delete();

        verify(_mockRoundBasedTimerManager)
                .deregisterOneTimeRoundBasedTimer(_oneTimeRoundBasedTimer);
    }

    @Test
    public void testDeletedInvariant() {
        _oneTimeRoundBasedTimer.delete();

        assertThrows(EntityDeletedException.class, () -> _oneTimeRoundBasedTimer.id());
        assertThrows(EntityDeletedException.class, () -> _oneTimeRoundBasedTimer.run());
        assertThrows(EntityDeletedException.class, () -> _oneTimeRoundBasedTimer.priority());
        assertThrows(EntityDeletedException.class,
                () -> _oneTimeRoundBasedTimer.roundWhenGoesOff());
    }
}
