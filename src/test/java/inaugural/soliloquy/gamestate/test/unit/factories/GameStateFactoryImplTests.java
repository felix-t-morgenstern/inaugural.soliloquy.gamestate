package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.GameStateFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.GameStateFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameStateFactoryImplTests {
    private final Party PARTY = new FakeParty();
    private final VariableCache DATA = new VariableCacheStub();
    private final FakeRegistryFactory REGISTRY_FACTORY = new FakeRegistryFactory();
    private final GameZonesRepoStub GAME_ZONES_REPO = new GameZonesRepoStub();
    private final FakeCameraFactory CAMERA_FACTORY = new FakeCameraFactory();
    private final FakeRoundManager ROUND_MANAGER = new FakeRoundManager();
    private final FakeItemFactory ITEM_FACTORY = new FakeItemFactory();
    private final FakeCharacterFactory CHARACTER_FACTORY = new FakeCharacterFactory();
    private final FakeRoundBasedTimerFactory ROUND_BASED_TIMER_FACTORY =
            new FakeRoundBasedTimerFactory();
    private final KeyBindingFactoryStub KEY_BINDING_FACTORY = new KeyBindingFactoryStub();
    private final KeyBindingContextFactoryStub KEY_BINDING_CONTEXT_FACTORY =
            new KeyBindingContextFactoryStub();
    private final KeyEventListenerFactoryStub KEY_PRESS_LISTENER_FACTORY =
            new KeyEventListenerFactoryStub();

    @Mock
    private RoundBasedTimerManager _mockRoundBasedTimerManager;

    private GameStateFactory _gameStateFactory;

    @BeforeEach
    void setUp() {
        _mockRoundBasedTimerManager = mock(RoundBasedTimerManager.class);

        _gameStateFactory =
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY,
                        ROUND_MANAGER, _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(null, GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER,
                        _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, null, CAMERA_FACTORY, ROUND_MANAGER,
                        _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, null, ROUND_MANAGER,
                        _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY, null,
                        _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY,
                        ROUND_MANAGER, null, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY,
                        ROUND_MANAGER, _mockRoundBasedTimerManager, null, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY,
                        ROUND_MANAGER, _mockRoundBasedTimerManager, ITEM_FACTORY, null,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY,
                        ROUND_MANAGER, _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        null, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY,
                        ROUND_MANAGER, _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, null, KEY_BINDING_CONTEXT_FACTORY,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY,
                        ROUND_MANAGER, _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, null,
                        KEY_PRESS_LISTENER_FACTORY
                ));
        assertThrows(IllegalArgumentException.class, () ->
                new GameStateFactoryImpl(REGISTRY_FACTORY, GAME_ZONES_REPO, CAMERA_FACTORY,
                        ROUND_MANAGER, _mockRoundBasedTimerManager, ITEM_FACTORY, CHARACTER_FACTORY,
                        ROUND_BASED_TIMER_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                        null
                ));
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
