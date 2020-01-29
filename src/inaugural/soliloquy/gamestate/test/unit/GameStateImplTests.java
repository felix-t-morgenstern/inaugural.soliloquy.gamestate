package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.factories.*;

import static org.junit.jupiter.api.Assertions.*;

class GameStateImplTests {
    private final Party PARTY = new PartyStub();
    private final VariableCache PERSISTENT_VARIABLE_CACHE =
            new VariableCacheStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final RegistryFactory REGISTRY_FACTORY = new RegistryFactoryStub();
    private final GameZonesRepo GAME_ZONES_REPO = new GameZonesRepoStub();
    private final CameraFactoryStub CAMERA_FACTORY = new CameraFactoryStub();
    private final RoundManager ROUND_MANAGER = new RoundManagerStub();
    private final ItemFactory ITEM_FACTORY = new ItemFactoryStub();
    private final CharacterFactory CHARACTER_FACTORY = new CharacterFactoryStub();
    private final TimerFactory TIMER_FACTORY = new TimerFactoryStub();
    private final KeyBindingFactory KEY_BINDING_FACTORY = new KeyBindingFactoryStub();
    private final KeyBindingContextFactory KEY_BINDING_CONTEXT_FACTORY =
            new KeyBindingContextFactoryStub();
    private final KeyPressListenerFactory KEY_PRESS_LISTENER_FACTORY =
            new KeyPressListenerFactoryStub();

    private GameState _gameState;

    @BeforeEach
    void setUp() {
        _gameState = new GameStateImpl(PARTY,
                PERSISTENT_VARIABLE_CACHE,
                MAP_FACTORY,
                REGISTRY_FACTORY,
                GAME_ZONES_REPO,
                CAMERA_FACTORY,
                ROUND_MANAGER,
                ITEM_FACTORY,
                CHARACTER_FACTORY,
                TIMER_FACTORY,
                KEY_BINDING_FACTORY,
                KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
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
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        null,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        null,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        null,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        null,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        null,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        null,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        null,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        null,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        null,
                        KEY_BINDING_FACTORY,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        null,
                        KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
                        KEY_BINDING_FACTORY,
                        null,
                        KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        CAMERA_FACTORY,
                        ROUND_MANAGER,
                        ITEM_FACTORY,
                        CHARACTER_FACTORY,
                        TIMER_FACTORY,
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
        final GameZone gameZone = new GameZoneStub();

        assertNull(_gameState.getCurrentGameZone());

        _gameState.setCurrentGameZone(gameZone);

        assertSame(gameZone, _gameState.getCurrentGameZone());
    }

    @Test
    void testCamera() {
        _gameState.setCurrentGameZone(new GameZoneStub());

        assertSame(CameraFactoryStub.CAMERA, _gameState.camera());
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
    void testTimerFactory() {
        assertNotNull(_gameState.timerFactory());
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
        assertNotNull(_gameState.keyPressListenerFactory());
    }
}
