package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameStateFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameState;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.factories.GameStateFactory;
import soliloquy.specs.gamestate.factories.TimerFactory;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class GameStateFactoryImplTests {
    private final Party PARTY = new FakeParty();
    private final VariableCache DATA = new VariableCacheStub();
    private final FakeMapFactory MAP_FACTORY = new FakeMapFactory();
    private final FakeRegistryFactory REGISTRY_FACTORY = new FakeRegistryFactory();
    private final GameZonesRepoStub GAME_ZONES_REPO = new GameZonesRepoStub();
    private final FakeCameraFactory CAMERA_FACTORY = new FakeCameraFactory();
    private final FakeRoundManager ROUND_MANAGER = new FakeRoundManager();
    private final FakeItemFactory ITEM_FACTORY = new FakeItemFactory();
    private final FakeCharacterFactory CHARACTER_FACTORY = new FakeCharacterFactory();
    private final FakeTimerFactory TIMER_FACTORY = new FakeTimerFactory();
    private final Function<RoundManager, TimerFactory> TIMER_FACTORY_FACTORY = r -> {
        _roundManagerForTimerFactoryFactory = r;
        return TIMER_FACTORY;
    };
    private final KeyBindingFactoryStub KEY_BINDING_FACTORY = new KeyBindingFactoryStub();
    private final KeyBindingContextFactoryStub KEY_BINDING_CONTEXT_FACTORY =
            new KeyBindingContextFactoryStub();
    private final KeyPressListenerFactoryStub KEY_PRESS_LISTENER_FACTORY =
            new KeyPressListenerFactoryStub();

    private RoundManager _roundManagerForTimerFactoryFactory;
    private GameStateFactory _gameStateFactory;

    @BeforeEach
    void setUp() {
        _gameStateFactory = new GameStateFactoryImpl(MAP_FACTORY, REGISTRY_FACTORY,
                GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY, CHARACTER_FACTORY,
                TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(null,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                null, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, null, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, null, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, null, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, null,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                null, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, null, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, null, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, null,
                KEY_PRESS_LISTENER_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new GameStateFactoryImpl(MAP_FACTORY,
                REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
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
        assertSame(TIMER_FACTORY, gameState.timerFactory());
        assertSame(ROUND_MANAGER, _roundManagerForTimerFactoryFactory);
        // TODO: Consider adding more extensive testing of constructor params
    }
}
