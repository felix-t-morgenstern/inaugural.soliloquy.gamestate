package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
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

    public CameraFactoryImpl(CoordinateFactory coordinateFactory,
                             CollectionFactory collectionFactory, MapFactory mapFactory,
                             TileVisibility tileVisibility) {
        COORDINATE_FACTORY = Check.ifNull(coordinateFactory, "coordinateFactory");
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "collectionFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
        TILE_VISIBILITY = Check.ifNull(tileVisibility, "tileVisibility");
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
