package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.entities.*;
import inaugural.soliloquy.gamestate.entities.gameevents.GameEventFiringImpl;
import inaugural.soliloquy.gamestate.entities.timers.ClockBasedTimerManagerImpl;
import inaugural.soliloquy.gamestate.entities.timers.RoundBasedTimerManagerImpl;
import inaugural.soliloquy.gamestate.factories.*;
import inaugural.soliloquy.gamestate.infrastructure.GameSaveBlockerImpl;
import inaugural.soliloquy.gamestate.persistence.*;
import inaugural.soliloquy.tools.collections.Collections;
import inaugural.soliloquy.tools.module.AbstractModule;
import soliloquy.specs.common.entities.*;
import soliloquy.specs.common.entities.Runnable;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.game.Module;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.WallSegment;
import soliloquy.specs.io.graphics.Graphics;
import soliloquy.specs.io.graphics.assets.Sprite;
import soliloquy.specs.io.graphics.rendering.FrameExecutor;
import soliloquy.specs.ruleset.Ruleset;
import soliloquy.specs.ruleset.gameconcepts.ActiveCharactersProvider;
import soliloquy.specs.ruleset.gameconcepts.RoundEndHandling;
import soliloquy.specs.ruleset.gameconcepts.TurnHandling;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.Consumer;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static soliloquy.specs.gamestate.entities.CharacterEvents.CharacterEvent;

@SuppressWarnings("unused")
public class GameStateModule extends AbstractModule {
    public GameStateModule(Module commonModule,
                           Module rulesetModule,
                           Module ioModule,
                           Map<String, Path> fileLocations,
                           int gameZoneHandlerTilesPerBatch,
                           int gameZoneHandlerThreadPoolSize,
                           Consumer<Throwable> handleError,
                           Map<String, Runnable> runnables,
                           @SuppressWarnings("rawtypes") Map<String, Supplier> suppliers,
                           @SuppressWarnings("rawtypes")
                           Map<String, soliloquy.specs.common.entities.Consumer> consumers,
                           @SuppressWarnings("rawtypes") Map<String, BiConsumer> biConsumers,
                           @SuppressWarnings("rawtypes") Map<String, Function> functions,
                           @SuppressWarnings("rawtypes") Map<String, BiFunction> biFunctions) {
        var persistenceHandler = commonModule.provide(PersistenceHandler.class);
        @SuppressWarnings("rawtypes") var mapHandler =
                persistenceHandler.<Map>getTypeHandler(Map.class.getCanonicalName());
        var spriteHandler =
                persistenceHandler.<Sprite>getTypeHandler(Sprite.class.getCanonicalName());

        var ruleset = rulesetModule.provide(Ruleset.class);
        var activeCharactersProvider = rulesetModule.provide(ActiveCharactersProvider.class);
        var turnHandling = rulesetModule.provide(TurnHandling.class);
        var roundEndHandling = rulesetModule.provide(RoundEndHandling.class);
        Consumer<Character> addToEndOfRoundManager =
                rulesetModule.provide("addToEndOfRoundManager");
        Consumer<Character> removeFromRoundManager =
                rulesetModule.provide("removeFromRoundManager");

        var graphics = ioModule.provide(Graphics.class);
        var frameExecutor = ioModule.provide(FrameExecutor.class);

        // NB: This is almost certainly _NOT_ the correct place to store this for now; once I
        // have a clean place to register and store CharacterEvents, though, I can place this
        // somewhere better.
        var characterEvents = Collections.<String, CharacterEvent>mapOf();

        var party = new PartyImpl(mapOf());

        var gameZoneFactory = new GameZoneFactoryImpl(
                addToEndOfRoundManager, removeFromRoundManager,
                (terrain, zone, loc) -> {
                    switch (terrain) {
                        case Tile t -> ((TileImpl) t).assignGameZoneAfterAddedToGameZone(zone, loc);
                        case WallSegment s ->
                                ((WallSegmentImpl) s).assignGameZoneAfterAddedToGameZone(zone, loc);
                        default -> throw new IllegalStateException("Unexpected value: " + terrain);
                    }
                });

        var itemFactory = new ItemFactoryImpl();

        var itemHandler = new ItemHandler(ruleset.itemTypes()::get, mapHandler, itemFactory);

        var fixtureFactory = new TileFixtureFactoryImpl(TileFixtureItemsImpl::new);

        var fixtureHandler =
                new TileFixtureHandler(ruleset.fixtureTypes()::get, fixtureFactory, mapHandler,
                        itemHandler);

        var characterFactory =
                new CharacterFactoryImpl(CharacterEventsImpl::new, CharacterEquipmentSlotsImpl::new,
                        CharacterInventoryImpl::new, CharacterStatusEffectsImpl::new);

        var characterHandler = new CharacterHandler(characterFactory, ruleset.characterTypes()::get,
                ruleset.characterClassifications()::get, graphics::getImageAssetSet,
                ruleset.characterAITypes()::get, characterEvents::get,
                ruleset.variableStatisticTypes()::get, ruleset.statusEffectTypes()::get,
                ruleset.passiveAbilities()::getById, ruleset.activeAbilities()::getById,
                ruleset.reactiveAbilities()::getById, mapHandler, itemHandler);

        var tileHandler =
                new TileHandler(data -> new TileImpl(TileEntitiesImpl::new, data), characterHandler,
                        itemHandler, fixtureHandler, spriteHandler, mapHandler,
                        ruleset.gameMovementEvents()::get, ruleset.gameAbilityEvents()::get,
                        ruleset.groundTypes()::get);

        var gameZoneHandler = new GameZoneHandler(gameZoneFactory, tileHandler, mapHandler,
                consumers::get, gameZoneHandlerTilesPerBatch,
                gameZoneHandlerThreadPoolSize);

        var gameZoneRepo = new GameZoneRepoImpl(gameZoneHandler, fileLocations);

        var gameSaveBlocker = new GameSaveBlockerImpl();

        var gameEventFiring = new GameEventFiringImpl(gameSaveBlocker, handleError);

        var roundBasedTimerManager = new RoundBasedTimerManagerImpl(gameEventFiring);

        var roundBasedTimerFactory = new RoundBasedTimerFactoryImpl(roundBasedTimerManager);

        var clockBasedTimerManager = new ClockBasedTimerManagerImpl(frameExecutor);

        var roundManager = new RoundManagerImpl(roundBasedTimerManager, activeCharactersProvider,
                gameZoneRepo::currentGameZone, turnHandling, roundEndHandling);

        var gameState = new GameStateImpl(party, mapOf(), gameZoneRepo, roundManager,
                roundBasedTimerManager, clockBasedTimerManager, itemFactory, characterFactory,
                roundBasedTimerFactory);

        var gameStateFactory =
                new GameStateFactoryImpl(gameZoneRepo, roundManager, roundBasedTimerManager,
                        clockBasedTimerManager, itemFactory, characterFactory,
                        roundBasedTimerFactory);
    }

    @Override
    public <T> T provide(Class<T> aClass) throws IllegalArgumentException {
        return null;
    }

    @Override
    public <T> T provide(String s) throws IllegalArgumentException {
        return null;
    }
}
