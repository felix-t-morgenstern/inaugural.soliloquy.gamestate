package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class TileFactoryImpl implements TileFactory {
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY;
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY;

    public TileFactoryImpl(TileEntitiesFactory tileEntitiesFactory,
                           TileWallSegmentsFactory tileWallSegmentsFactory) {
        TILE_ENTITIES_FACTORY = Check.ifNull(tileEntitiesFactory, "tileEntitiesFactory");
        TILE_WALL_SEGMENTS_FACTORY = Check.ifNull(tileWallSegmentsFactory,
                "tileWallSegmentsFactory");
    }

    @Override
    public Tile make(int x, int y, VariableCache data) throws IllegalArgumentException {
        Check.ifNonNegative(x, "x");
        Check.ifNonNegative(y, "y");
        return new TileImpl(x, y, TILE_ENTITIES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, data);
    }

    @Override
    public String getInterfaceName() {
        return TileFactory.class.getCanonicalName();
    }
}
