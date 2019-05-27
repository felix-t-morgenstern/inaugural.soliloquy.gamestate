package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileItems;

public class TileItems extends GameEntityMediatorWithZIndex<IItem> implements ITileItems {
    private final ITile TILE;
    private static final IItem ITEM_ARCHETYPE = new ItemArchetype();

    public TileItems(ITile tile, IMapFactory mapFactory) {
        super(mapFactory);
        if (tile == null) {
            throw new IllegalArgumentException("TileItems: tile must be non-null");
        }
        TILE = tile;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return ITileItems.class.getCanonicalName();
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
    protected boolean containingObjectIsDeleted() {
        return TILE.isDeleted();
    }

    @Override
    protected IItem getArchetype() {
        return ITEM_ARCHETYPE;
    }
}
