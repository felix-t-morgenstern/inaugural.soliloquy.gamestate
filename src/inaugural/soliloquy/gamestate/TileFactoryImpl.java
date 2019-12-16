package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.*;

public class TileFactoryImpl implements TileFactory {
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY;
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileFactoryImpl(TileEntitiesFactory tileEntitiesFactory,
                           TileWallSegmentsFactory tileWallSegmentsFactory,
                           CollectionFactory collectionFactory) {
        if (tileEntitiesFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: tileEntitiesFactory cannot be null");
        }
        TILE_ENTITIES_FACTORY = tileEntitiesFactory;
        if (tileWallSegmentsFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: tileWallSegmentsFactory cannot be null");
        }
        TILE_WALL_SEGMENTS_FACTORY = tileWallSegmentsFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public Tile make(GameZone gameZone, ReadableCoordinate location, GenericParamsSet data)
            throws IllegalArgumentException {
        if (gameZone == null) {
            throw new IllegalArgumentException("TileFactoryImpl.make: gameZone cannot be null");
        }
        if (location == null) {
            throw new IllegalArgumentException("TileFactoryImpl.make: location cannot be null");
        }
        return new TileImpl(gameZone, location, TILE_ENTITIES_FACTORY, TILE_WALL_SEGMENTS_FACTORY,
                COLLECTION_FACTORY, data);
    }

    @Override
    public String getInterfaceName() {
        return TileFactory.class.getCanonicalName();
    }
}
