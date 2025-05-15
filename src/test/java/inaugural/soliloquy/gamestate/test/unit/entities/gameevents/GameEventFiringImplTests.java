package inaugural.soliloquy.gamestate.test.unit.entities.gameevents;

import inaugural.soliloquy.gamestate.entities.gameevents.GameEventFiringImpl;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.gameevents.GameEventFiring;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.concurrent.CopyOnWriteArrayList;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TODO: Bad suite.
@ExtendWith(MockitoExtension.class)
public class GameEventFiringImplTests {
    private boolean event1CanComplete = false;
    private boolean event2CanComplete = false;
    private boolean event3CanComplete = false;
    private CopyOnWriteArrayList<Integer> eventsCompleted;

    private final Runnable EVENT_1 = () -> {
        while (!event1CanComplete) {
            CheckedExceptionWrapper.sleep(10);
        }
        eventsCompleted.add(1);
    };
    private final Runnable EVENT_2 = () -> {
        while (!event2CanComplete) {
            CheckedExceptionWrapper.sleep(10);
        }
        eventsCompleted.add(2);
    };
    private final Runnable EVENT_3 = () -> {
        while (!event3CanComplete) {
            CheckedExceptionWrapper.sleep(10);
        }
        eventsCompleted.add(3);
    };
    private final IllegalArgumentException ERROR = new IllegalArgumentException();
    private final Runnable EVENT_ERROR = () -> { throw ERROR; };

    private Throwable handledError;

    @Mock private GameSaveBlocker mockGameSaveBlocker;

    private GameEventFiring gameEventFiring;

    @BeforeEach
    public void setUp() {
        eventsCompleted = new CopyOnWriteArrayList<>();

        gameEventFiring = new GameEventFiringImpl(mockGameSaveBlocker, e -> handledError = e);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameEventFiringImpl(null, e -> handledError = e));
        assertThrows(IllegalArgumentException.class,
                () -> new GameEventFiringImpl(mockGameSaveBlocker, null));
    }

    @Test
    public void testRegisterEventFiresEvent() {
        event1CanComplete = true;

        gameEventFiring.registerEvent(EVENT_1, randomInt());
        CheckedExceptionWrapper.sleep(50);

        assertTrue(eventsCompleted.contains(1));
    }

    @Test
    public void testEventsFiredInOrderOfPriorityAndPlacement() {
        int event1Priority = randomInt();
        gameEventFiring.registerEvent(EVENT_1, event1Priority);
        gameEventFiring.registerEvent(EVENT_2, randomIntWithInclusiveCeiling(event1Priority - 1));
        gameEventFiring.registerEvent(EVENT_3, event1Priority);

        event1CanComplete = true;
        event2CanComplete = true;
        event3CanComplete = true;

        CheckedExceptionWrapper.sleep(50);

        assertEquals(3, eventsCompleted.size());
        assertEquals(1, (int) eventsCompleted.get(0));
        assertEquals(3, (int) eventsCompleted.get(1));
        assertEquals(2, (int) eventsCompleted.get(2));
    }

    @Test
    public void testFreeForGameplayInput() {
        gameEventFiring.registerEvent(EVENT_1, randomInt());
        gameEventFiring.registerEvent(EVENT_2, randomInt());
        gameEventFiring.registerEvent(EVENT_3, randomInt());

        CheckedExceptionWrapper.sleep(50);

        assertFalse(gameEventFiring.freeForGameplayInput());

        event1CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        assertFalse(gameEventFiring.freeForGameplayInput());

        event2CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        assertFalse(gameEventFiring.freeForGameplayInput());

        event3CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        assertTrue(gameEventFiring.freeForGameplayInput());
    }

    @Test
    public void testPlacementAndReleasingOfGameSaveBlocks() {
        gameEventFiring.registerEvent(EVENT_1, randomInt());
        gameEventFiring.registerEvent(EVENT_2, randomInt());
        gameEventFiring.registerEvent(EVENT_3, randomInt());

        CheckedExceptionWrapper.sleep(50);

        verify(mockGameSaveBlocker, once()).placeEventFiringBlock(any());
        verify(mockGameSaveBlocker, times(0)).releaseEventFiringBlock(any());

        event1CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        verify(mockGameSaveBlocker, once()).placeEventFiringBlock(any());
        verify(mockGameSaveBlocker, times(0)).releaseEventFiringBlock(any());

        event2CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        verify(mockGameSaveBlocker, once()).placeEventFiringBlock(any());
        verify(mockGameSaveBlocker, times(0)).releaseEventFiringBlock(any());

        event3CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        verify(mockGameSaveBlocker, once()).placeEventFiringBlock(any());
        verify(mockGameSaveBlocker, once()).releaseEventFiringBlock(any());
    }

    @Test
    public void testSubthreadErrorHandler() {
        gameEventFiring.registerEvent(EVENT_ERROR, randomInt());

        CheckedExceptionWrapper.sleep(50);

        assertSame(ERROR, handledError);
    }
}
