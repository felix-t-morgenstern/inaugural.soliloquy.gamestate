package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileItems;
import soliloquy.specs.gamestate.factories.TileItemsFactory;

public class TileItemsFactoryStub implements TileItemsFactory {
    @Override
    public TileItems make(Tile tile) throws IllegalArgumentException {
        return new TileItemsStub(tile);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
