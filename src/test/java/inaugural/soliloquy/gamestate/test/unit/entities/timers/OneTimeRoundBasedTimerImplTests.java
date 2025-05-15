package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.OneTimeRoundBasedTimerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.timers.OneTimeRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OneTimeRoundBasedTimerImplTests {
    private final String TIMER_ID = randomString();
    private final String ACTION_ID = randomString();
    private final int ROUND_WHEN_GOES_OFF = randomInt();
    private final int PRIORITY = randomInt();

    @Mock private Action<Long> mockAction;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;

    private OneTimeRoundBasedTimer oneTimeRoundBasedTimer;

    @BeforeEach
    public void setUp() {
        //noinspection unchecked
        mockAction = mock(Action.class);

        lenient().when(mockAction.id()).thenReturn(ACTION_ID);
        mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        oneTimeRoundBasedTimer = new OneTimeRoundBasedTimerImpl(TIMER_ID, mockAction,
                ROUND_WHEN_GOES_OFF, PRIORITY,
                mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                null, mockAction, ROUND_WHEN_GOES_OFF, PRIORITY,
                mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer
        ));
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                "", mockAction, ROUND_WHEN_GOES_OFF, PRIORITY,
                mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer
        ));
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                TIMER_ID, null, ROUND_WHEN_GOES_OFF, PRIORITY,
                mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer
        ));
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                TIMER_ID, mockAction, ROUND_WHEN_GOES_OFF, PRIORITY,
                null,
                mockRoundBasedTimerManager::deregisterOneTimeRoundBasedTimer
        ));
        assertThrows(IllegalArgumentException.class, () -> new OneTimeRoundBasedTimerImpl(
                TIMER_ID, mockAction, ROUND_WHEN_GOES_OFF, PRIORITY,
                mockRoundBasedTimerManager::registerOneTimeRoundBasedTimer,
                null
        ));
    }

    @Test
    public void testEquals() {
        OneTimeRoundBasedTimer mockEqualTimer = mock(OneTimeRoundBasedTimer.class);
        when(mockEqualTimer.id()).thenReturn(TIMER_ID);
        when(mockEqualTimer.actionId()).thenReturn(ACTION_ID);
        when(mockEqualTimer.priority()).thenReturn(PRIORITY);
        when(mockEqualTimer.roundWhenGoesOff()).thenReturn(ROUND_WHEN_GOES_OFF);

        assertEquals(oneTimeRoundBasedTimer, mockEqualTimer);
    }

    @Test
    public void testRoundWhenGoesOff() {
        assertEquals(ROUND_WHEN_GOES_OFF, oneTimeRoundBasedTimer.roundWhenGoesOff());
    }

    @Test
    public void testActionId() {
        assertEquals(ACTION_ID, oneTimeRoundBasedTimer.actionId());
    }

    @Test
    public void testRun() {
        oneTimeRoundBasedTimer.run();

        verify(mockAction).run(null);
    }

    @Test
    public void testPriority() {
        assertEquals(PRIORITY, oneTimeRoundBasedTimer.priority());
    }

    @Test
    public void testId() {
        assertEquals(TIMER_ID, oneTimeRoundBasedTimer.id());
    }

    @Test
    public void testDelete() {
        oneTimeRoundBasedTimer.delete();

        verify(mockRoundBasedTimerManager)
                .deregisterOneTimeRoundBasedTimer(oneTimeRoundBasedTimer);
    }

    @Test
    public void testDeletedInvariant() {
        oneTimeRoundBasedTimer.delete();

        assertThrows(EntityDeletedException.class, () -> oneTimeRoundBasedTimer.id());
        assertThrows(EntityDeletedException.class, () -> oneTimeRoundBasedTimer.run());
        assertThrows(EntityDeletedException.class, () -> oneTimeRoundBasedTimer.priority());
        assertThrows(EntityDeletedException.class,
                () -> oneTimeRoundBasedTimer.roundWhenGoesOff());
    }
}
