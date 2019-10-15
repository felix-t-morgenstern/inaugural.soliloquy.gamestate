package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;

import java.util.ArrayList;
import java.util.List;

public class TileFixtureItemsStub implements TileFixtureItems {
    public final TileFixture TILE_FIXTURE;
    public static List<Item> ITEMS = new ArrayList<>();

    private boolean _deleted;

    public TileFixtureItemsStub(TileFixture tileFixture) {
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
        ITEMS.add(item);
        item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TILE_FIXTURE);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        if(ITEMS.contains(item)) {
            item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(null);
        }
        return ITEMS.remove(item);
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        return ITEMS.contains(item);
    }
}
