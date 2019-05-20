package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileWallSegment;
import soliloquy.gamestate.specs.ITileWallSegments;

public class TileWallSegments implements ITileWallSegments {
    private final ITile TILE;
    private final ICollectionFactory COLLECTION_FACTORY;

    public TileWallSegments(ITile tile, ICollectionFactory collectionFactory) {
        TILE = tile;
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public ICollection<ITileWallSegment> getTileWallSegmentsRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void addTileWallSegment(ITileWallSegment iTileWallSegment) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public boolean removeTileWallSegment(ITileWallSegment iTileWallSegment) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean containsTileWallSegment(ITileWallSegment iTileWallSegment) throws IllegalArgumentException, IllegalStateException {
        return false;
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
