package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtureItems;

import java.util.ArrayList;

public class TileFixtureItems extends HasDeletionInvariants implements ITileFixtureItems {
    private final ITileFixture TILE_FIXTURE;
    private final ICollectionFactory COLLECTION_FACTORY;
    private final ArrayList<IItem> CONTAINED_ITEMS;

    private static final IItem ITEM_ARCHETYPE = new ItemArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileFixtureItems(ITileFixture tileFixture, ICollectionFactory collectionFactory) {
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
        for(IItem item : CONTAINED_ITEMS) {
            if (!item.isDeleted()) {
                item.delete();
            }
        }
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return ITileFixtureItems.class.getCanonicalName();
    }

    @Override
    public ICollection<IItem> getRepresentation() throws UnsupportedOperationException, IllegalStateException {
        enforceDeletionInvariants("getRepresentation");
        ICollection<IItem> representation = COLLECTION_FACTORY.make(ITEM_ARCHETYPE);
        for(IItem item : CONTAINED_ITEMS) {
            representation.add(item);
        }
        return representation;
    }

    @Override
    public void add(IItem item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("add");
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.add: item must be non-null");
        }
        CONTAINED_ITEMS.add(item);
    }

    @Override
    public boolean remove(IItem item) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("remove");
        if (item == null) {
            throw new IllegalArgumentException("TileFixtureItems.remove: item must be non-null");
        }
        return CONTAINED_ITEMS.remove(item);
    }

    @Override
    public boolean contains(IItem item) throws IllegalArgumentException, IllegalStateException {
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
