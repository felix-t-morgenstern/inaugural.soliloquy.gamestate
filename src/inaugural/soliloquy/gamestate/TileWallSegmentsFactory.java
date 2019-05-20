package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.ICollectionFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileWallSegments;
import soliloquy.gamestate.specs.ITileWallSegmentsFactory;

public class TileWallSegmentsFactory implements ITileWallSegmentsFactory {
    private final ICollectionFactory COLLECTION_FACTORY;

    public TileWallSegmentsFactory(ICollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public ITileWallSegments make(ITile tile) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory.make: tile must be non-null");
        }
        return new TileWallSegments(tile, COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ITileWallSegmentsFactory.class.getCanonicalName();
    }
}
