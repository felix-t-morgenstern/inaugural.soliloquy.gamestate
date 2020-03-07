package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.factories.*;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

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
    private final FakeTimerFactory TIMER_FACTORY = new FakeTimerFactory();
    private final Function<RoundManager, TimerFactory> TIMER_FACTORY_FACTORY = r -> {
        _roundManagerForTimerFactoryFactory = r;
        return TIMER_FACTORY;
    };
    private final KeyBindingFactory KEY_BINDING_FACTORY = new KeyBindingFactoryStub();
    private final KeyBindingContextFactory KEY_BINDING_CONTEXT_FACTORY =
            new KeyBindingContextFactoryStub();
    private final KeyPressListenerFactory KEY_PRESS_LISTENER_FACTORY =
            new KeyPressListenerFactoryStub();

    private RoundManager _roundManagerForTimerFactoryFactory;
    private GameState _gameState;

    @BeforeEach
    void setUp() {
        _roundManagerForTimerFactoryFactory = null;
        _gameState = new GameStateImpl(PARTY,
                PERSISTENT_VARIABLE_CACHE,
                MAP_FACTORY,
                REGISTRY_FACTORY,
                GAME_ZONES_REPO,
                CAMERA_FACTORY,
                ROUND_MANAGER,
                ITEM_FACTORY,
                CHARACTER_FACTORY,
                TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
                        TIMER_FACTORY_FACTORY,
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
    void testTimerFactory() {
        assertSame(TIMER_FACTORY, _gameState.timerFactory());
        assertSame(ROUND_MANAGER, _roundManagerForTimerFactoryFactory);
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
