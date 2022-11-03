package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.GameAbilityEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameMovementEventArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;

import java.util.HashMap;
import java.util.Map;

public class GameStateImpl implements GameState {
    private final Party PARTY;
    private final Map<String, CharacterAIType> CHARACTER_AI_TYPES;
    private final GameZonesRepo GAME_ZONES_REPO;
    private final Registry<GameMovementEvent> MOVEMENT_EVENTS;
    private final Registry<GameAbilityEvent> ABILITY_EVENTS;
    private final Camera CAMERA;
    private final RoundManager ROUND_MANAGER;
    private final RoundBasedTimerManager ROUND_BASED_TIMER_MANAGER;
    private final ClockBasedTimerManager CLOCK_BASED_TIMER_MANAGER;
    private final Map<Integer, KeyBindingContext> KEY_BINDING_CONTEXTS;
    private final ItemFactory ITEM_FACTORY;
    private final CharacterFactory CHARACTER_FACTORY;
    private final RoundBasedTimerFactory ROUND_BASED_TIMER_FACTORY;
    private final KeyBindingFactory KEY_BINDING_FACTORY;
    private final KeyBindingContextFactory KEY_BINDING_CONTEXT_FACTORY;
    private final KeyEventListener KEY_EVENT_LISTENER;

    private final static GameMovementEvent GAME_MOVEMENT_EVENT_ARCHETYPE =
            new GameMovementEventArchetype();
    private final static GameAbilityEvent GAME_ABILITY_EVENT_ARCHETYPE =
            new GameAbilityEventArchetype();

    private VariableCache data;
    private GameZone currentGameZone;

    public GameStateImpl(Party party,
                         VariableCache data,
                         RegistryFactory registryFactory,
                         GameZonesRepo gameZonesRepo,
                         CameraFactory cameraFactory,
                         RoundManager roundManager,
                         RoundBasedTimerManager roundBasedTimerManager,
                         ClockBasedTimerManager clockBasedTimerManager,
                         ItemFactory itemFactory,
                         CharacterFactory characterFactory,
                         RoundBasedTimerFactory roundBasedTimerFactory,
                         KeyBindingFactory keyBindingFactory,
                         KeyBindingContextFactory keyBindingContextFactory,
                         KeyEventListenerFactory keyEventListenerFactory) {
        PARTY = Check.ifNull(party, "party");
        this.data = Check.ifNull(data, "data");
        CHARACTER_AI_TYPES = new HashMap<>();
        GAME_ZONES_REPO = Check.ifNull(gameZonesRepo, "gameZonesRepo");
        CAMERA = Check.ifNull(cameraFactory, "cameraFactory").make(this::getCurrentGameZone);
        Check.ifNull(registryFactory, "registryFactory");
        MOVEMENT_EVENTS = registryFactory.make(GAME_MOVEMENT_EVENT_ARCHETYPE);
        ABILITY_EVENTS = registryFactory.make(GAME_ABILITY_EVENT_ARCHETYPE);
        ROUND_MANAGER = Check.ifNull(roundManager, "roundManager");
        ROUND_BASED_TIMER_MANAGER = Check.ifNull(roundBasedTimerManager, "roundBasedTimerManager");
        CLOCK_BASED_TIMER_MANAGER = Check.ifNull(clockBasedTimerManager, "clockBasedTimerManager");
        KEY_BINDING_CONTEXTS = new HashMap<>();
        ITEM_FACTORY = Check.ifNull(itemFactory, "itemFactory");
        CHARACTER_FACTORY = Check.ifNull(characterFactory, "characterFactory");
        ROUND_BASED_TIMER_FACTORY = Check.ifNull(roundBasedTimerFactory, "roundBasedTimerFactory");
        KEY_BINDING_FACTORY = Check.ifNull(keyBindingFactory, "keyBindingFactory");
        KEY_BINDING_CONTEXT_FACTORY = Check.ifNull(keyBindingContextFactory,
                "keyBindingContextFactory");
        // TODO: Ensure that most recent timestamp info is fed into this class!
        KEY_EVENT_LISTENER = Check.ifNull(keyEventListenerFactory,
                "keyEventListenerFactory").make(null);
    }

    @Override
    public Party party() throws IllegalStateException {
        return PARTY;
    }

    @Override
    public VariableCache getVariableCache() {
        return data;
    }

    @Override
    public void setVariableCache(VariableCache variableCache) throws IllegalArgumentException {
        data = Check.ifNull(variableCache, "variableCache");
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
        return currentGameZone;
    }

    @Override
    public void setCurrentGameZone(GameZone gameZone) {
        currentGameZone = gameZone;
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
    public RoundBasedTimerManager roundBasedTimerManager() {
        return ROUND_BASED_TIMER_MANAGER;
    }

    @Override
    public ClockBasedTimerManager clockBasedTimerManager() {
        return CLOCK_BASED_TIMER_MANAGER;
    }

    @Override
    public Map<Integer, KeyBindingContext> keyBindingContexts() throws IllegalStateException {
        return KEY_BINDING_CONTEXTS;
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
    public RoundBasedTimerFactory roundBasedTimerFactory() {
        return ROUND_BASED_TIMER_FACTORY;
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
    public KeyEventListener keyEventListener() {
        return KEY_EVENT_LISTENER;
    }

    @Override
    public String getInterfaceName() {
        return GameState.class.getCanonicalName();
    }
}
