package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterAITypeArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameAbilityEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameMovementEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.KeyBindingContextArchetype;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.Ruleset;
import soliloquy.specs.ruleset.entities.CharacterAIType;

public class GameStateImpl implements GameState {
    private final Party PARTY;
    private final VariableCache VARIABLE_CACHE;
    private final Map<String, CharacterAIType> CHARACTER_AI_TYPES;
    private final GameZonesRepo GAME_ZONES_REPO;
    private final Registry<GameMovementEvent> MOVEMENT_EVENTS;
    private final Registry<GameAbilityEvent> ABILITY_EVENTS;
    private final Camera CAMERA;
    private final RoundManager ROUND_MANAGER;
    private final Map<Integer, KeyBindingContext> KEY_BINDING_CONTEXTS;
    private final Ruleset RULESET;
    private final GameZoneFactory GAME_ZONE_FACTORY;
    private final TileFactory TILE_FACTORY;
    private final ItemFactory ITEM_FACTORY;
    private final CharacterFactory CHARACTER_FACTORY;
    private final TimerFactory TIMER_FACTORY;
    private final KeyBindingFactory KEY_BINDING_FACTORY;
    private final KeyBindingContextFactory KEY_BINDING_CONTEXT_FACTORY;
    private final KeyPressListenerFactory KEY_PRESS_LISTENER_FACTORY;

    private final static KeyBindingContext KEY_BINDING_CONTEXT_ARCHETYPE =
            new KeyBindingContextArchetype();
    private final static CharacterAIType CHARACTER_AI_TYPE_ARCHETYPE =
            new CharacterAITypeArchetype();
    private final static GameMovementEvent GAME_MOVEMENT_EVENT_ARCHETYPE =
            new GameMovementEventArchetype();
    private final static GameAbilityEvent GAME_ABILITY_EVENT_ARCHETYPE =
            new GameAbilityEventArchetype();

    private GameZone _currentGameZone;

    @SuppressWarnings("ConstantConditions")
    public GameStateImpl(Party party,
                         VariableCache persistentVariableCache,
                         MapFactory mapFactory,
                         RegistryFactory registryFactory,
                         GameZonesRepo gameZonesRepo,
                         Camera camera,
                         RoundManager roundManager,
                         Ruleset ruleset,
                         GameZoneFactory gameZoneFactory,
                         TileFactory tileFactory,
                         ItemFactory itemFactory,
                         CharacterFactory characterFactory,
                         TimerFactory timerFactory,
                         KeyBindingFactory keyBindingFactory,
                         KeyBindingContextFactory keyBindingContextFactory,
                         KeyPressListenerFactory keyPressListenerFactory) {
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
        CHARACTER_AI_TYPES = mapFactory.make("", CHARACTER_AI_TYPE_ARCHETYPE);
        if (gameZonesRepo == null) {
            throw new IllegalArgumentException("GameState: gameZonesRepo must be non-null");
        }
        GAME_ZONES_REPO = gameZonesRepo;
        if (camera == null) {
            throw new IllegalArgumentException("GameState: camera must be non-null");
        }
        CAMERA = camera;
        if (registryFactory == null) {
            throw new IllegalArgumentException("GameState: registryFactory must be non-null");
        }
        MOVEMENT_EVENTS = registryFactory.make(GAME_MOVEMENT_EVENT_ARCHETYPE);
        ABILITY_EVENTS = registryFactory.make(GAME_ABILITY_EVENT_ARCHETYPE);
        if (roundManager == null) {
            throw new IllegalArgumentException("GameState: roundManager must be non-null");
        }
        ROUND_MANAGER = roundManager;
        KEY_BINDING_CONTEXTS = mapFactory.make(0, KEY_BINDING_CONTEXT_ARCHETYPE);
        if (ruleset == null) {
            throw new IllegalArgumentException("GameState: ruleset must be non-null");
        }
        RULESET = ruleset;
        if (gameZoneFactory == null) {
            throw new IllegalArgumentException("GameState: gameZoneFactory must be non-null");
        }
        GAME_ZONE_FACTORY = gameZoneFactory;
        if (tileFactory == null) {
            throw new IllegalArgumentException("GameState: tileFactory must be non-null");
        }
        TILE_FACTORY = tileFactory;
        if (itemFactory == null) {
            throw new IllegalArgumentException("GameState: itemFactory must be non-null");
        }
        ITEM_FACTORY = itemFactory;
        if (characterFactory == null) {
            throw new IllegalArgumentException("GameState: characterFactory must be non-null");
        }
        CHARACTER_FACTORY = characterFactory;
        if (timerFactory == null) {
            throw new IllegalArgumentException("GameState: timerFactory must be non-null");
        }
        TIMER_FACTORY = timerFactory;
        if (keyBindingFactory == null) {
            throw new IllegalArgumentException("GameState: keyBindingFactory must be non-null");
        }
        KEY_BINDING_FACTORY = keyBindingFactory;
        if (keyBindingContextFactory == null) {
            throw new IllegalArgumentException(
                    "GameState: keyBindingContextFactory must be non-null");
        }
        KEY_BINDING_CONTEXT_FACTORY = keyBindingContextFactory;
        if (keyPressListenerFactory == null) {
            throw new IllegalArgumentException(
                    "GameState: keyPressListenerFactory must be non-null");
        }
        KEY_PRESS_LISTENER_FACTORY = keyPressListenerFactory;
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
    public Camera camera() {
        return CAMERA;
    }

    @Override
    public Registry<GameMovementEvent> movementEvents() {
        return MOVEMENT_EVENTS;
    }

    @Override
    public Registry<GameAbilityEvent> abilityEvents() {
        return ABILITY_EVENTS;
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
    public GameZoneFactory gameZoneFactory() {
        return  GAME_ZONE_FACTORY;
    }

    @Override
    public TileFactory tileFactory() {
        return TILE_FACTORY;
    }

    @Override
    public ItemFactory itemFactory() {
        return ITEM_FACTORY;
    }

    @Override
    public CharacterFactory characterFactory() {
        return CHARACTER_FACTORY;
    }

    @Override
    public TimerFactory timerFactory() {
        return TIMER_FACTORY;
    }

    @Override
    public KeyBindingFactory keyBindingFactory() {
        return KEY_BINDING_FACTORY;
    }

    @Override
    public KeyBindingContextFactory keyBindingContextFactory() {
        return KEY_BINDING_CONTEXT_FACTORY;
    }

    @Override
    public KeyPressListenerFactory keyPressListenerFactory() {
        return KEY_PRESS_LISTENER_FACTORY;
    }

    @Override
    public String getInterfaceName() {
        return GameState.class.getCanonicalName();
    }
}
