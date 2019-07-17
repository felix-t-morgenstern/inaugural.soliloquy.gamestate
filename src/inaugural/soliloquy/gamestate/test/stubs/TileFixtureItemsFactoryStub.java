package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

public class TileFixtureItemsFactoryStub implements TileFixtureItemsFactory {
    @Override
    public TileFixtureItems make(TileFixture tileFixture) {
        return new TileFixtureItemsStub(tileFixture);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
