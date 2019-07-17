package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegments;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class TileWallSegmentsFactoryImpl implements TileWallSegmentsFactory {
    private final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentsFactoryImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public TileWallSegments make(Tile tile) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory.make: tile must be non-null");
        }
        return new TileWallSegmentsImpl(tile, COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegmentsFactory.class.getCanonicalName();
    }
}
