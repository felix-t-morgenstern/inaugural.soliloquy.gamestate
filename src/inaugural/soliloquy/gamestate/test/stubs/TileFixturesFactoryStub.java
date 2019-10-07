package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixtures;
import soliloquy.specs.gamestate.factories.TileFixturesFactory;

public class TileFixturesFactoryStub implements TileFixturesFactory {
    @Override
    public TileFixtures make(Tile tile) throws IllegalArgumentException {
        return new TileFixturesStub(tile);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
