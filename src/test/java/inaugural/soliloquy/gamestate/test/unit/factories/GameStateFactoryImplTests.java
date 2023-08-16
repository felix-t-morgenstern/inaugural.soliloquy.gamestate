package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.gamestate.factories.GameStateFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class GameStateFactoryImplTests {
    @Mock private RegistryFactory mockRegistryFactory;
    @Mock private GameZonesRepo mockGameZonesRepo;
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
    @Mock private VariableCache mockData;

    private GameStateFactory gameStateFactory;

    @Before
    public void setUp() {
        mockKeyBindingFactory = mock(KeyBindingFactory.class);
        mockRoundManager = mock(RoundManager.class);
        mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);
        mockClockBasedTimerManager = mock(ClockBasedTimerManager.class);

        gameStateFactory =
                new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(null, mockGameZonesRepo, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, null, mockCameraFactory,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo, null,
                        mockRoundManager, mockRoundBasedTimerManager, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, null, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, mockItemFactory, mockCharacterFactory,
                        mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, mockRoundManager, null, mockClockBasedTimerManager,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, mockRoundManager, mockRoundBasedTimerManager, null,
                        mockItemFactory, mockCharacterFactory, mockRoundBasedTimerFactory,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, mockRoundManager, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, null, mockCharacterFactory,
                        mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, mockRoundManager, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, mockItemFactory, null,
                        mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, mockRoundManager, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, mockItemFactory, mockCharacterFactory, null,
                        mockKeyBindingFactory, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, mockRoundManager, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, mockItemFactory, mockCharacterFactory,
                        mockRoundBasedTimerFactory, null, mockKeyBindingContextFactory,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, mockRoundManager, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, mockItemFactory, mockCharacterFactory,
                        mockRoundBasedTimerFactory, mockKeyBindingFactory, null,
                        mockKeyEventListenerFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateFactoryImpl(mockRegistryFactory, mockGameZonesRepo,
                        mockCameraFactory, mockRoundManager, mockRoundBasedTimerManager,
                        mockClockBasedTimerManager, mockItemFactory, mockCharacterFactory,
                        mockRoundBasedTimerFactory, mockKeyBindingFactory,
                        mockKeyBindingContextFactory, null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(GameStateFactory.class.getCanonicalName(),
                gameStateFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        var gameState = gameStateFactory.make(mockParty, mockData);

        assertNotNull(gameState);
        assertTrue(gameState instanceof GameStateImpl);
        assertSame(mockParty, gameState.party());
        assertSame(mockData, gameState.getVariableCache());
        // TODO: Consider adding more extensive testing of constructor params
    }
}
