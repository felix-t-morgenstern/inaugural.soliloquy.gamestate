package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterAITypeArchetype;
import inaugural.soliloquy.gamestate.archetypes.KeyBindingContextArchetype;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.valueobjects.GameState;
import soliloquy.specs.gamestate.valueobjects.GameZonesRepo;
import soliloquy.specs.ruleset.Ruleset;
import soliloquy.specs.ruleset.entities.CharacterAIType;

public class GameStateImpl implements GameState {
    private final Party PARTY;
    private final VariableCache VARIABLE_CACHE;
    private final Map<String, CharacterAIType> CHARACTER_AI_TYPES;
    private final GameZonesRepo GAME_ZONES_REPO;
    private final RoundManager ROUND_MANAGER;
    private final Map<Integer, KeyBindingContext> KEY_BINDING_CONTEXTS;
    private final Ruleset RULESET;

    private GameZone _currentGameZone;

    @SuppressWarnings("ConstantConditions")
    public GameStateImpl(Party party,
                         VariableCache persistentVariableCache,
                         MapFactory mapFactory,
                         GameZonesRepo gameZonesRepo,
                         RoundManager roundManager,
                         Ruleset ruleset) {
        if (party == null) {
            throw new IllegalArgumentException("GameState: party must be non-null");
        }
        PARTY = party;
        if (persistentVariableCache == null) {
            throw new IllegalArgumentException(
                    "GameState: persistentVariableCache must be non-null");
        }
        VARIABLE_CACHE = persistentVariableCache;
        if (mapFactory == null) {
            throw new IllegalArgumentException("GameState: mapFactory must be non-null");
        }
        CHARACTER_AI_TYPES = mapFactory.make("", new CharacterAITypeArchetype());
        if (gameZonesRepo == null) {
            throw new IllegalArgumentException("GameState: gameZonesRepo must be non-null");
        }
        GAME_ZONES_REPO = gameZonesRepo;
        if (roundManager == null) {
            throw new IllegalArgumentException("GameState: roundManager must be non-null");
        }
        ROUND_MANAGER = roundManager;
        KEY_BINDING_CONTEXTS = mapFactory.make(0, new KeyBindingContextArchetype());
        if (ruleset == null) {
            throw new IllegalArgumentException("GameState: ruleset must be non-null");
        }
        RULESET = ruleset;
    }

    @Override
    public Party party() throws IllegalStateException {
        return PARTY;
    }

    @Override
    public VariableCache variableCache() {
        return VARIABLE_CACHE;
    }

    @Override
    public Map<String, CharacterAIType> characterAIs() {
        return CHARACTER_AI_TYPES;
    }

    @Override
    public GameZonesRepo gameZonesRepo() {
        return GAME_ZONES_REPO;
    }

    @Override
    public GameZone getCurrentGameZone() {
        return _currentGameZone;
    }

    @Override
    public void setCurrentGameZone(GameZone gameZone) {
        _currentGameZone = gameZone;
    }

    @Override
    public RoundManager roundManager() {
        return ROUND_MANAGER;
    }

    @Override
    public Map<Integer, KeyBindingContext> keyBindingContexts() throws IllegalStateException {
        return KEY_BINDING_CONTEXTS;
    }

    @Override
    public Ruleset ruleset() {
        return RULESET;
    }

    @Override
    public String getInterfaceName() {
        return GameState.class.getCanonicalName();
    }
}
