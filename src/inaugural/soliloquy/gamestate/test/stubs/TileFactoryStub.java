package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileFactory;

public class TileFactoryStub implements TileFactory {
    @Override
    public Tile make(GameZone gameZone, ReadableCoordinate location) throws IllegalArgumentException {
        return new TileStub(gameZone, location);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
