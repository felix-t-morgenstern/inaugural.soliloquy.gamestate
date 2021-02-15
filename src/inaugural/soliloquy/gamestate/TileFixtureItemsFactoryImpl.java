package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;

public class TileFixtureItemsFactoryImpl implements TileFixtureItemsFactory {
    private final ListFactory LIST_FACTORY;

    public TileFixtureItemsFactoryImpl(ListFactory listFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    @Override
    public TileFixtureItems make(TileFixture tileFixture) {
        if (tileFixture == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItemsFactory.make: tileFixture must be non-null");
        }
        return new TileFixtureItemsImpl(tileFixture, LIST_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileFixtureItemsFactory.class.getCanonicalName();
    }
}
