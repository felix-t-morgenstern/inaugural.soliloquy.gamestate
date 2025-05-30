package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public class FakeTileFixtureItems implements TileFixtureItems {
    public final TileFixture TILE_FIXTURE;

    public final java.util.List<Item> _items = listOf();

    private boolean _deleted;

    public FakeTileFixtureItems(TileFixture tileFixture) {
        TILE_FIXTURE = tileFixture;
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
    public List<Item> representation() throws UnsupportedOperationException, IllegalStateException {
        return listOf(_items);
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        _items.add(item);
        item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        if (_items.contains(item)) {
            item.assignTileFixtureAfterAddedItemToTileFixtureItems(null);
        }
        return _items.remove(item);
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        return _items.contains(item);
    }
}
