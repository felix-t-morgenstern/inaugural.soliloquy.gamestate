package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.gamestate.factories.GameStateFactoryImpl;
import inaugural.soliloquy.tools.collections.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class GameStateFactoryImplTests {
    @Mock private GameZoneRepo mockGameZoneRepo;
    @Mock private Function<Supplier<GameZone>, Camera> mockCameraFactory;
    @Mock private KeyBindingFactory mockKeyBindingFactory;
    @Mock private RoundManager mockRoundManager;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;
    @Mock private ClockBasedTimerManager mockClockBasedTimerManager;
    @Mock private ItemFactory mockItemFactory;
    @Mock private CharacterFactory mockCharacterFactory;
    @Mock private RoundBasedTimerFactory mockRoundBasedTimerFactory;
    @Mock private KeyBindingContextFactory mockKeyBindingContextFactory;
    @Mock private KeyEventListenerFactory mockKeyEventListenerFactory;
    @Mock private Party mockParty;

    private GameStateFactory gameStateFactory;

    @BeforeEach
    public void setUp() {
        mockKeyBindingFactory = mock(KeyBindingFactory.class);
        mockRoundManager = mock(RoundManager.class);
        mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);
        mockClockBasedTimerManager = mock(ClockBasedTimerManager.class);

        gameStateFactory =
                new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(null, mockCameraFactory, mockRoundManager,
                        mockRoundBasedTimerManager, mockClockBasedTimerManager, mockItemFactory,
                        mockCharacterFactory, mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, null, mockRoundManager,
                        mockRoundBasedTimerManager, mockClockBasedTimerManager, mockItemFactory,
                        mockCharacterFactory, mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory, null,
                        mockRoundBasedTimerManager, mockClockBasedTimerManager, mockItemFactory,
                        mockCharacterFactory, mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, null, mockClockBasedTimerManager, mockItemFactory,
                        mockCharacterFactory, mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, null, mockItemFactory,
                        mockCharacterFactory, mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        null, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, null, mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, null, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory, null,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, null, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockGameZoneRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory, null));
    }

    @Test
    public void testMake() {
        var data = Collections.<String, Object>mapOf(pairOf(randomString(), randomString()));

        var gameState = gameStateFactory.make(mockParty, data);

        assertNotNull(gameState);
        assertTrue(gameState instanceof GameStateImpl);
        assertSame(mockParty, gameState.party());
        assertEquals(data, gameState.data());
        // TODO: Consider adding more extensive testing of constructor params
    }
}
