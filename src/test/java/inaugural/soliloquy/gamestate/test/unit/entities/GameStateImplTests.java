package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.GameStateImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;

import java.util.function.Function;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GameStateImplTests {
    @Mock private Registry<GameMovementEvent> mockGameMovementEvents;
    @Mock private Registry<GameAbilityEvent> mockGameAbilityEvents;
    @Mock private RegistryFactory mockRegistryFactory;
    @Mock private GameZonesRepo mockGameZonesRepo;
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
    @Mock private VariableCache mockData;

    private GameState gameState;

    @Before
    public void setUp() {
        //noinspection unchecked,rawtypes
        when(mockRegistryFactory.make(any()))
                .thenReturn((Registry) mockGameMovementEvents)
                .thenReturn(mockGameAbilityEvents);
        when(mockCameraFactory.apply(any())).thenReturn(mockCamera);
        when(mockKeyEventListenerFactory.make(any())).thenReturn(mockKeyEventListener);

        gameState = new GameStateImpl(mockParty,
                mockData,
                mockRegistryFactory,
                mockGameZonesRepo,
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
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(null,
                        mockData,
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
                        mockRegistryFactory,
                        mockGameZonesRepo,
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
    public void testGetInterfaceName() {
        assertEquals(GameState.class.getCanonicalName(), gameState.getInterfaceName());
    }

    @Test
    public void testParty() {
        assertSame(mockParty, gameState.party());
    }

    @Test
    public void testSetAndGetVariableCache() {
        assertSame(mockData, gameState.getVariableCache());

        var newVariableCache = mock(VariableCache.class);

        gameState.setVariableCache(newVariableCache);

        assertSame(newVariableCache, gameState.getVariableCache());
    }

    @Test
    public void testSetVariableCacheWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> gameState.setVariableCache(null));
    }

    @Test
    public void testCharacterAIs() {
        assertNotNull(gameState.characterAIs());
    }

    @Test
    public void testGameZonesRepo() {
        assertSame(mockGameZonesRepo, gameState.gameZonesRepo());
    }

    @Test
    public void testMovementEvents() {
        assertNotNull(gameState.movementEvents());
        assertSame(mockGameMovementEvents, gameState.movementEvents());
    }

    @Test
    public void testAbilityEvents() {
        assertNotNull(gameState.abilityEvents());
        assertSame(mockGameAbilityEvents, gameState.abilityEvents());
    }

    @Test
    public void testGetAndSetGameZone() {
        var mockGameZone = mock(GameZone.class);

        assertNull(gameState.getCurrentGameZone());

        gameState.setCurrentGameZone(mockGameZone);

        assertSame(mockGameZone, gameState.getCurrentGameZone());
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
