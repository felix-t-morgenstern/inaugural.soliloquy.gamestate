package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtureItems;
import soliloquy.gamestate.specs.ITileFixtureItemsFactory;

public class TileFixtureItemsFactoryStub implements ITileFixtureItemsFactory {
    @Override
    public ITileFixtureItems make(ITileFixture tileFixture) {
        return new TileFixtureItemsStub(tileFixture);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
