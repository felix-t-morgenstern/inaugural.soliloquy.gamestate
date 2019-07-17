package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.TileWallSegmentArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegments;

public class TileWallSegmentsImpl extends GameEntityMediatorUnsorted<TileWallSegment>
        implements TileWallSegments {
    private final Tile TILE;
    private final static TileWallSegment TILE_WALL_SEGMENT_ARCHETYPE =
            new TileWallSegmentArchetype();

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentsImpl(Tile tile, CollectionFactory collectionFactory) {
        super(collectionFactory);
        if (tile == null) {
            throw new IllegalArgumentException("TileWallSegments: tile must be non-null");
        }
        TILE = tile;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return TileWallSegments.class.getCanonicalName();
    }

    @Override
    protected TileWallSegment getArchetype() {
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
