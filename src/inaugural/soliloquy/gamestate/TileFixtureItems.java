package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtureItems;

public class TileFixtureItems implements ITileFixtureItems {
    // TODO: Implement and test this class (currently implemented as a shell for TileFixtureItemsFactory)
    private final ITileFixture TILE_FIXTURE;
    private final ICollectionFactory COLLECTION_FACTORY;

    public TileFixtureItems(ITileFixture tileFixture, ICollectionFactory collectionFactory) {
        TILE_FIXTURE = tileFixture;
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public ICollection<IItem> getContainedItemsRepresentation() throws UnsupportedOperationException, IllegalStateException {
        return null;
    }

    @Override
    public void addContainedItem(IItem iItem) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean removeContainedItem(IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean containsContainedItem(IItem iItem) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
