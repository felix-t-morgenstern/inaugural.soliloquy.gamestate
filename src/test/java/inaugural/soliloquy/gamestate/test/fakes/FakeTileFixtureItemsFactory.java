package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

public class FakeTileFixtureItemsFactory implements TileFixtureItemsFactory {
    @Override
    public TileFixtureItems make(TileFixture tileFixture) {
        return new FakeTileFixtureItems(tileFixture);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
