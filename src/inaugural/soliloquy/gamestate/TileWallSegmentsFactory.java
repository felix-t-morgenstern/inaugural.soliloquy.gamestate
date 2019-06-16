package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileWallSegments;
import soliloquy.specs.gamestate.factories.ITileWallSegmentsFactory;

public class TileWallSegmentsFactory implements ITileWallSegmentsFactory {
    private final ICollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
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
