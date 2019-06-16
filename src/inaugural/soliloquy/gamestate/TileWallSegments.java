package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.TileWallSegmentArchetype;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileWallSegment;
import soliloquy.specs.gamestate.entities.ITileWallSegments;

public class TileWallSegments extends GameEntityMediatorUnsorted<ITileWallSegment>
        implements ITileWallSegments {
    private final ITile TILE;
    private final static ITileWallSegment TILE_WALL_SEGMENT_ARCHETYPE =
            new TileWallSegmentArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileWallSegments(ITile tile, ICollectionFactory collectionFactory) {
        super(collectionFactory);
        if (tile == null) {
            throw new IllegalArgumentException("TileWallSegments: tile must be non-null");
        }
        TILE = tile;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return ITileWallSegments.class.getCanonicalName();
    }

    @Override
    protected ITileWallSegment getArchetype() {
        return TILE_WALL_SEGMENT_ARCHETYPE;
    }

    @Override
    protected String className() {
        return "TileWallSegments";
    }

    @Override
    protected String containingClassName() {
        return "tile";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return TILE.isDeleted();
    }
}
