package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.GameZonesRepoStub;
import inaugural.soliloquy.gamestate.test.stubs.KeyBindingContextFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.KeyBindingFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.GameZonesRepo;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.CharacterFactory;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameStateImplTests {
    private final Party PARTY = new FakeParty();
    private final VariableCache PERSISTENT_VARIABLE_CACHE = new VariableCacheStub();
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

    @Mock private RoundBasedTimerManager mockRoundBasedTimerManager;
    @Mock private ClockBasedTimerManager mockClockBasedTimerManager;

    private GameState gameState;

    @BeforeEach
    void setUp() {
        mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);
        mockClockBasedTimerManager = mock(ClockBasedTimerManager.class);

        gameState = new GameStateImpl(PARTY,
                PERSISTENT_VARIABLE_CACHE,
                REGISTRY_FACTORY,
                GAME_ZONES_REPO,
                CAMERA_FACTORY,
                ROUND_MANAGER,
                mockRoundBasedTimerManager,
                mockClockBasedTimerManager,
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
                () -> new GameStateImpl(null,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        null,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        null,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        null,
                        CAMERA_FACTORY, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        null, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        null,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        null,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        null,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        null,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        null,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        null,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        null,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        null,
                        KEY_EVENT_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY, ROUND_MANAGER,
                        mockRoundBasedTimerManager,
                        mockClockBasedTimerManager,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameState.class.getCanonicalName(), gameState.getInterfaceName());
    }

    @Test
    void testParty() {
        assertSame(PARTY, gameState.party());
    }

    @Test
    void testSetAndGetVariableCache() {
        assertSame(PERSISTENT_VARIABLE_CACHE, gameState.getVariableCache());

        VariableCache newVariableCache = mock(VariableCache.class);

        gameState.setVariableCache(newVariableCache);

        assertSame(newVariableCache, gameState.getVariableCache());
    }

    @Test
    void testSetVariableCacheWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> gameState.setVariableCache(null));
    }

    @Test
    void testCharacterAIs() {
        assertNotNull(gameState.characterAIs());
    }

    @Test
    void testGameZonesRepo() {
        assertSame(GAME_ZONES_REPO, gameState.gameZonesRepo());
    }

    @Test
    void testMovementEvents() {
        assertNotNull(gameState.movementEvents());
    }

    @Test
    void testAbilityEvents() {
        assertNotNull(gameState.abilityEvents());
    }

    @Test
    void testGetAndSetGameZone() {
        final GameZone gameZone = new FakeGameZone();

        assertNull(gameState.getCurrentGameZone());

        gameState.setCurrentGameZone(gameZone);

        assertSame(gameZone, gameState.getCurrentGameZone());
    }

    @Test
    void testCamera() {
        gameState.setCurrentGameZone(new FakeGameZone());

        assertSame(FakeCameraFactory.CAMERA, gameState.camera());
        assertSame(CAMERA_FACTORY.GET_CURRENT_GAME_ZONE.get(), gameState.getCurrentGameZone());
    }

    @Test
    void testRoundManager() {
        assertSame(ROUND_MANAGER, gameState.roundManager());
    }

    @Test
    void testKeyBindingContexts() {
        assertNotNull(gameState.keyBindingContexts());
    }

    @Test
    void testItemFactory() {
        assertNotNull(gameState.itemFactory());
    }

    @Test
    void testCharacterFactory() {
        assertNotNull(gameState.characterFactory());
    }

    @Test
    void testRoundBasedTimerManager() {
        assertSame(mockRoundBasedTimerManager, gameState.roundBasedTimerManager());
    }

    @Test
    void testClockBasedTimerManager() {
        assertSame(mockClockBasedTimerManager, gameState.clockBasedTimerManager());
    }

    @Test
    void testRoundBasedTimerFactory() {
        assertSame(ROUND_BASED_TIMER_FACTORY, gameState.roundBasedTimerFactory());
    }

    @Test
    void testKeyBindingFactory() {
        assertNotNull(gameState.keyBindingFactory());
    }

    @Test
    void testKeyBindingContextFactory() {
        assertNotNull(gameState.keyBindingContextFactory());
    }

    @Test
    void testKeyPressListenerFactory() {
        assertSame(KEY_EVENT_LISTENER_FACTORY.CREATED, gameState.keyEventListener());
    }
}
