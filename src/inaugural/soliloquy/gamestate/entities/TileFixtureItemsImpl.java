package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;

import java.util.ArrayList;
import java.util.List;

public class TileFixtureItemsImpl extends CanTellIfItemIsPresentElsewhere
        implements TileFixtureItems {
    private final TileFixture TILE_FIXTURE;
    private final ArrayList<Item> CONTAINED_ITEMS;

    @SuppressWarnings("ConstantConditions")
    public TileFixtureItemsImpl(TileFixture tileFixture) {
        TILE_FIXTURE = Check.ifNull(tileFixture, "tileFixture");
        CONTAINED_ITEMS = new ArrayList<>();
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        for (Item item : CONTAINED_ITEMS) {
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
        return new ArrayList<>(CONTAINED_ITEMS);
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemAssignmentInvariant(item, "add");
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.add: item must be non-null");
        }
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
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.remove: item must be non-null");
        }
        boolean itemWasInAggregate = CONTAINED_ITEMS.remove(item);
        if (itemWasInAggregate) {
            item.assignTileFixtureAfterAddedItemToTileFixtureItems(null);
        }
        return itemWasInAggregate;
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemAssignmentInvariant(item, "contains");
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.contains: item must be non-null");
        }
        return CONTAINED_ITEMS.contains(item);
    }

    @Override
    protected String containingClassName() {
        return "tile fixture";
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
