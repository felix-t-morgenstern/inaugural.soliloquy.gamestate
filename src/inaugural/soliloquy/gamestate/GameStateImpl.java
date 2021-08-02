package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.CharacterAITypeArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameAbilityEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameMovementEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.KeyBindingContextArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.CharacterAIType;

import java.util.function.Function;

public class GameStateImpl implements GameState {
    private final Party PARTY;
    private final VariableCache DATA;
    private final Map<String, CharacterAIType> CHARACTER_AI_TYPES;
    private final GameZonesRepo GAME_ZONES_REPO;
    private final Registry<GameMovementEvent> MOVEMENT_EVENTS;
    private final Registry<GameAbilityEvent> ABILITY_EVENTS;
    private final Camera CAMERA;
    private final RoundManager ROUND_MANAGER;
    private final Map<Integer, KeyBindingContext> KEY_BINDING_CONTEXTS;
    private final ItemFactory ITEM_FACTORY;
    private final CharacterFactory CHARACTER_FACTORY;
    private final TurnBasedTimerFactory TIMER_FACTORY;
    private final KeyBindingFactory KEY_BINDING_FACTORY;
    private final KeyBindingContextFactory KEY_BINDING_CONTEXT_FACTORY;
    private final KeyEventListener KEY_EVENT_LISTENER;

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
                         VariableCache data,
                         MapFactory mapFactory,
                         RegistryFactory registryFactory,
                         GameZonesRepo gameZonesRepo,
                         CameraFactory cameraFactory,
                         RoundManager roundManager,
                         ItemFactory itemFactory,
                         CharacterFactory characterFactory,
                         Function<RoundManager,TurnBasedTimerFactory> turnBasedTimerFactoryFactory,
                         KeyBindingFactory keyBindingFactory,
                         KeyBindingContextFactory keyBindingContextFactory,
                         KeyEventListenerFactory keyEventListenerFactory) {
        PARTY = Check.ifNull(party, "party");
        DATA = Check.ifNull(data, "data");
        Check.ifNull(mapFactory, "mapFactory");
        CHARACTER_AI_TYPES = mapFactory.make("", CHARACTER_AI_TYPE_ARCHETYPE);
        GAME_ZONES_REPO = Check.ifNull(gameZonesRepo, "gameZonesRepo");
        CAMERA = Check.ifNull(cameraFactory, "cameraFactory").make(this::getCurrentGameZone);
        Check.ifNull(registryFactory, "registryFactory");
        MOVEMENT_EVENTS = registryFactory.make(GAME_MOVEMENT_EVENT_ARCHETYPE);
        ABILITY_EVENTS = registryFactory.make(GAME_ABILITY_EVENT_ARCHETYPE);
        ROUND_MANAGER = Check.ifNull(roundManager, "roundManager");
        KEY_BINDING_CONTEXTS = mapFactory.make(0, KEY_BINDING_CONTEXT_ARCHETYPE);
        ITEM_FACTORY = Check.ifNull(itemFactory, "itemFactory");
        CHARACTER_FACTORY = Check.ifNull(characterFactory, "characterFactory");
        TIMER_FACTORY = Check.ifNull(turnBasedTimerFactoryFactory, "turnBasedTimerFactoryFactory")
                .apply(roundManager);
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
    public VariableCache variableCache() {
        return DATA;
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
    public ItemFactory itemFactory() {
        return ITEM_FACTORY;
    }

    @Override
    public CharacterFactory characterFactory() {
        return CHARACTER_FACTORY;
    }

    @Override
    public TurnBasedTimerFactory turnBasedTimerFactory() {
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
    public KeyEventListener keyEventListener() {
        return KEY_EVENT_LISTENER;
    }

    @Override
    public String getInterfaceName() {
        return GameState.class.getCanonicalName();
    }
}
