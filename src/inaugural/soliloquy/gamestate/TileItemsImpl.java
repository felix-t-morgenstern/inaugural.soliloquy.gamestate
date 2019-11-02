package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileItems;

import java.util.function.Predicate;

public class TileItemsImpl extends GameEntityMediatorWithZIndex<Item> implements TileItems {
    private final Tile TILE;
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE;
    private static final Item ITEM_ARCHETYPE = new ItemArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileItemsImpl(Tile tile, MapFactory mapFactory,
                         Predicate<Item> itemIsPresentElsewhere) {
        super(mapFactory);
        if (tile == null) {
            throw new IllegalArgumentException("TileItems: tile must be non-null");
        }
        TILE = tile;
        if (itemIsPresentElsewhere == null) {
            throw new IllegalArgumentException("TileItems: itemIsPresentElsewhere must be " +
                    "non-null");
        }
        ITEM_IS_PRESENT_ELSEWHERE = itemIsPresentElsewhere;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return TileItems.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "TileItems";
    }

    @Override
    protected String containingClassName() {
        return "tile";
    }

    @Override
    protected Deletable getContainingObject() {
        return TILE;
    }

    @Override
    protected Item getArchetype() {
        return ITEM_ARCHETYPE;
    }

    @Override
    protected void enforceAssignmentInvariant(Item item, String methodName) {
        if (item != null && ENTITIES.containsKey(item) &&
                item.getContainingTile() != TILE) {
            throw new IllegalStateException("TileItemsImpl." + methodName + ": item is present " +
                    "in this class, but this class is not assigned to item");
        }
    }

    @Override
    protected void assignEntityToAggregate(Item item) {
        item.assignTileToItemAfterAddingItemToTileItems(TILE);
    }

    @Override
    protected void removeEntityFromAggregate(Item item) {
        item.assignTileToItemAfterAddingItemToTileItems(null);
    }

    @Override
    boolean entityIsPresentElsewhere(Item item) {
        return ITEM_IS_PRESENT_ELSEWHERE.test(item);
    }
}
