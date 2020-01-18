package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileFactory;

public class TileFactoryStub implements TileFactory {
    @Override
    public Tile make(GameZone gameZone, int x, int y, VariableCache data)
            throws IllegalArgumentException {
        return new TileStub(gameZone, x, y, data);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
