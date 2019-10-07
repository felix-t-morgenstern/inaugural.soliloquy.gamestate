package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegments;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class TileWallSegmentsFactoryStub implements TileWallSegmentsFactory {
    @Override
    public TileWallSegments make(Tile tile) throws IllegalArgumentException {
        return new TileWallSegmentsStub(tile);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
