package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.CameraImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.function.Supplier;

public class CameraFactoryImpl implements CameraFactory {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final ListFactory LIST_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final TileVisibility TILE_VISIBILITY;

    public CameraFactoryImpl(CoordinateFactory coordinateFactory,
                             ListFactory listFactory, MapFactory mapFactory,
                             TileVisibility tileVisibility) {
        COORDINATE_FACTORY = Check.ifNull(coordinateFactory, "coordinateFactory");
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
        TILE_VISIBILITY = Check.ifNull(tileVisibility, "tileVisibility");
    }

    @Override
    public Camera make(Supplier<GameZone> getCurrentGameZone) throws IllegalArgumentException {
        return new CameraImpl(COORDINATE_FACTORY, LIST_FACTORY, MAP_FACTORY,
                TILE_VISIBILITY, getCurrentGameZone);
    }

    @Override
    public String getInterfaceName() {
        return CameraFactory.class.getCanonicalName();
    }
}
