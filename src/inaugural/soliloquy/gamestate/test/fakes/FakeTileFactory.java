package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileFactory;

public class FakeTileFactory implements TileFactory {
    @Override
    public Tile make(int x, int y, VariableCache data)
            throws IllegalArgumentException {
        return new FakeTile(x, y, data);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
