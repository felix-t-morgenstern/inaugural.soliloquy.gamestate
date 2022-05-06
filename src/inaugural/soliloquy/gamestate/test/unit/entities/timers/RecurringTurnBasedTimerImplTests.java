package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.RecurringRoundBasedTimerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RecurringRoundBasedTimerImplTests {
    private final String TIMER_ID = randomString();
    private final String ACTION_ID = randomString();
    private final int ROUND_MODULO = randomIntWithInclusiveFloor(1);
    private final int ROUND_OFFSET = ROUND_MODULO - 1;
    private final int PRIORITY = randomInt();

    @Mock
    private Action<Long> _mockAction;
    @Mock
    private RoundBasedTimerManager _mockRoundBasedTimerManager;

    private RecurringRoundBasedTimer _recurringRoundBasedTimer;

    @BeforeEach
    void setUp() {
        //noinspection unchecked
        _mockAction = mock(Action.class);
        when(_mockAction.id()).thenReturn(ACTION_ID);

        _mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        _recurringRoundBasedTimer = new RecurringRoundBasedTimerImpl(TIMER_ID, _mockAction,
                ROUND_MODULO, ROUND_OFFSET, PRIORITY,
                _mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                _mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(null, _mockAction, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, _mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        _mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl("", _mockAction, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, _mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        _mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, null, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, _mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        _mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, _mockAction, 0, 0,
                        PRIORITY, _mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        _mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, _mockAction, ROUND_MODULO, -1,
                        PRIORITY, _mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        _mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, _mockAction, ROUND_MODULO, ROUND_MODULO,
                        PRIORITY, _mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        _mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, _mockAction, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, null,
                        _mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, _mockAction, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, _mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        null
                ));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RecurringRoundBasedTimer.class.getCanonicalName(),
                _recurringRoundBasedTimer.getInterfaceName());
    }

    @Test
    void testEquals() {
        RecurringRoundBasedTimer mockTimer = mock(RecurringRoundBasedTimer.class);
        when(mockTimer.id()).thenReturn(TIMER_ID);
        when(mockTimer.actionId()).thenReturn(ACTION_ID);
        when(mockTimer.priority()).thenReturn(PRIORITY);
        when(mockTimer.roundModulo()).thenReturn(ROUND_MODULO);
        when(mockTimer.roundOffset()).thenReturn(ROUND_OFFSET);

        assertEquals(_recurringRoundBasedTimer, mockTimer);
    }

    @Test
    void testRoundModulo() {
        assertEquals(ROUND_MODULO, _recurringRoundBasedTimer.roundModulo());
    }

    @Test
    void testRoundOffset() {
        assertEquals(ROUND_OFFSET, _recurringRoundBasedTimer.roundOffset());
    }

    @Test
    void testActionId() {
        assertEquals(ACTION_ID, _recurringRoundBasedTimer.actionId());
    }

    @Test
    void testRun() {
        _recurringRoundBasedTimer.run();

        verify(_mockAction).run(null);
    }

    @Test
    void testPriority() {
        assertEquals(PRIORITY, _recurringRoundBasedTimer.priority());
    }

    @Test
    void testId() {
        assertEquals(TIMER_ID, _recurringRoundBasedTimer.id());
    }

    @Test
    void testDelete() {
        _recurringRoundBasedTimer.delete();

        verify(_mockRoundBasedTimerManager)
                .deregisterRecurringRoundBasedTimer(_recurringRoundBasedTimer);
    }

    @Test
    void testDeletedInvariant() {
        _recurringRoundBasedTimer.delete();

        assertThrows(EntityDeletedException.class, () -> _recurringRoundBasedTimer.id());
        assertThrows(EntityDeletedException.class, () -> _recurringRoundBasedTimer.run());
        assertThrows(EntityDeletedException.class, () -> _recurringRoundBasedTimer.priority());
        assertThrows(EntityDeletedException.class, () -> _recurringRoundBasedTimer.roundModulo());
        assertThrows(EntityDeletedException.class, () -> _recurringRoundBasedTimer.roundOffset());
    }
}
