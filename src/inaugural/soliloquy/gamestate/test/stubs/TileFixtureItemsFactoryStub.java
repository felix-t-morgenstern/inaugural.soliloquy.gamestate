package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.ITileFixture;
import soliloquy.specs.gamestate.entities.ITileFixtureItems;
import soliloquy.specs.gamestate.factories.ITileFixtureItemsFactory;

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
