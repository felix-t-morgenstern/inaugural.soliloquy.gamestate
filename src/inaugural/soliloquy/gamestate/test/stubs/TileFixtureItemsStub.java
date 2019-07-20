package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;

public class TileFixtureItemsStub implements TileFixtureItems {
    public final TileFixture TILE_FIXTURE;

    private boolean _deleted;

    TileFixtureItemsStub(TileFixture tileFixture) {
        TILE_FIXTURE = tileFixture;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _deleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _deleted;
    }

    @Override
    public ReadableCollection<Item> representation() throws UnsupportedOperationException, IllegalStateException {
        return null;
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        return false;
    }
}
