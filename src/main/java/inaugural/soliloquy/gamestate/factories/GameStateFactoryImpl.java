package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class GameStateFactoryImpl implements GameStateFactory {
    private final GameZoneRepo GAME_ZONES_REPO;
    private final Function<Supplier<GameZone>, Camera> CAMERA_FACTORY;
    private final RoundManager ROUND_MANAGER;
    private final RoundBasedTimerManager ROUND_BASED_TIMER_MANAGER;
    private final ClockBasedTimerManager CLOCK_BASED_TIMER_MANAGER;
    private final ItemFactory ITEM_FACTORY;
    private final CharacterFactory CHARACTER_FACTORY;
    private final RoundBasedTimerFactory ROUND_BASED_TIMER_FACTORY;

    public GameStateFactoryImpl(GameZoneRepo GameZoneRepo,
                                Function<Supplier<GameZone>, Camera> cameraFactory,
                                RoundManager roundManager,
                                RoundBasedTimerManager roundBasedTimerManager,
                                ClockBasedTimerManager clockBasedTimerManager,
                                ItemFactory itemFactory,
                                CharacterFactory characterFactory,
                                RoundBasedTimerFactory roundBasedTimerFactory) {
        GAME_ZONES_REPO = Check.ifNull(GameZoneRepo, "GameZoneRepo");
        CAMERA_FACTORY = Check.ifNull(cameraFactory, "cameraFactory");
        ROUND_MANAGER = Check.ifNull(roundManager, "roundManager");
        ROUND_BASED_TIMER_MANAGER = Check.ifNull(roundBasedTimerManager, "roundBasedTimerManager");
        CLOCK_BASED_TIMER_MANAGER = Check.ifNull(clockBasedTimerManager, "clockBasedTimerManager");
        ITEM_FACTORY = Check.ifNull(itemFactory, "itemFactory");
        CHARACTER_FACTORY = Check.ifNull(characterFactory, "characterFactory");
        ROUND_BASED_TIMER_FACTORY = Check.ifNull(roundBasedTimerFactory, "roundBasedTimerFactory");
    }

    @Override
    public GameState make(Party party, Map<String, Object> data) throws IllegalArgumentException {
        return new GameStateImpl(party, data, GAME_ZONES_REPO, CAMERA_FACTORY,
                ROUND_MANAGER, ROUND_BASED_TIMER_MANAGER, CLOCK_BASED_TIMER_MANAGER, ITEM_FACTORY,
                CHARACTER_FACTORY, ROUND_BASED_TIMER_FACTORY);
    }
}
