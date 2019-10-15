package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;

import java.util.ArrayList;
import java.util.function.Predicate;

public class TileFixtureItemsImpl extends HasDeletionInvariants implements TileFixtureItems {
    private final TileFixture TILE_FIXTURE;
    private final CollectionFactory COLLECTION_FACTORY;
    private final ArrayList<Item> CONTAINED_ITEMS;
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE;

    private static final Item ITEM_ARCHETYPE = new ItemArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileFixtureItemsImpl(TileFixture tileFixture, CollectionFactory collectionFactory,
                                Predicate<Item> itemIsPresentElsewhere) {
        if (tileFixture == null) {
            throw new IllegalArgumentException("TileFixtureItems: tileFixture must be non-null");
        }
        TILE_FIXTURE = tileFixture;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItems: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (itemIsPresentElsewhere == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItems: itemIsPresentElsewhere must be non-null");
        }
        ITEM_IS_PRESENT_ELSEWHERE = itemIsPresentElsewhere;
        CONTAINED_ITEMS = new ArrayList<>();
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
        for(Item item : CONTAINED_ITEMS) {
            if (!item.isDeleted()) {
                item.delete();
            }
        }
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return TileFixtureItems.class.getCanonicalName();
    }

    @Override
    public ReadableCollection<Item> representation() throws UnsupportedOperationException, IllegalStateException {
        enforceDeletionInvariants("getRepresentation");
        Collection<Item> items = COLLECTION_FACTORY.make(ITEM_ARCHETYPE);
        for(Item item : CONTAINED_ITEMS) {
            items.add(item);
        }
        return items.readOnlyRepresentation();
    }

    @Override
    public void add(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("add");
        enforceItemAssignmentInvariant(item, "add");
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.add: item must be non-null");
        }
        if (ITEM_IS_PRESENT_ELSEWHERE.test(item)) {
            throw new IllegalArgumentException("TileFixtureItems.add: item present elsewhere");
        }
        CONTAINED_ITEMS.add(item);
        item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TILE_FIXTURE);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("remove");
        enforceItemAssignmentInvariant(item, "remove");
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.remove: item must be non-null");
        }
        boolean itemWasInAggregate = CONTAINED_ITEMS.remove(item);
        if (itemWasInAggregate) {
            item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(null);
        }
        return itemWasInAggregate;
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("contains");
        enforceItemAssignmentInvariant(item, "contains");
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.contains: item must be non-null");
        }
        return CONTAINED_ITEMS.contains(item);
    }

    @Override
    protected String className() {
        return "TileFixtureItems";
    }

    @Override
    protected String containingClassName() {
        return "tile fixture";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return TILE_FIXTURE.isDeleted();
    }

    private void enforceItemAssignmentInvariant(Item item, String methodName) {
        if (item != null && CONTAINED_ITEMS.contains(item) &&
                item.getContainingTileFixture() != TILE_FIXTURE) {
            throw new IllegalStateException("TileFixtureItemsImpl." + methodName +
                    ": item is present in this class, but has not been assigned to this class");
        }
    }
}
