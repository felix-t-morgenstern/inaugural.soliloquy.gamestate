package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileWallSegmentsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegments;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class TileWallSegmentsFactoryImpl implements TileWallSegmentsFactory {
    @Override
    public TileWallSegments make(Tile tile) throws IllegalArgumentException {
        return new TileWallSegmentsImpl(Check.ifNull(tile, "tile"));
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegmentsFactory.class.getCanonicalName();
    }
}
