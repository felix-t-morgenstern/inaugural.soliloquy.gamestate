package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegments;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class TileWallSegmentsFactoryImpl implements TileWallSegmentsFactory {
    private final PairFactory PAIR_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentsFactoryImpl(PairFactory pairFactory,
                                       CollectionFactory collectionFactory,
                                       MapFactory mapFactory) {
        if (pairFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory: pairFactory must be non-null");
        }
        PAIR_FACTORY = pairFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory: collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public TileWallSegments make(Tile tile) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentsFactory.make: tile must be non-null");
        }
        return new TileWallSegmentsImpl(tile, PAIR_FACTORY, COLLECTION_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegmentsFactory.class.getCanonicalName();
    }
}
