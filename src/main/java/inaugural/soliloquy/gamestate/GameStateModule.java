package inaugural.soliloquy.gamestate;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.gamestate.entities.GameZonesRepoImpl;
import inaugural.soliloquy.gamestate.entities.RoundManagerImpl;
import inaugural.soliloquy.gamestate.factories.*;
import inaugural.soliloquy.gamestate.persistence.*;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.GameStateFactory;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.FixtureType;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.ruleset.entities.ItemType;
import soliloquy.specs.ruleset.entities.WallSegmentType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.entities.character.*;
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;
import soliloquy.specs.ruleset.gameconcepts.StatisticCalculation;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class GameStateModule extends AbstractModule {
    @SuppressWarnings("FieldCanBeLocal") private GameStateFactory gameStateFactory;

    public GameStateModule(RegistryFactory registryFactory,
                           VariableCacheFactory variableCacheFactory,
                           PersistentValuesHandler persistentValuesHandler,
                           StatisticCalculation characterStatisticCalculation,
                           TileVisibility tileVisibility,
                           ActiveCharactersProvider activeCharactersProvider,
                           TurnHandling turnHandling,
                           Registry<CharacterType> characterTypes,
                           Registry<CharacterClassification> characterClassifications,
                           Registry<ItemType> itemTypes,
                           Registry<GroundType> groundTypes,
                           Registry<FixtureType> fixtureTypes,
                           Registry<WallSegmentType> wallSegmentTypes,
                           Registry<ImageAssetSet> imageAssetSets,
                           Registry<CharacterAIType> characterAITypes,
                           Registry<CharacterEvents.CharacterEvent> gameCharacterEvents,
                           Registry<GameMovementEvent> gameMovementEvents,
                           Registry<GameAbilityEvent> gameAbilityEvents,
                           Registry<StaticStatisticType> staticStatisticTypes,
                           Registry<VariableStatisticType> variableStatisticTypes,
                           Registry<StatusEffectType> statusEffectTypes,
                           Registry<PassiveAbility> passiveAbilities,
                           Registry<ActiveAbility> activeAbilities,
                           Registry<ReactiveAbility> reactiveAbilities,
                           @SuppressWarnings("rawtypes") Registry<Action> actions,
                           java.util.Map<String, Path> fileLocations) {
        Supplier<UUID> uuidFactory = UUID::randomUUID;

        TypeHandler<UUID> uuidHandler =
                persistentValuesHandler.getTypeHandler(UUID.class.getCanonicalName());

        TypeHandler<VariableCache> dataHandler =
                persistentValuesHandler.getTypeHandler(VariableCache.class.getCanonicalName());

        TypeHandler<Sprite> spriteHandler =
                persistentValuesHandler.getTypeHandler(Sprite.class.getCanonicalName());

        var itemFactory = new ItemFactoryImpl(variableCacheFactory);

        var itemHandler = new ItemHandler(itemTypes::get, dataHandler, itemFactory);

        var characterEventsFactory = new CharacterEventsFactoryImpl();

        var characterEquipmentSlotsFactory = new CharacterEquipmentSlotsFactoryImpl();

        var characterInventoryFactory = new CharacterInventoryFactoryImpl();

        var characterStatusEffectsFactory = new CharacterStatusEffectsFactoryImpl();

        var characterFactory = new CharacterFactoryImpl(uuidFactory, characterEventsFactory,
                characterEquipmentSlotsFactory, characterInventoryFactory,
                characterStatusEffectsFactory, variableCacheFactory);

        // TODO: Populate with characterEventsHandler
        var characterHandler = new CharacterHandler(characterFactory, characterTypes::get,
                characterClassifications::get, imageAssetSets::get, characterAITypes::get, null,
                variableStatisticTypes::get, statusEffectTypes::get, passiveAbilities::get,
                activeAbilities::get, reactiveAbilities::get, dataHandler, itemHandler);

        var tileFixtureItemsFactory = new TileFixtureItemsFactoryImpl();

        var tileFixtureFactory =
                new TileFixtureFactoryImpl(tileFixtureItemsFactory, variableCacheFactory);

        var tileFixturesHandler =
                new TileFixtureHandler(fixtureTypes::get, tileFixtureFactory, dataHandler,
                        itemHandler);

        var tileEntitiesFactory = new TileEntitiesFactoryImpl();

        var tileFactory = new TileFactoryImpl(tileEntitiesFactory);

        var tileHandler =
                new TileHandler(tileFactory, characterHandler, itemHandler, tileFixturesHandler,
                        spriteHandler, dataHandler, gameMovementEvents::get, gameAbilityEvents::get,
                        groundTypes::get);

        // TODO: Populate this!
        RoundBasedTimerManager roundBasedTimerManager = null;

        // TODO: Populate this!
        ClockBasedTimerManager clockBasedTimerManager = null;

        // TODO: Populate this!
        RoundManagerImpl roundManager = null;

        var gameZoneFactory = new GameZoneFactoryImpl(
                c -> roundManager.setCharacterPositionInQueue(c, Integer.MAX_VALUE),
                roundManager::removeCharacterFromQueue);

        // TODO: Populate tilesPerBatch and threadPoolSize from configs somewhere
        var gameZoneHandler =
                new GameZoneHandler(gameZoneFactory, tileHandler, dataHandler, actions::get, 5, 5);

        var gameZonesRepo = new GameZonesRepoImpl(gameZoneHandler, fileLocations);

        var cameraFactory = new CameraFactoryImpl(tileVisibility);

        var roundBasedTimerFactory = new RoundBasedTimerFactoryImpl(roundBasedTimerManager);

        var keyBindingFactory = new KeyBindingFactoryImpl();

        var keyBindingContextFactory = new KeyBindingContextFactoryImpl();

        var keyEventListenerFactory = new KeyEventListenerFactoryImpl();

        var gameStateFactory =
                new GameStateFactoryImpl(registryFactory, gameZonesRepo, cameraFactory,
                        roundManager, roundBasedTimerManager, clockBasedTimerManager, itemFactory,
                        characterFactory, roundBasedTimerFactory, keyBindingFactory,
                        keyBindingContextFactory, keyEventListenerFactory);
    }

    @Override
    protected void configure() {
    }
}
