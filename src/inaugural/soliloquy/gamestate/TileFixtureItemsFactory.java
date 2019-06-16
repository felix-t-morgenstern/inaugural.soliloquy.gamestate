package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.gamestate.entities.ITileFixture;
import soliloquy.specs.gamestate.entities.ITileFixtureItems;
import soliloquy.specs.gamestate.factories.ITileFixtureItemsFactory;

public class TileFixtureItemsFactory implements ITileFixtureItemsFactory {
    private final ICollectionFactory COLLECTION_FACTORY;

    public TileFixtureItemsFactory(ICollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItemsFactory: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public ITileFixtureItems make(ITileFixture tileFixture) {
        if (tileFixture == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItemsFactory.make: tileFixture must be non-null");
        }
        return new TileFixtureItems(tileFixture, COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ITileFixtureItemsFactory.class.getCanonicalName();
    }
}
