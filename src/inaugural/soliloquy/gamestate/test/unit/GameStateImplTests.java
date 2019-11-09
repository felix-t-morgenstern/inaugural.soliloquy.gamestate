package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.ruleset.Ruleset;

import static org.junit.jupiter.api.Assertions.*;

class GameStateImplTests {
    private final Party PARTY = new PartyStub();
    private final VariableCache PERSISTENT_VARIABLE_CACHE =
            new VariableCacheStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final RegistryFactory REGISTRY_FACTORY = new RegistryFactoryStub();
    private final GameZonesRepo GAME_ZONES_REPO = new GameZonesRepoStub();
    private final RoundManager ROUND_MANAGER = new RoundManagerStub();
    private final Ruleset RULESET = new RulesetStub();

    private GameState _gameState;

    @BeforeEach
    void setUp() {
        _gameState = new GameStateImpl(PARTY,
                PERSISTENT_VARIABLE_CACHE,
                MAP_FACTORY,
                REGISTRY_FACTORY,
                GAME_ZONES_REPO,
                ROUND_MANAGER,
                RULESET);
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
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        null,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        null,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        null,
                        GAME_ZONES_REPO,
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        null,
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        null,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameStateImpl(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        REGISTRY_FACTORY,
                        GAME_ZONES_REPO,
                        ROUND_MANAGER,
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
    void testRoundManager() {
        assertSame(ROUND_MANAGER, _gameState.roundManager());
    }

    @Test
    void testKeyBindingContexts() {
        assertNotNull(_gameState.keyBindingContexts());
    }

    @Test
    void testRuleset() {
        assertSame(RULESET, _gameState.ruleset());
    }
}
