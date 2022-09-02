package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileFixtureItemsImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

public class TileFixtureItemsFactoryImpl implements TileFixtureItemsFactory {
    @Override
    public TileFixtureItems make(TileFixture tileFixture) {
        return new TileFixtureItemsImpl(Check.ifNull(tileFixture, "tileFixture"));
    }

    @Override
    public String getInterfaceName() {
        return TileFixtureItemsFactory.class.getCanonicalName();
    }
}
