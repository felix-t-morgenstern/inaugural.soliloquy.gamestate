package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtureItems;

public class TileFixtureItemsStub implements ITileFixtureItems {
    public final ITileFixture TILE_FIXTURE;

    public TileFixtureItemsStub(ITileFixture tileFixture) {
        TILE_FIXTURE = tileFixture;
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
    public String getInterfaceName() {
        return null;
    }
}
