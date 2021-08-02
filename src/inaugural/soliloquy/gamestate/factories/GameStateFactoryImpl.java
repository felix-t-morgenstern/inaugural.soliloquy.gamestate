package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.GameStateImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.GameZonesRepo;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.RoundManager;
import soliloquy.specs.gamestate.factories.*;

import java.util.function.Function;

public class GameStateFactoryImpl implements GameStateFactory {
    private final MapFactory MAP_FACTORY;
    private final RegistryFactory REGISTRY_FACTORY;
    private final GameZonesRepo GAME_ZONES_REPO;
    private final CameraFactory CAMERA_FACTORY;
    private final RoundManager ROUND_MANAGER;
    private final ItemFactory ITEM_FACTORY;
    private final CharacterFactory CHARACTER_FACTORY;
    private final Function<RoundManager, TurnBasedTimerFactory> TURN_BASED_TIMER_FACTORY_FACTORY;
    private final KeyBindingFactory KEY_BINDING_FACTORY;
    private final KeyBindingContextFactory KEY_BINDING_CONTEXT_FACTORY;
    private final KeyEventListenerFactory KEY_EVENT_LISTENER_FACTORY;

    public GameStateFactoryImpl(MapFactory mapFactory, RegistryFactory registryFactory,
                                GameZonesRepo gameZonesRepo, CameraFactory cameraFactory,
                                RoundManager roundManager, ItemFactory itemFactory,
                                CharacterFactory characterFactory,
                                Function<RoundManager, TurnBasedTimerFactory>
                                            turnBasedTimerFactoryFactory,
                                KeyBindingFactory keyBindingFactory,
                                KeyBindingContextFactory keyBindingContextFactory,
                                KeyEventListenerFactory keyEventListenerFactory) {
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
        REGISTRY_FACTORY = Check.ifNull(registryFactory, "registryFactory");
        GAME_ZONES_REPO = Check.ifNull(gameZonesRepo, "gameZonesRepo");
        CAMERA_FACTORY = Check.ifNull(cameraFactory, "cameraFactory");
        ROUND_MANAGER = Check.ifNull(roundManager, "roundManager");
        ITEM_FACTORY = Check.ifNull(itemFactory, "itemFactory");
        CHARACTER_FACTORY = Check.ifNull(characterFactory, "characterFactory");
        TURN_BASED_TIMER_FACTORY_FACTORY = Check.ifNull(turnBasedTimerFactoryFactory,
                "turnBasedTimerFactoryFactory");
        KEY_BINDING_FACTORY = Check.ifNull(keyBindingFactory, "keyBindingFactory");
        KEY_BINDING_CONTEXT_FACTORY = Check.ifNull(keyBindingContextFactory,
                "keyBindingContextFactory");
        KEY_EVENT_LISTENER_FACTORY = Check.ifNull(keyEventListenerFactory,
                "keyEventListenerFactory");
    }

    @Override
    public GameState make(Party party, VariableCache variableCache) throws IllegalArgumentException {
        return new GameStateImpl(party, variableCache, MAP_FACTORY, REGISTRY_FACTORY,
                GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY, CHARACTER_FACTORY,
                TURN_BASED_TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_EVENT_LISTENER_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return GameStateFactory.class.getCanonicalName();
    }
}
