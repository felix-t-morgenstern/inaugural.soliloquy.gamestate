package inaugural.soliloquy.gamestate;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.gamestate.entities.GameZonesRepoImpl;
import inaugural.soliloquy.gamestate.entities.RoundManagerImpl;
import inaugural.soliloquy.gamestate.factories.*;
import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.*;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
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
import java.util.function.Function;

public class GameStateModule extends AbstractModule {
    private GameStateFactory _gameStateFactory;

    public GameStateModule(ListFactory listFactory,
                           CoordinateFactory coordinateFactory,
                           EntityUuidFactory entityUuidFactory,
                           MapFactory mapFactory,
                           PairFactory pairFactory,
                           RegistryFactory registryFactory,
                           VariableCacheFactory variableCacheFactory,
                           PersistentValuesHandler persistentValuesHandler,
                           CharacterStatisticCalculation characterStatisticCalculation,
                           StatusEffectResistanceCalculation resistanceCalculation,
                           TileVisibility tileVisibility,
                           ActiveCharactersProvider activeCharactersProvider,
                           TurnHandling turnHandling,
                           RoundEndHandling roundEndHandling,
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
        TypeHandler<EntityUuid> uuidHandler =
                persistentValuesHandler.getTypeHandler(
                        EntityUuid.class.getCanonicalName());

        TypeHandler<VariableCache> dataHandler =
                persistentValuesHandler.getTypeHandler(
                        VariableCache.class.getCanonicalName());

        TypeHandler<Sprite> spriteHandler =
                persistentValuesHandler.getTypeHandler(
                        Sprite.class.getCanonicalName());

                ItemFactory itemFactory = new ItemFactoryImpl(entityUuidFactory, variableCacheFactory,
                pairFactory);

        TypeHandler<Item> itemHandler = new ItemHandler(itemTypes::get,
                uuidHandler, dataHandler, itemFactory);

        CharacterEventsFactory characterEventsFactory =
                new CharacterEventsFactoryImpl(listFactory, mapFactory);

        CharacterEquipmentSlotsFactory characterEquipmentSlotsFactory =
                new CharacterEquipmentSlotsFactoryImpl(pairFactory, mapFactory);

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

        CharacterFactory characterFactory = new CharacterFactoryImpl(entityUuidFactory,
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

        TileFixtureFactory tileFixtureFactory = new TileFixtureFactoryImpl(entityUuidFactory,
                tileFixtureItemsFactory, variableCacheFactory);

        TypeHandler<TileFixture> tileFixturesHandler =
                new TileFixtureHandler(fixtureTypes::get, tileFixtureFactory,
                        uuidHandler, dataHandler, itemHandler);

        TileEntitiesFactory tileEntitiesFactory = new TileEntitiesFactoryImpl(pairFactory,
                mapFactory);
        TileWallSegmentsFactory tileWallSegmentsFactory =
                new TileWallSegmentsFactoryImpl(pairFactory, mapFactory);

        TileFactory tileFactory = new TileFactoryImpl(coordinateFactory, tileEntitiesFactory,
                tileWallSegmentsFactory);

        TileWallSegmentFactory tileWallSegmentFactory =
                new TileWallSegmentFactoryImpl(variableCacheFactory);

        TypeHandler<Tile> tileHandler = new TileHandler(tileFactory,
                tileWallSegmentFactory, characterHandler, itemHandler, tileFixturesHandler,
                spriteHandler, dataHandler, wallSegmentTypes::get, gameMovementEvents::get,
                gameAbilityEvents::get, groundTypes::get);

        RoundManagerImpl roundManager = new RoundManagerImpl(listFactory, pairFactory,
                variableCacheFactory, activeCharactersProvider, turnHandling, roundEndHandling);

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

        Function<RoundManager, TurnBasedTimerFactory> turnBasedTimerFactoryFactory = r ->
                new TurnBasedTimerFactoryImpl(roundManager::addOneTimeTurnBasedTimer,
                        roundManager::removeOneTimeTurnBasedTimer, roundManager::addRecurringTurnBasedTimer,
                        roundManager::removeRecurringTurnBasedTimer);

        KeyBindingFactory keyBindingFactory = new KeyBindingFactoryImpl(listFactory);

        KeyBindingContextFactory keyBindingContextFactory =
                new KeyBindingContextFactoryImpl(listFactory);

        KeyEventListenerFactory keyEventListenerFactory =
                new KeyEventListenerFactoryImpl(listFactory, mapFactory);

        _gameStateFactory = new GameStateFactoryImpl(mapFactory, registryFactory, gameZonesRepo,
                cameraFactory, roundManager, itemFactory, characterFactory,
                turnBasedTimerFactoryFactory, keyBindingFactory, keyBindingContextFactory,
                keyEventListenerFactory);
    }

    @Override
    protected void configure() {
    }
}
