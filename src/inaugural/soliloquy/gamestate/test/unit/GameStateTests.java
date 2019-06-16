package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameState;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.IPersistentVariableCache;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.gamestate.entities.IGameZone;
import soliloquy.specs.gamestate.entities.IParty;
import soliloquy.specs.gamestate.entities.IRoundManager;
import soliloquy.specs.gamestate.valueobjects.IGameState;
import soliloquy.specs.gamestate.valueobjects.IGameZonesRepo;
import soliloquy.specs.ruleset.IRuleset;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTests {
    private final IParty PARTY = new PartyStub();
    private final IPersistentVariableCache PERSISTENT_VARIABLE_CACHE =
            new PersistentVariableCacheStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();
    private final IGameZonesRepo GAME_ZONES_REPO = new GameZonesRepoStub();
    private final IRoundManager ROUND_MANAGER = new RoundManagerStub();
    private final IRuleset RULESET = new RulesetStub();

    private IGameState _gameState;

    @BeforeEach
    void setUp() {
        _gameState = new GameState(PARTY,
                PERSISTENT_VARIABLE_CACHE,
                MAP_FACTORY,
                GAME_ZONES_REPO,
                ROUND_MANAGER,
                RULESET);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameState(null,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        GAME_ZONES_REPO,
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameState(PARTY,
                        null,
                        MAP_FACTORY,
                        GAME_ZONES_REPO,
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameState(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        null,
                        GAME_ZONES_REPO,
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameState(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        null,
                        ROUND_MANAGER,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameState(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        GAME_ZONES_REPO,
                        null,
                        RULESET));
        assertThrows(IllegalArgumentException.class,
                () -> _gameState = new GameState(PARTY,
                        PERSISTENT_VARIABLE_CACHE,
                        MAP_FACTORY,
                        GAME_ZONES_REPO,
                        ROUND_MANAGER,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IGameState.class.getCanonicalName(), _gameState.getInterfaceName());
    }

    @Test
    void testParty() {
        assertSame(PARTY, _gameState.party());
    }

    @Test
    void testPersistentVariables() {
        assertSame(PERSISTENT_VARIABLE_CACHE, _gameState.persistentVariables());
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
    void testGetAndSetGameZone() {
        final IGameZone gameZone = new GameZoneStub();

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
