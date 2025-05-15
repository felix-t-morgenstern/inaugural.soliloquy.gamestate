package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.GameStateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GameStateImplTests {
    @Mock private Map<String, GameMovementEvent> mockGameMovementEvents;
    @Mock private Map<String, GameAbilityEvent> mockGameAbilityEvents;
    @Mock private GameZoneRepo mockGameZoneRepo;
    @Mock private Camera mockCamera;
    @Mock private Function<Supplier<GameZone>, Camera> mockCameraFactory;
    @Mock private KeyBindingFactory mockKeyBindingFactory;
    @Mock private RoundManager mockRoundManager;
    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;
    @Mock private ClockBasedTimerManager mockClockBasedTimerManager;
    @Mock private ItemFactory mockItemFactory;
    @Mock private CharacterFactory mockCharacterFactory;
    @Mock private RoundBasedTimerFactory mockRoundBasedTimerFactory;
    @Mock private KeyBindingContextFactory mockKeyBindingContextFactory;
    @Mock private KeyEventListener mockKeyEventListener;
    @Mock private KeyEventListenerFactory mockKeyEventListenerFactory;
    @Mock private Party mockParty;
    @Mock private Map<String, Object> mockData;

    private GameState gameState;

    @BeforeEach
    public void setUp() {
        when(mockCameraFactory.apply(any())).thenReturn(mockCamera);
        when(mockKeyEventListenerFactory.make(any())).thenReturn(mockKeyEventListener);

        gameState = new GameStateImpl(mockParty,
                mockData,
                mockGameZoneRepo,
                mockCameraFactory,
                mockRoundManager,
                mockRoundBasedTimerManager,
                mockClockBasedTimerManager,
                mockItemFactory,
                mockCharacterFactory,
                mockRoundBasedTimerFactory,
                mockKeyBindingFactory,
                mockKeyBindingContextFactory,
                mockKeyEventListenerFactory);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(null,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory,
                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        null,
                        mockGameZoneRepo,
                        mockCameraFactory, mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        null,
                        mockCameraFactory, mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        null, mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory,
                        null,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory,
                        mockRoundManager,
                        null,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory,
                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        null,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory,
                        mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        null,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory, mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        null,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory, mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        null,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory, mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        null,
                        mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory, mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        null,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(mockParty,
                        mockData,
                        mockGameZoneRepo,
                        mockCameraFactory, mockRoundManager,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        mockItemFactory,
                        mockCharacterFactory,
                        mockRoundBasedTimerFactory,
                        mockKeyBindingFactory,
                        mockKeyBindingContextFactory,
                        null));
    }

    @Test
    public void testParty() {
        assertSame(mockParty, gameState.party());
    }

    @Test
    public void testCharacterAIs() {
        assertNotNull(gameState.characterAIs());
    }

    @Test
    public void testGameZoneRepo() {
        assertSame(mockGameZoneRepo, gameState.gameZoneRepo());
    }

    @Test
    public void testMovementEvents() {
        assertNotNull(gameState.movementEvents());
    }

    @Test
    public void testAbilityEvents() {
        assertNotNull(gameState.abilityEvents());
    }

    @Test
    public void testCamera() {
        assertSame(mockCamera, gameState.camera());
        verify(mockCameraFactory).apply(any());
        // TODO: Figure out how to capture instance method references
    }

    @Test
    public void testRoundManager() {
        assertSame(mockRoundManager, gameState.roundManager());
    }

    @Test
    public void testKeyBindingContexts() {
        assertNotNull(gameState.keyBindingContexts());
    }

    @Test
    public void testItemFactory() {
        assertNotNull(gameState.itemFactory());
    }

    @Test
    public void testCharacterFactory() {
        assertNotNull(gameState.characterFactory());
    }

    @Test
    public void testRoundBasedTimerManager() {
        assertSame(mockRoundBasedTimerManager, gameState.roundBasedTimerManager());
    }

    @Test
    public void testClockBasedTimerManager() {
        assertSame(mockClockBasedTimerManager, gameState.clockBasedTimerManager());
    }

    @Test
    public void testRoundBasedTimerFactory() {
        assertSame(mockRoundBasedTimerFactory, gameState.roundBasedTimerFactory());
    }

    @Test
    public void testKeyBindingFactory() {
        assertNotNull(gameState.keyBindingFactory());
    }

    @Test
    public void testKeyBindingContextFactory() {
        assertNotNull(gameState.keyBindingContextFactory());
    }

    @Test
    public void testKeyPressListenerFactory() {
        assertSame(mockKeyEventListener, gameState.keyEventListener());
        verify(mockKeyEventListenerFactory).make(null);
    }
}
