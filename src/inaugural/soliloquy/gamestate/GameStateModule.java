package inaugural.soliloquy.gamestate;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.gamestate.entities.GameZonesRepoImpl;
import inaugural.soliloquy.gamestate.entities.RoundManagerImpl;
import inaugural.soliloquy.gamestate.factories.*;
import inaugural.soliloquy.gamestate.persistence.*;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.ruleset.gameconcepts.*;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class GameStateModule extends AbstractModule {
    @SuppressWarnings("FieldCanBeLocal") private GameStateFactory _gameStateFactory;

    public GameStateModule(ListFactory listFactory,
                           CoordinateFactory coordinateFactory,
                           MapFactory mapFactory,
                           RegistryFactory registryFactory,
                           VariableCacheFactory variableCacheFactory,
                           PersistentValuesHandler persistentValuesHandler,
                           CharacterStatisticCalculation characterStatisticCalculation,
                           StatusEffectResistanceCalculation resistanceCalculation,
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
                           Registry<GameCharacterEvent> gameCharacterEvents,
                           Registry<GameMovementEvent> gameMovementEvents,
                           Registry<GameAbilityEvent> gameAbilityEvents,
                           Registry<CharacterStaticStatisticType> characterStaticStatisticTypes,
                           Registry<CharacterVariableStatisticType>
                                   characterVariableStatisticTypes,
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
                persistentValuesHandler.getTypeHandler(
                        VariableCache.class.getCanonicalName());

        TypeHandler<Sprite> spriteHandler =
                persistentValuesHandler.getTypeHandler(
                        Sprite.class.getCanonicalName());

        ItemFactory itemFactory = new ItemFactoryImpl(uuidFactory, variableCacheFactory);

        TypeHandler<Item> itemHandler = new ItemHandler(itemTypes::get,
                uuidHandler, dataHandler, itemFactory);

        CharacterEventsFactory characterEventsFactory =
                new CharacterEventsFactoryImpl(listFactory, mapFactory);

        CharacterEquipmentSlotsFactory characterEquipmentSlotsFactory =
                new CharacterEquipmentSlotsFactoryImpl(mapFactory);

        CharacterInventoryFactory characterInventoryFactory =
                new CharacterInventoryFactoryImpl(listFactory);

        EntityMemberOfTypeFactory<CharacterVariableStatisticType,
                CharacterVariableStatistic, Character> characterVariableStatisticFactory =
                new CharacterVariableStatisticFactory(variableCacheFactory,
                        characterStatisticCalculation);

        CharacterVariableStatisticsFactory variableStatsFactory =
                new CharacterVariableStatisticsFactoryImpl(mapFactory, listFactory,
                        variableCacheFactory, characterVariableStatisticFactory);

        EntityMembersOfTypeFactory entitiesOfTypeFactory =
                new EntityMembersOfTypeFactoryImpl(listFactory, variableCacheFactory);

        CharacterStatusEffectsFactory characterStatusEffectsFactory =
                new CharacterStatusEffectsFactoryImpl(mapFactory, resistanceCalculation);

        CharacterFactory characterFactory = new CharacterFactoryImpl(uuidFactory,
                characterEventsFactory, characterEquipmentSlotsFactory, characterInventoryFactory,
                variableStatsFactory, entitiesOfTypeFactory, characterStatusEffectsFactory,
                variableCacheFactory);

        TypeHandler<Character> characterHandler =
                new CharacterHandler(characterFactory, uuidHandler, characterTypes::get,
                        characterClassifications::get, imageAssetSets::get, characterAITypes::get,
                        gameCharacterEvents::get, characterStaticStatisticTypes::get,
                        characterVariableStatisticTypes::get, statusEffectTypes::get,
                        passiveAbilities::get, activeAbilities::get, reactiveAbilities::get,
                        dataHandler, itemHandler);

        TileFixtureItemsFactory tileFixtureItemsFactory =
                new TileFixtureItemsFactoryImpl(listFactory);

        TileFixtureFactory tileFixtureFactory = new TileFixtureFactoryImpl(uuidFactory,
                tileFixtureItemsFactory, variableCacheFactory);

        TypeHandler<TileFixture> tileFixturesHandler =
                new TileFixtureHandler(fixtureTypes::get, tileFixtureFactory,
                        uuidHandler, dataHandler, itemHandler);

        TileEntitiesFactory tileEntitiesFactory = new TileEntitiesFactoryImpl(mapFactory);
        TileWallSegmentsFactory tileWallSegmentsFactory =
                new TileWallSegmentsFactoryImpl(mapFactory);

        TileFactory tileFactory = new TileFactoryImpl(coordinateFactory, tileEntitiesFactory,
                tileWallSegmentsFactory);

        TileWallSegmentFactory tileWallSegmentFactory =
                new TileWallSegmentFactoryImpl(variableCacheFactory);

        TypeHandler<Tile> tileHandler = new TileHandler(tileFactory,
                tileWallSegmentFactory, characterHandler, itemHandler, tileFixturesHandler,
                spriteHandler, dataHandler, wallSegmentTypes::get, gameMovementEvents::get,
                gameAbilityEvents::get, groundTypes::get);

        // TODO: Populate this!
        RoundBasedTimerManager roundBasedTimerManager = null;

        // TODO: Populate this!
        RoundManagerImpl roundManager = null;

        GameZoneFactory gameZoneFactory = new GameZoneFactoryImpl(coordinateFactory,
                listFactory,
                c -> roundManager.setCharacterPositionInQueue(c, Integer.MAX_VALUE),
                roundManager::removeCharacterFromQueue);

        TypeHandler<GameZone> gameZoneHandler =
                new GameZoneHandler(gameZoneFactory, tileHandler, dataHandler,
                        actions::get);

        GameZonesRepo gameZonesRepo = new GameZonesRepoImpl(gameZoneHandler, fileLocations);

        CameraFactory cameraFactory = new CameraFactoryImpl(coordinateFactory, listFactory,
                mapFactory, tileVisibility);

        RoundBasedTimerFactory roundBasedTimerFactory =
                new RoundBasedTimerFactoryImpl(roundBasedTimerManager);

        KeyBindingFactory keyBindingFactory = new KeyBindingFactoryImpl(listFactory);

        KeyBindingContextFactory keyBindingContextFactory =
                new KeyBindingContextFactoryImpl(listFactory);

        KeyEventListenerFactory keyEventListenerFactory =
                new KeyEventListenerFactoryImpl(listFactory, mapFactory);

        _gameStateFactory = new GameStateFactoryImpl(mapFactory, registryFactory, gameZonesRepo,
                cameraFactory, roundManager, roundBasedTimerManager, itemFactory, characterFactory,
                roundBasedTimerFactory, keyBindingFactory, keyBindingContextFactory,
                keyEventListenerFactory);
    }

    @Override
    protected void configure() {
    }
}
