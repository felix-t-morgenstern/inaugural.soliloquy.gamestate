package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterAITypeArchetype;
import inaugural.soliloquy.gamestate.archetypes.KeyBindingContextArchetype;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.infrastructure.IPersistentVariableCache;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.gamestate.entities.IGameZone;
import soliloquy.specs.gamestate.entities.IKeyBindingContext;
import soliloquy.specs.gamestate.entities.IParty;
import soliloquy.specs.gamestate.entities.IRoundManager;
import soliloquy.specs.gamestate.valueobjects.IGameState;
import soliloquy.specs.gamestate.valueobjects.IGameZonesRepo;
import soliloquy.specs.ruleset.IRuleset;
import soliloquy.specs.ruleset.entities.ICharacterAIType;

public class GameState implements IGameState {
    private final IParty PARTY;
    private final IPersistentVariableCache PERSISTENT_VARIABLE_CACHE;
    private final IMap<String, ICharacterAIType> CHARACTER_AI_TYPES;
    private final IGameZonesRepo GAME_ZONES_REPO;
    private final IRoundManager ROUND_MANAGER;
    private final IMap<Integer, IKeyBindingContext> KEY_BINDING_CONTEXTS;
    private final IRuleset RULESET;

    private IGameZone _currentGameZone;

    @SuppressWarnings("ConstantConditions")
    public GameState(IParty party,
                     IPersistentVariableCache persistentVariableCache,
                     IMapFactory mapFactory,
                     IGameZonesRepo gameZonesRepo,
                     IRoundManager roundManager,
                     IRuleset ruleset) {
        if (party == null) {
            throw new IllegalArgumentException("GameState: party must be non-null");
        }
        PARTY = party;
        if (persistentVariableCache == null) {
            throw new IllegalArgumentException(
                    "GameState: persistentVariableCache must be non-null");
        }
        PERSISTENT_VARIABLE_CACHE = persistentVariableCache;
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
    public IParty party() throws IllegalStateException {
        return PARTY;
    }

    @Override
    public IPersistentVariableCache persistentVariables() {
        return PERSISTENT_VARIABLE_CACHE;
    }

    @Override
    public IMap<String, ICharacterAIType> characterAIs() {
        return CHARACTER_AI_TYPES;
    }

    @Override
    public IGameZonesRepo gameZonesRepo() {
        return GAME_ZONES_REPO;
    }

    @Override
    public IGameZone getCurrentGameZone() {
        return _currentGameZone;
    }

    @Override
    public void setCurrentGameZone(IGameZone gameZone) {
        _currentGameZone = gameZone;
    }

    @Override
    public IRoundManager roundManager() {
        return ROUND_MANAGER;
    }

    @Override
    public IMap<Integer, IKeyBindingContext> keyBindingContexts() throws IllegalStateException {
        return KEY_BINDING_CONTEXTS;
    }

    @Override
    public IRuleset ruleset() {
        return RULESET;
    }

    @Override
    public String getInterfaceName() {
        return IGameState.class.getCanonicalName();
    }
}
