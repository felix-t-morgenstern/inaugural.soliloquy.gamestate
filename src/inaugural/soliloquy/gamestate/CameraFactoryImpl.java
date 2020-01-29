package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.function.Supplier;

public class CameraFactoryImpl implements CameraFactory {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final TileVisibility TILE_VISIBILITY;

    @SuppressWarnings("ConstantConditions")
    public CameraFactoryImpl(CoordinateFactory coordinateFactory,
                             CollectionFactory collectionFactory, MapFactory mapFactory,
                             TileVisibility tileVisibility) {
        if (coordinateFactory == null) {
            throw new IllegalArgumentException(
                    "CameraFactoryImpl: coordinateFactory cannot be null");
        }
        COORDINATE_FACTORY = coordinateFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CameraFactoryImpl: collectionFactory cannot be null");
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
    }

    @Override
    public Camera make(Supplier<GameZone> getCurrentGameZone) throws IllegalArgumentException {
        return new CameraImpl(COORDINATE_FACTORY, COLLECTION_FACTORY, MAP_FACTORY,
                TILE_VISIBILITY, getCurrentGameZone);
    }

    @Override
    public String getInterfaceName() {
        return CameraFactory.class.getCanonicalName();
    }
}
