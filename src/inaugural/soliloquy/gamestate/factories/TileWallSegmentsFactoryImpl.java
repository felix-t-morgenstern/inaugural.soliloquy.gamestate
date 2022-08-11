package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileWallSegmentsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegments;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class TileWallSegmentsFactoryImpl implements TileWallSegmentsFactory {
    private final MapFactory MAP_FACTORY;

    public TileWallSegmentsFactoryImpl(MapFactory mapFactory) {
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public TileWallSegments make(Tile tile) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory.make: tile must be non-null");
        }
        return new TileWallSegmentsImpl(tile, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegmentsFactory.class.getCanonicalName();
    }
}
