package inaugural.soliloquy.gamestate.test.unit.entities.gameevents;

import inaugural.soliloquy.gamestate.entities.gameevents.GameEventFiringImpl;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.gamestate.entities.gameevents.GameEventFiring;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.concurrent.CopyOnWriteArrayList;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameEventFiringImplTests {
    private boolean _event1CanComplete = false;
    private boolean _event2CanComplete = false;
    private boolean _event3CanComplete = false;
    private CopyOnWriteArrayList<Integer> _eventsCompleted;

    private final Runnable EVENT_1 = () -> {
        while (!_event1CanComplete) {
            CheckedExceptionWrapper.sleep(10);
        }
        _eventsCompleted.add(1);
    };
    private final Runnable EVENT_2 = () -> {
        while (!_event2CanComplete) {
            CheckedExceptionWrapper.sleep(10);
        }
        _eventsCompleted.add(2);
    };
    private final Runnable EVENT_3 = () -> {
        while (!_event3CanComplete) {
            CheckedExceptionWrapper.sleep(10);
        }
        _eventsCompleted.add(3);
    };
    private final IllegalArgumentException ERROR = new IllegalArgumentException();
    private final Runnable EVENT_ERROR = () -> { throw ERROR; };

    private Throwable _handledError;

    @Mock private GameSaveBlocker _mockGameSaveBlocker;

    private GameEventFiring _gameEventFiring;

    @BeforeEach
    void setUp() {
        _eventsCompleted = new CopyOnWriteArrayList<>();

        _mockGameSaveBlocker = mock(GameSaveBlocker.class);

        _gameEventFiring = new GameEventFiringImpl(_mockGameSaveBlocker, e -> _handledError = e);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameEventFiringImpl(null, e -> _handledError = e));
        assertThrows(IllegalArgumentException.class,
                () -> new GameEventFiringImpl(_mockGameSaveBlocker, null));
    }

    @Test
    void testRegisterEventFiresEvent() {
        _event1CanComplete = true;

        _gameEventFiring.registerEvent(EVENT_1, randomInt());
        CheckedExceptionWrapper.sleep(50);

        assertTrue(_eventsCompleted.contains(1));
    }

    @Test
    void testEventsFiredInOrderOfPriorityAndPlacement() {
        int event1Priority = randomInt();
        _gameEventFiring.registerEvent(EVENT_1, event1Priority);
        _gameEventFiring.registerEvent(EVENT_2, randomIntWithInclusiveCeiling(event1Priority - 1));
        _gameEventFiring.registerEvent(EVENT_3, event1Priority);

        _event1CanComplete = true;
        _event2CanComplete = true;
        _event3CanComplete = true;

        CheckedExceptionWrapper.sleep(50);

        assertEquals(3, _eventsCompleted.size());
        assertEquals(1, (int) _eventsCompleted.get(0));
        assertEquals(3, (int) _eventsCompleted.get(1));
        assertEquals(2, (int) _eventsCompleted.get(2));
    }

    @Test
    void testFreeForGameplayInput() {
        _gameEventFiring.registerEvent(EVENT_1, randomInt());
        _gameEventFiring.registerEvent(EVENT_2, randomInt());
        _gameEventFiring.registerEvent(EVENT_3, randomInt());

        CheckedExceptionWrapper.sleep(50);

        assertFalse(_gameEventFiring.freeForGameplayInput());

        _event1CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        assertFalse(_gameEventFiring.freeForGameplayInput());

        _event2CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        assertFalse(_gameEventFiring.freeForGameplayInput());

        _event3CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        assertTrue(_gameEventFiring.freeForGameplayInput());
    }

    @Test
    void testPlacementAndReleasingOfGameSaveBlocks() {
        _gameEventFiring.registerEvent(EVENT_1, randomInt());
        _gameEventFiring.registerEvent(EVENT_2, randomInt());
        _gameEventFiring.registerEvent(EVENT_3, randomInt());

        CheckedExceptionWrapper.sleep(50);

        verify(_mockGameSaveBlocker, times(1)).placeEventFiringBlock(any());
        verify(_mockGameSaveBlocker, times(0)).releaseEventFiringBlock(any());

        _event1CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        verify(_mockGameSaveBlocker, times(1)).placeEventFiringBlock(any());
        verify(_mockGameSaveBlocker, times(0)).releaseEventFiringBlock(any());

        _event2CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        verify(_mockGameSaveBlocker, times(1)).placeEventFiringBlock(any());
        verify(_mockGameSaveBlocker, times(0)).releaseEventFiringBlock(any());

        _event3CanComplete = true;
        CheckedExceptionWrapper.sleep(50);

        verify(_mockGameSaveBlocker, times(1)).placeEventFiringBlock(any());
        verify(_mockGameSaveBlocker, times(1)).releaseEventFiringBlock(any());
    }

    @Test
    void testSubthreadErrorHandler() {
        _gameEventFiring.registerEvent(EVENT_ERROR, randomInt());

        CheckedExceptionWrapper.sleep(50);

        assertSame(ERROR, _handledError);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameEventFiring.class.getCanonicalName(), _gameEventFiring.getInterfaceName());
    }
}
