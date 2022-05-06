package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameStateImplTests {
    private final Party PARTY = new FakeParty();
    private final VariableCache PERSISTENT_VARIABLE_CACHE =
            new VariableCacheStub();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();
    private final RegistryFactory REGISTRY_FACTORY = new FakeRegistryFactory();
    private final GameZonesRepo GAME_ZONES_REPO = new GameZonesRepoStub();
    private final FakeCameraFactory CAMERA_FACTORY = new FakeCameraFactory();
    private final RoundManager ROUND_MANAGER = new FakeRoundManager();
    private final ItemFactory ITEM_FACTORY = new FakeItemFactory();
    private final CharacterFactory CHARACTER_FACTORY = new FakeCharacterFactory();
    private final FakeRoundBasedTimerFactory ROUND_BASED_TIMER_FACTORY =
            new FakeRoundBasedTimerFactory();
    private final KeyBindingFactory KEY_BINDING_FACTORY = new KeyBindingFactoryStub();
    private final KeyBindingContextFactory KEY_BINDING_CONTEXT_FACTORY =
            new KeyBindingContextFactoryStub();
    private final FakeKeyEventListenerFactory KEY_EVENT_LISTENER_FACTORY =
            new FakeKeyEventListenerFactory();
    
    @Mock
    private RoundBasedTimerManager _mockRoundBasedTimerManager;
    
    private GameState _gameState;

    @BeforeEach
    void setUp() {
        _mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);
        
        _gameState = new GameStateImpl(PARTY,
                PERSISTENT_VARIABLE_CACHE,
                MAP_FACTORY,
                REGISTRY_FACTORY,
                GAME_ZONES_REPO,
                CAMERA_FACTORY,
                ROUND_MANAGER,
                _mockRoundBasedTimerManager,
                ITEM_FACTORY,
                CHARACTER_FACTORY,
                ROUND_BASED_TIMER_FACTORY,
                KEY_BINDING_FACTORY,
                KEY_BINDING_CONTEXT_FACTORY,
                KEY_EVENT_LISTENER_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(null,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        null,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        null,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        null,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        null,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        null,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        null,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        null,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        null,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        null,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        null,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        null,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        null,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,ROUND_MANAGER,
                        _mockRoundBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameState.class.getCanonicalName(), _gameState.getInterfaceName());
    }

    @Test
    void testParty() {
        assertSame(PARTY, _gameState.party());
    }

    @Test
    void testPersistentVariables() {
        assertSame(PERSISTENT_VARIABLE_CACHE, _gameState.variableCache());
    }

    @Test
    void testCharacterAIs() {
        assertNotNull(_gameState.characterAIs());
    }

    @Test
    void testGameZonesRepo() {
        assertSame(GAME_ZONES_REPO, _gameState.gameZonesRepo());
    }

    @Test
    void testMovementEvents() {
        assertNotNull(_gameState.movementEvents());
    }

    @Test
    void testAbilityEvents() {
        assertNotNull(_gameState.abilityEvents());
    }

    @Test
    void testGetAndSetGameZone() {
        final GameZone gameZone = new FakeGameZone();

        assertNull(_gameState.getCurrentGameZone());

        _gameState.setCurrentGameZone(gameZone);

        assertSame(gameZone, _gameState.getCurrentGameZone());
    }

    @Test
    void testCamera() {
        _gameState.setCurrentGameZone(new FakeGameZone());

        assertSame(FakeCameraFactory.CAMERA, _gameState.camera());
        assertSame(CAMERA_FACTORY.GET_CURRENT_GAME_ZONE.get(), _gameState.getCurrentGameZone());
    }

    @Test
    void testRoundManager() {
        assertSame(ROUND_MANAGER, _gameState.roundManager());
    }

    @Test
    void testKeyBindingContexts() {
        assertNotNull(_gameState.keyBindingContexts());
    }

    @Test
    void testItemFactory() {
        assertNotNull(_gameState.itemFactory());
    }

    @Test
    void testCharacterFactory() {
        assertNotNull(_gameState.characterFactory());
    }

    @Test
    void testRoundBasedTimerManager() {
        assertSame(_mockRoundBasedTimerManager, _gameState.roundBasedTimerManager());
    }

    @Test
    void testRoundBasedTimerFactory() {
        assertSame(ROUND_BASED_TIMER_FACTORY, _gameState.roundBasedTimerFactory());
    }

    @Test
    void testKeyBindingFactory() {
        assertNotNull(_gameState.keyBindingFactory());
    }

    @Test
    void testKeyBindingContextFactory() {
        assertNotNull(_gameState.keyBindingContextFactory());
    }

    @Test
    void testKeyPressListenerFactory() {
        assertSame(KEY_EVENT_LISTENER_FACTORY.CREATED, _gameState.keyEventListener());
    }
}
