package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class TileFactoryImpl implements TileFactory {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY;
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY;

    public TileFactoryImpl(CoordinateFactory coordinateFactory,
                           TileEntitiesFactory tileEntitiesFactory,
                           TileWallSegmentsFactory tileWallSegmentsFactory) {
        COORDINATE_FACTORY = Check.ifNull(coordinateFactory, "coordinateFactory");
        TILE_ENTITIES_FACTORY = Check.ifNull(tileEntitiesFactory, "tileEntitiesFactory");
        TILE_WALL_SEGMENTS_FACTORY = Check.ifNull(tileWallSegmentsFactory,
                "tileWallSegmentsFactory");
    }

    @Override
    public Tile make(int x, int y, VariableCache data) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException("TileFactoryImpl.make: x cannot be negative");
        }
        if (y < 0) {
            throw new IllegalArgumentException("TileFactoryImpl.make: y cannot be negative");
        }
        return new TileImpl(x, y, COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, data);
    }

    @Override
    public String getInterfaceName() {
        return TileFactory.class.getCanonicalName();
    }
}
