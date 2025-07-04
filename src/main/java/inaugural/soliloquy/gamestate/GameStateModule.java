package inaugural.soliloquy.gamestate;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.gamestate.entities.*;
import inaugural.soliloquy.gamestate.factories.*;
import inaugural.soliloquy.gamestate.persistence.*;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import soliloquy.specs.gamestate.factories.GameStateFactory;
import soliloquy.specs.io.graphics.assets.ImageAssetSet;
import soliloquy.specs.io.graphics.assets.Sprite;
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
import soliloquy.specs.ruleset.gameconcepts.TileVisibilityCalculation;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;
import soliloquy.specs.ruleset.valueobjects.CharacterClassification;

import java.nio.file.Path;
import java.util.Map;

import static soliloquy.specs.gamestate.entities.CharacterEvents.CharacterEvent;

@SuppressWarnings("unused")
public class GameStateModule extends AbstractModule {
    @SuppressWarnings("FieldCanBeLocal") private GameStateFactory gameStateFactory;

    public GameStateModule(PersistenceHandler persistenceHandler,
                           StatisticCalculation characterStatisticCalculation,
                           TileVisibilityCalculation tileVisibilityCalculation,
                           ActiveCharactersProvider activeCharactersProvider,
                           TurnHandling turnHandling,
                           Map<String, CharacterType> characterTypes,
                           Map<String, CharacterClassification> characterClassifications,
                           Map<String, ItemType> itemTypes,
                           Map<String, GroundType> groundTypes,
                           Map<String, FixtureType> fixtureTypes,
                           Map<String, WallSegmentType> wallSegmentTypes,
                           Map<String, ImageAssetSet> imageAssetSets,
                           Map<String, CharacterAIType> characterAITypes,
                           Map<String, CharacterEvent> characterEvents,
                           Map<String, CharacterEvents.CharacterEvent> gameCharacterEvents,
                           Map<String, GameMovementEvent> gameMovementEvents,
                           Map<String, GameAbilityEvent> gameAbilityEvents,
                           Map<String, StaticStatisticType> staticStatisticTypes,
                           Map<String, VariableStatisticType> variableStatisticTypes,
                           Map<String, StatusEffectType> statusEffectTypes,
                           Map<String, PassiveAbility> passiveAbilities,
                           Map<String, ActiveAbility> activeAbilities,
                           Map<String, ReactiveAbility> reactiveAbilities,
                           @SuppressWarnings("rawtypes") Map<String, Action> actions,
                           java.util.Map<String, Path> fileLocations) {
        //noinspection rawtypes
        TypeHandler<Map> mapHandler =
                persistenceHandler.getTypeHandler(Map.class.getCanonicalName());
        TypeHandler<Sprite> spriteHandler =
                persistenceHandler.getTypeHandler(Sprite.class.getCanonicalName());

        var itemFactory = new ItemFactoryImpl();

        var itemHandler = new ItemHandler(itemTypes::get, mapHandler, itemFactory);

        var characterFactory = new CharacterFactoryImpl(CharacterEventsImpl::new,
                CharacterEquipmentSlotsImpl::new, CharacterInventoryImpl::new,
                CharacterStatusEffectsImpl::new);

        // TODO: Populate with characterEventsHandler
        var characterHandler = new CharacterHandler(characterFactory, characterTypes::get,
                characterClassifications::get, imageAssetSets::get, characterAITypes::get,
                characterEvents::get, variableStatisticTypes::get, statusEffectTypes::get,
                passiveAbilities::get, activeAbilities::get, reactiveAbilities::get, mapHandler,
                itemHandler);

        var tileFixtureFactory = new TileFixtureFactoryImpl(TileFixtureItemsImpl::new);

        var tileFixturesHandler =
                new TileFixtureHandler(fixtureTypes::get, tileFixtureFactory, mapHandler,
                        itemHandler);

        var tileHandler = new TileHandler(
                data -> new TileImpl(TileEntitiesImpl::new, data), characterHandler, itemHandler,
                tileFixturesHandler, spriteHandler, mapHandler, gameMovementEvents::get,
                gameAbilityEvents::get, groundTypes::get);

        // TODO: Populate this!
        RoundBasedTimerManager roundBasedTimerManager = null;

        // TODO: Populate this!
        ClockBasedTimerManager clockBasedTimerManager = null;

        // TODO: Populate this!
        RoundManagerImpl roundManager = null;

        var gameZoneFactory = new GameZoneFactoryImpl(
                c -> roundManager.setCharacterPositionInQueue(c, Integer.MAX_VALUE),
                roundManager::removeCharacterFromQueue,
                (terrain, gameZone, loc) -> ((AbstractGameZoneTerrain) terrain)
                        .assignGameZoneAfterAddedToGameZone(gameZone, loc));

        // TODO: Populate tilesPerBatch and threadPoolSize from configs somewhere
        var gameZoneHandler =
                new GameZoneHandler(gameZoneFactory, tileHandler, mapHandler, actions::get, 5, 5);

        var GameZoneRepo = new GameZoneRepoImpl(gameZoneHandler, fileLocations);

        var roundBasedTimerFactory = new RoundBasedTimerFactoryImpl(roundBasedTimerManager);

        var gameStateFactory =
                new GameStateFactoryImpl(GameZoneRepo, roundManager, roundBasedTimerManager,
                        clockBasedTimerManager, itemFactory, characterFactory,
                        roundBasedTimerFactory);
    }

    @Override
    protected void configure() {
    }
}
