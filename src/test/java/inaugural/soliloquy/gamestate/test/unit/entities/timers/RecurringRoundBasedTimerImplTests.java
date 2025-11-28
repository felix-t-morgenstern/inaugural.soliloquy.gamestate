package inaugural.soliloquy.gamestate.test.unit.entities.timers;

import inaugural.soliloquy.gamestate.entities.timers.RecurringRoundBasedTimerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Consumer;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.timers.RecurringRoundBasedTimer;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecurringRoundBasedTimerImplTests {
    private final String TIMER_ID = randomString();
    private final String CONSUMER_ID = randomString();
    private final int ROUND_MODULO = randomIntWithInclusiveFloor(1);
    private final int ROUND_OFFSET = ROUND_MODULO - 1;
    private final int PRIORITY = randomInt();

    @Mock private Consumer<Long> mockConsumer;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;

    private RecurringRoundBasedTimer recurringRoundBasedTimer;

    @BeforeEach
    public void setUp() {
        lenient().when(mockConsumer.id()).thenReturn(CONSUMER_ID);

        recurringRoundBasedTimer = new RecurringRoundBasedTimerImpl(TIMER_ID, mockConsumer,
                ROUND_MODULO, ROUND_OFFSET, PRIORITY,
                mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(null, mockConsumer, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl("", mockConsumer, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, null, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, mockConsumer, 0, 0,
                        PRIORITY, mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, mockConsumer, ROUND_MODULO, -1,
                        PRIORITY, mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, mockConsumer, ROUND_MODULO, ROUND_MODULO,
                        PRIORITY, mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, mockConsumer, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, null,
                        mockRoundBasedTimerManager::deregisterRecurringRoundBasedTimer
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new RecurringRoundBasedTimerImpl(TIMER_ID, mockConsumer, ROUND_MODULO, ROUND_OFFSET,
                        PRIORITY, mockRoundBasedTimerManager::registerRecurringRoundBasedTimer,
                        null
                ));
    }

    @Test
    public void testEquals() {
        var mockTimer = mock(RecurringRoundBasedTimer.class);
        when(mockTimer.id()).thenReturn(TIMER_ID);
        when(mockTimer.consumerId()).thenReturn(CONSUMER_ID);
        when(mockTimer.priority()).thenReturn(PRIORITY);
        when(mockTimer.roundModulo()).thenReturn(ROUND_MODULO);
        when(mockTimer.roundOffset()).thenReturn(ROUND_OFFSET);

        assertEquals(recurringRoundBasedTimer, mockTimer);
    }

    @Test
    public void testRoundModulo() {
        assertEquals(ROUND_MODULO, recurringRoundBasedTimer.roundModulo());
    }

    @Test
    public void testRoundOffset() {
        assertEquals(ROUND_OFFSET, recurringRoundBasedTimer.roundOffset());
    }

    @Test
    public void testActionId() {
        assertEquals(CONSUMER_ID, recurringRoundBasedTimer.consumerId());
    }

    @Test
    public void testRun() {
        recurringRoundBasedTimer.run();

        verify(mockConsumer).accept(null);
        verify(mockRoundBasedTimerManager, never())
                .deregisterRecurringRoundBasedTimer(recurringRoundBasedTimer);
        assertFalse(recurringRoundBasedTimer.isDeleted());
    }

    @Test
    public void testPriority() {
        assertEquals(PRIORITY, recurringRoundBasedTimer.priority());
    }

    @Test
    public void testId() {
        assertEquals(TIMER_ID, recurringRoundBasedTimer.id());
    }

    @Test
    public void testDelete() {
        recurringRoundBasedTimer.delete();

        verify(mockRoundBasedTimerManager)
                .deregisterRecurringRoundBasedTimer(recurringRoundBasedTimer);
    }

    @Test
    public void testDeletedInvariant() {
        recurringRoundBasedTimer.delete();

        assertThrows(EntityDeletedException.class, () -> recurringRoundBasedTimer.id());
        assertThrows(EntityDeletedException.class, () -> recurringRoundBasedTimer.run());
        assertThrows(EntityDeletedException.class, () -> recurringRoundBasedTimer.priority());
        assertThrows(EntityDeletedException.class, () -> recurringRoundBasedTimer.roundModulo());
        assertThrows(EntityDeletedException.class, () -> recurringRoundBasedTimer.roundOffset());
    }
}
