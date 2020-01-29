package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameStateFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameState;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.GameStateFactory;

import static org.junit.jupiter.api.Assertions.*;

class GameStateFactoryImplTests {
    private final Party PARTY = new PartyStub();
    private final VariableCache DATA = new VariableCacheStub();
    private final MapFactoryStub MAP_FACTORY = new MapFactoryStub();
    private final RegistryFactoryStub REGISTRY_FACTORY = new RegistryFactoryStub();
    private final GameZonesRepoStub GAME_ZONES_REPO = new GameZonesRepoStub();
    private final CameraFactoryStub CAMERA_FACTORY = new CameraFactoryStub();
    private final RoundManagerStub ROUND_MANAGER = new RoundManagerStub();
    private final ItemFactoryStub ITEM_FACTORY = new ItemFactoryStub();
    private final CharacterFactoryStub CHARACTER_FACTORY = new CharacterFactoryStub();
    private final TimerFactoryStub TIMER_FACTORY = new TimerFactoryStub();
    private final KeyBindingFactoryStub KEY_BINDING_FACTORY = new KeyBindingFactoryStub();
    private final KeyBindingContextFactoryStub KEY_BINDING_CONTEXT_FACTORY =
            new KeyBindingContextFactoryStub();
    private final KeyPressListenerFactoryStub KEY_PRESS_LISTENER_FACTORY =
            new KeyPressListenerFactoryStub();

    private GameStateFactory _gameStateFactory;

    @BeforeEach
    void setUp() {
        _gameStateFactory = new GameStateFactoryImpl(MAP_FACTORY, REGISTRY_FACTORY,
                GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY, CHARACTER_FACTORY,
                TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(null,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                null, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, null, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, null, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, null, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, null,
                CHARACTER_FACTORY, TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                null, TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, null, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY, null, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY, KEY_BINDING_FACTORY, null,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameStateFactory.class.getCanonicalName(),
                _gameStateFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        GameState gameState = _gameStateFactory.make(PARTY, DATA);

        assertNotNull(gameState);
        assertSame(PARTY, gameState.party());
        assertSame(DATA, gameState.variableCache());
        // TODO: Consider adding more extensive testing of constructor params
    }
}
