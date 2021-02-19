package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.GameStateImpl;
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
    private final Function<RoundManager, TimerFactory> TIMER_FACTORY_FACTORY;
    private final KeyBindingFactory KEY_BINDING_FACTORY;
    private final KeyBindingContextFactory KEY_BINDING_CONTEXT_FACTORY;
    private final KeyPressListenerFactory KEY_PRESS_LISTENER_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public GameStateFactoryImpl(MapFactory mapFactory, RegistryFactory registryFactory,
                                GameZonesRepo gameZonesRepo, CameraFactory cameraFactory,
                                RoundManager roundManager, ItemFactory itemFactory,
                                CharacterFactory characterFactory,
                                Function<RoundManager, TimerFactory> timerFactoryFactory,
                                KeyBindingFactory keyBindingFactory,
                                KeyBindingContextFactory keyBindingContextFactory,
                                KeyPressListenerFactory keyPressListenerFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException("GameStateFactoryImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
        if (registryFactory == null) {
            throw new IllegalArgumentException(
                    "GameStateFactoryImpl: registryFactory cannot be null");
        }
        REGISTRY_FACTORY = registryFactory;
        if (gameZonesRepo == null) {
            throw new IllegalArgumentException(
                    "GameStateFactoryImpl: gameZonesRepo cannot be null");
        }
        GAME_ZONES_REPO = gameZonesRepo;
        if (cameraFactory == null) {
            throw new IllegalArgumentException("GameStateFactoryImpl: cameraFactory cannot be null");
        }
        CAMERA_FACTORY = cameraFactory;
        if (roundManager == null) {
            throw new IllegalArgumentException(
                    "GameStateFactoryImpl: roundManager cannot be null");
        }
        ROUND_MANAGER = roundManager;
        if (itemFactory == null) {
            throw new IllegalArgumentException("GameStateFactoryImpl: itemFactory cannot be null");
        }
        ITEM_FACTORY = itemFactory;
        if (characterFactory == null) {
            throw new IllegalArgumentException(
                    "GameStateFactoryImpl: characterFactory cannot be null");
        }
        CHARACTER_FACTORY = characterFactory;
        if (timerFactoryFactory == null) {
            throw new IllegalArgumentException(
                    "GameStateFactoryImpl: timerFactoryFactory cannot be null");
        }
        TIMER_FACTORY_FACTORY = timerFactoryFactory;
        if (keyBindingFactory == null) {
            throw new IllegalArgumentException(
                    "GameStateFactoryImpl: keyBindingFactory cannot be null");
        }
        KEY_BINDING_FACTORY = keyBindingFactory;
        if (keyBindingContextFactory == null) {
            throw new IllegalArgumentException(
                    "GameStateFactoryImpl: keyBindingContextFactory cannot be null");
        }
        KEY_BINDING_CONTEXT_FACTORY = keyBindingContextFactory;
        if (keyPressListenerFactory == null) {
            throw new IllegalArgumentException(
                    "GameStateFactoryImpl: keyPressListenerFactory cannot be null");
        }
        KEY_PRESS_LISTENER_FACTORY = keyPressListenerFactory;
    }

    @Override
    public GameState make(Party party, VariableCache variableCache) throws IllegalArgumentException {
        return new GameStateImpl(party, variableCache, MAP_FACTORY, REGISTRY_FACTORY,
                GAME_ZONES_REPO, CAMERA_FACTORY, ROUND_MANAGER, ITEM_FACTORY, CHARACTER_FACTORY,
                TIMER_FACTORY_FACTORY, KEY_BINDING_FACTORY, KEY_BINDING_CONTEXT_FACTORY,
                KEY_PRESS_LISTENER_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return GameStateFactory.class.getCanonicalName();
    }
}
