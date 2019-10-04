package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileItems;

import java.util.ArrayList;
import java.util.List;

public class TileItemsStub implements TileItems {
    public static List<Item> ITEMS = new ArrayList<>();

    private final Tile TILE;

    public TileItemsStub() {
        TILE = new TileStub();
    }

    public TileItemsStub(Tile tile) {
        TILE = tile;
    }

    @Override
    public ReadableMap<Item, Integer> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void add(Item item) throws IllegalArgumentException {
        ITEMS.add(item);
        item.assignTileToItemAfterAddingItemToTileItems(TILE);
    }

    @Override
    public void add(Item item, int i) throws IllegalArgumentException {

    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException {
        boolean itemPresent = ITEMS.remove(item);
        if (itemPresent) {
            item.assignTileToItemAfterAddingItemToTileItems(TILE);
        }
        return itemPresent;
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException {
        return ITEMS.contains(item);
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
