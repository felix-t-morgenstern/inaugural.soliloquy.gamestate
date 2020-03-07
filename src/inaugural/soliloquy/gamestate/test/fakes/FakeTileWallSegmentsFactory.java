package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegments;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class FakeTileWallSegmentsFactory implements TileWallSegmentsFactory {
    @Override
    public TileWallSegments make(Tile tile) throws IllegalArgumentException {
        return new FakeTileWallSegments(tile);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
