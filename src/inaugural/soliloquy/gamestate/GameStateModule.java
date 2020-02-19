package inaugural.soliloquy.gamestate;

import com.google.inject.AbstractModule;
import com.google.inject.name.Named;
import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.*;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.*;
import soliloquy.specs.ruleset.entities.*;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;
import soliloquy.specs.ruleset.gameconcepts.*;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;
import soliloquy.specs.sprites.entities.Sprite;
import soliloquy.specs.sprites.entities.SpriteSet;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;

public class GameStateModule extends AbstractModule {
    private GameStateFactory _gameStateFactory;

    public GameStateModule(CollectionFactory collectionFactory,
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
                           Registry<SpriteSet> spriteSets,
                           Registry<CharacterAIType> characterAITypes,
                           Registry<GameCharacterEvent> gameCharacterEvents,
                           Registry<GameMovementEvent> gameMovementEvents,
                           Registry<GameAbilityEvent> gameAbilityEvents,
                           Registry<CharacterStaticStatisticType> characterStaticStatisticTypes,
                           Registry<CharacterDepletableStatisticType>
                                   characterDepletableStatisticTypes,
                           Registry<StatusEffectType> statusEffectTypes,
                           Registry<ActiveAbilityType> activeAbilityTypes,
                           Registry<ReactiveAbilityType> reactiveAbilityTypes,
                           Registry<Action> actions,
                           @Named("onCharacterTurnStart") Consumer<Character> onCharacterTurnStart,
                           @Named("onCharacterTurnEnd") Consumer<Character> onCharacterTurnEnd,
                           @Named("onRoundStart") Consumer<Void> onRoundStart,
                           @Named("onRoundEnd") Consumer<Void> onRoundEnd,
                           java.util.Map<String, Path> fileLocations) {
        PersistentValueTypeHandler<EntityUuid> uuidHandler =
                persistentValuesHandler.getPersistentValueTypeHandler(
                        EntityUuid.class.getCanonicalName());

        PersistentValueTypeHandler<VariableCache> dataHandler =
                persistentValuesHandler.getPersistentValueTypeHandler(
                        VariableCache.class.getCanonicalName());

        PersistentValueTypeHandler<Sprite> spriteHandler =
                persistentValuesHandler.getPersistentValueTypeHandler(
                        Sprite.class.getCanonicalName());

                ItemFactory itemFactory = new ItemFactoryImpl(entityUuidFactory, variableCacheFactory,
                pairFactory);

        PersistentValueTypeHandler<Item> itemHandler = new PersistentItemHandler(itemTypes::get,
                uuidHandler, dataHandler, itemFactory);

        CharacterEventsFactory characterEventsFactory =
                new CharacterEventsFactoryImpl(collectionFactory, mapFactory);

        CharacterEquipmentSlotsFactory characterEquipmentSlotsFactory =
                new CharacterEquipmentSlotsFactoryImpl(pairFactory, mapFactory);

        CharacterInventoryFactory characterInventoryFactory =
                new CharacterInventoryFactoryImpl(collectionFactory);

        CharacterEntityOfTypeFactory<CharacterDepletableStatisticType,
                CharacterDepletableStatistic> characterDepletableStatisticFactory =
                new CharacterDepletableStatisticFactory(characterStatisticCalculation);

        CharacterDepletableStatisticsFactory depletableStatsFactory =
                new CharacterDepletableStatisticsFactoryImpl(mapFactory, collectionFactory,
                        characterDepletableStatisticFactory);

        CharacterEntitiesOfTypeFactory entitiesOfTypeFactory =
                new CharacterEntitiesOfTypeFactoryImpl(collectionFactory);

        CharacterStatusEffectsFactory characterStatusEffectsFactory =
                new CharacterStatusEffectsFactoryImpl(mapFactory, resistanceCalculation);

        CharacterFactory characterFactory = new CharacterFactoryImpl(entityUuidFactory,
                collectionFactory, mapFactory, characterEventsFactory,
                characterEquipmentSlotsFactory, characterInventoryFactory, depletableStatsFactory,
                entitiesOfTypeFactory, characterStatusEffectsFactory, variableCacheFactory);

        PersistentValueTypeHandler<Character> characterHandler =
                new PersistentCharacterHandler(characterFactory, uuidHandler, characterTypes::get,
                        characterClassifications::get, spriteSets::get, characterAITypes::get,
                        gameCharacterEvents::get, characterStaticStatisticTypes::get,
                        characterDepletableStatisticTypes::get, statusEffectTypes::get,
                        activeAbilityTypes::get, reactiveAbilityTypes::get, dataHandler,
                        itemHandler);

        TileFixtureItemsFactory tileFixtureItemsFactory =
                new TileFixtureItemsFactoryImpl(collectionFactory);

        TileFixtureFactory tileFixtureFactory = new TileFixtureFactoryImpl(entityUuidFactory,
                coordinateFactory, collectionFactory, tileFixtureItemsFactory,
                variableCacheFactory);

        PersistentValueTypeHandler<TileFixture> tileFixturesHandler =
                new PersistentTileFixtureHandler(fixtureTypes::get, tileFixtureFactory,
                        uuidHandler, dataHandler, itemHandler);

        TileEntitiesFactory tileEntitiesFactory = new TileEntitiesFactoryImpl(pairFactory,
                mapFactory);
        TileWallSegmentsFactory tileWallSegmentsFactory =
                new TileWallSegmentsFactoryImpl(pairFactory, mapFactory);

        TileFactory tileFactory = new TileFactoryImpl(coordinateFactory, tileEntitiesFactory,
                tileWallSegmentsFactory, collectionFactory, mapFactory);

        TileWallSegmentFactory tileWallSegmentFactory =
                new TileWallSegmentFactoryImpl(variableCacheFactory);

        PersistentValueTypeHandler<Tile> tileHandler = new PersistentTileHandler(tileFactory,
                tileWallSegmentFactory, characterHandler, itemHandler, tileFixturesHandler,
                spriteHandler, dataHandler, wallSegmentTypes::get, gameMovementEvents::get,
                gameAbilityEvents::get, groundTypes::get);

        GameZoneFactory gameZoneFactory = new GameZoneFactoryImpl(coordinateFactory,
                collectionFactory);

        PersistentValueTypeHandler<GameZone> gameZoneHandler =
                new PersistentGameZoneHandler(gameZoneFactory, tileHandler, dataHandler,
                        collectionFactory, actions::get);

        GameZonesRepo gameZonesRepo = new GameZonesRepoImpl(gameZoneHandler, fileLocations);

        CameraFactory cameraFactory = new CameraFactoryImpl(coordinateFactory, collectionFactory,
                mapFactory, tileVisibility);

        RoundManagerImpl roundManager = new RoundManagerImpl(collectionFactory, pairFactory,
                variableCacheFactory, activeCharactersProvider, turnHandling, roundEndHandling);

        Function<RoundManager, TimerFactory> timerFactoryFactory = r ->
                new TimerFactoryImpl(roundManager::addOneTimeTimer,
                        roundManager::removeOneTimeTimer, roundManager::addRecurringTimer,
                        roundManager::removeRecurringTimer);

        KeyBindingFactory keyBindingFactory = new KeyBindingFactoryImpl(collectionFactory);

        KeyBindingContextFactory keyBindingContextFactory =
                new KeyBindingContextFactoryImpl(collectionFactory);

        KeyPressListenerFactory keyPressListenerFactory =
                new KeyPressListenerFactoryImpl(mapFactory);

        _gameStateFactory = new GameStateFactoryImpl(mapFactory, registryFactory, gameZonesRepo,
                cameraFactory, roundManager, itemFactory, characterFactory, timerFactoryFactory,
                keyBindingFactory, keyBindingContextFactory, keyPressListenerFactory);
    }

    @Override
    protected void configure() {
    }
}
