package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.character.CharacterAIType;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static soliloquy.specs.gamestate.entities.CharacterEvents.CharacterEvent;

public class GameStateImpl implements GameState {
    private final Party PARTY;
    private final java.util.Map<String, CharacterAIType> CHARACTER_AI_TYPES;
    private final GameZoneRepo GAME_ZONE_REPO;
    private final Map<String, GameMovementEvent> MOVEMENT_EVENTS;
    private final Map<String, GameAbilityEvent> ABILITY_EVENTS;
    private final Map<String, CharacterEvent> CHARACTER_EVENTS;
    private final RoundManager ROUND_MANAGER;
    private final RoundBasedTimerManager ROUND_BASED_TIMER_MANAGER;
    private final ClockBasedTimerManager CLOCK_BASED_TIMER_MANAGER;
    private final ItemFactory ITEM_FACTORY;
    private final CharacterFactory CHARACTER_FACTORY;
    private final RoundBasedTimerFactory ROUND_BASED_TIMER_FACTORY;
    private final Map<String, Object> DATA;

    public GameStateImpl(Party party,
                         Map<String, Object> data,
                         GameZoneRepo GameZoneRepo,
                         RoundManager roundManager,
                         RoundBasedTimerManager roundBasedTimerManager,
                         ClockBasedTimerManager clockBasedTimerManager,
                         ItemFactory itemFactory,
                         CharacterFactory characterFactory,
                         RoundBasedTimerFactory roundBasedTimerFactory) {
        PARTY = Check.ifNull(party, "party");
        // TODO: Test to ensure this is actually cloned
        DATA = mapOf(Check.ifNull(data, "data"));
        CHARACTER_AI_TYPES = mapOf();
        GAME_ZONE_REPO = Check.ifNull(GameZoneRepo, "GameZoneRepo");
        MOVEMENT_EVENTS = mapOf();
        ABILITY_EVENTS = mapOf();
        CHARACTER_EVENTS = mapOf();
        ROUND_MANAGER = Check.ifNull(roundManager, "roundManager");
        ROUND_BASED_TIMER_MANAGER = Check.ifNull(roundBasedTimerManager, "roundBasedTimerManager");
        CLOCK_BASED_TIMER_MANAGER = Check.ifNull(clockBasedTimerManager, "clockBasedTimerManager");
        ITEM_FACTORY = Check.ifNull(itemFactory, "itemFactory");
        CHARACTER_FACTORY = Check.ifNull(characterFactory, "characterFactory");
        ROUND_BASED_TIMER_FACTORY = Check.ifNull(roundBasedTimerFactory, "roundBasedTimerFactory");
    }

    @Override
    public Party party() throws IllegalStateException {
        return PARTY;
    }

    @Override
    public java.util.Map<String, CharacterAIType> characterAIs() {
        return CHARACTER_AI_TYPES;
    }

    @Override
    public GameZoneRepo gameZoneRepo() {
        return GAME_ZONE_REPO;
    }

    @Override
    public Map<String, GameMovementEvent> movementEvents() {
        return MOVEMENT_EVENTS;
    }

    @Override
    public Map<String, GameAbilityEvent> abilityEvents() {
        return ABILITY_EVENTS;
    }

    @Override
    public Map<String, CharacterEvents.CharacterEvent> characterEvents() {
        return CHARACTER_EVENTS;
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
    public Map<String, Object> data() throws IllegalStateException {
        return DATA;
    }
}
