package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.logger.Logger;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.function.Supplier;

public class CameraFactoryImpl implements CameraFactory {
    private final Game GAME;
    private final Logger LOGGER;
    private final CoordinateFactory COORDINATE_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final TileVisibility TILE_VISIBILITY;
    private final Supplier<GameZone> GET_GAME_ZONE;

    @SuppressWarnings("ConstantConditions")
    public CameraFactoryImpl(Game game, Logger logger, CoordinateFactory coordinateFactory,
                             CollectionFactory collectionFactory, MapFactory mapFactory,
                             TileVisibility tileVisibility, Supplier<GameZone> getGameZone) {
        if (game == null) {
            throw new IllegalArgumentException("CameraFactoryImpl: game cannot be null");
        }
        GAME = game;
        if (logger == null) {
            throw new IllegalArgumentException("CameraFactoryImpl: logger cannot be null");
        }
        LOGGER = logger;
        if (coordinateFactory == null) {
            throw new IllegalArgumentException("CameraFactoryImpl: coordinateFactory cannot be " +
                    "null");
        }
        COORDINATE_FACTORY = coordinateFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException("CameraFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException("CameraFactoryImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
        if (tileVisibility == null) {
            throw new IllegalArgumentException("CameraFactoryImpl: tileVisibility cannot be null");
        }
        TILE_VISIBILITY = tileVisibility;
        if (getGameZone == null) {
            throw new IllegalArgumentException("CameraFactoryImpl: getGameZone cannot be null");
        }
        GET_GAME_ZONE = getGameZone;
    }

    @Override
    public Camera make() {
        return new CameraImpl(GAME, LOGGER, COORDINATE_FACTORY, COLLECTION_FACTORY, MAP_FACTORY,
                TILE_VISIBILITY, GET_GAME_ZONE);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
