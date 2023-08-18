package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public class TileFixtureItemsImpl extends CanTellIfItemIsPresentElsewhere
        implements TileFixtureItems {
    private final TileFixture TILE_FIXTURE;
    private final List<Item> CONTAINED_ITEMS;

    @SuppressWarnings("ConstantConditions")
    public TileFixtureItemsImpl(TileFixture tileFixture) {
        TILE_FIXTURE = Check.ifNull(tileFixture, "tileFixture");
        CONTAINED_ITEMS = listOf();
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        for (var item : CONTAINED_ITEMS) {
            if (!item.isDeleted()) {
                item.delete();
            }
        }
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants();
        return TileFixtureItems.class.getCanonicalName();
    }

    @Override
    public List<Item> representation() throws UnsupportedOperationException, IllegalStateException {
        enforceDeletionInvariants();
        return listOf(CONTAINED_ITEMS);
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemAssignmentInvariant(item, "add");
        Check.ifNull(item, "item");
        if (itemIsPresentElsewhere(item)) {
            throw new IllegalArgumentException("TileFixtureItems.add: item present elsewhere");
        }
        CONTAINED_ITEMS.add(item);
        item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemAssignmentInvariant(item, "remove");
        Check.ifNull(item, "item");
        var itemWasInAggregate = CONTAINED_ITEMS.remove(item);
        if (itemWasInAggregate) {
            item.assignTileFixtureAfterAddedItemToTileFixtureItems(null);
        }
        return itemWasInAggregate;
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemAssignmentInvariant(item, "contains");
        Check.ifNull(item, "item");
        return CONTAINED_ITEMS.contains(item);
    }

    @Override
    protected String containingClassName() {
        return "tileFixture";
    }

    @Override
    protected Deletable getContainingObject() {
        return TILE_FIXTURE;
    }

    private void enforceItemAssignmentInvariant(Item item, String methodName) {
        if (item != null && CONTAINED_ITEMS.contains(item) &&
                item.tileFixture() != TILE_FIXTURE) {
            throw new IllegalStateException("TileFixtureItemsImpl." + methodName +
                    ": item is present in this class, but has not been assigned to this class");
        }
    }
}
