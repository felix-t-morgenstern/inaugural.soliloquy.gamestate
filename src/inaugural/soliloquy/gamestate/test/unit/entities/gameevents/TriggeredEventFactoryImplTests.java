package inaugural.soliloquy.gamestate.test.unit.entities.gameevents;

import inaugural.soliloquy.gamestate.entities.gameevents.TriggeredEventImpl;
import inaugural.soliloquy.gamestate.factories.TriggeredEventFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.gamestate.entities.gameevents.TriggeredEvent;
import soliloquy.specs.gamestate.factories.TriggeredEventFactory;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TriggeredEventFactoryImplTests {
    @Mock
    private GameSaveBlocker _mockGameSaveBlocker;

    private TriggeredEventFactory _triggeredEventFactory;

    @BeforeEach
    void setUp() {
        _mockGameSaveBlocker = mock(GameSaveBlocker.class);

        _triggeredEventFactory = new TriggeredEventFactoryImpl(_mockGameSaveBlocker);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TriggeredEventFactoryImpl(null));
    }

    @Test
    void testMake() {
        TriggeredEvent triggeredEvent = _triggeredEventFactory.make(randomInt(), () -> {});

        assertNotNull(triggeredEvent);
        assertTrue(triggeredEvent instanceof TriggeredEventImpl);
        verify(_mockGameSaveBlocker).placeTriggeredEventBlock(triggeredEvent);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _triggeredEventFactory.make(randomInt(), null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TriggeredEventFactory.class.getCanonicalName(),
                _triggeredEventFactory.getInterfaceName());
    }
}
