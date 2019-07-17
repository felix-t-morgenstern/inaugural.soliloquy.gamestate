package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadOnlyCollection;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;

import java.util.ArrayList;

public class TileFixtureItemsImpl extends HasDeletionInvariants implements TileFixtureItems {
    private final TileFixture TILE_FIXTURE;
    private final CollectionFactory COLLECTION_FACTORY;
    private final ArrayList<Item> CONTAINED_ITEMS;

    private static final Item ITEM_ARCHETYPE = new ItemArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileFixtureItemsImpl(TileFixture tileFixture, CollectionFactory collectionFactory) {
        if (tileFixture == null) {
            throw new IllegalArgumentException("TileFixtureItems: tileFixture must be non-null");
        }
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureItems: collectionFactory must be non-null");
        }
        TILE_FIXTURE = tileFixture;
        COLLECTION_FACTORY = collectionFactory;
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
    public ReadOnlyCollection<Item> representation() throws UnsupportedOperationException, IllegalStateException {
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
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.add: item must be non-null");
        }
        CONTAINED_ITEMS.add(item);
    }

    @Override
    public boolean remove(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("remove");
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.remove: item must be non-null");
        }
        return CONTAINED_ITEMS.remove(item);
    }

    @Override
    public boolean contains(Item item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("contains");
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
}
