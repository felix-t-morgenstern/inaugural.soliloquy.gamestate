package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

public class TileFactoryImpl implements TileFactory {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY;
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileFactoryImpl(CoordinateFactory coordinateFactory,
                           TileEntitiesFactory tileEntitiesFactory,
                           TileWallSegmentsFactory tileWallSegmentsFactory,
                           CollectionFactory collectionFactory, MapFactory mapFactory) {
        if (coordinateFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: coordinateFactory cannot be null");
        }
        COORDINATE_FACTORY = coordinateFactory;
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
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public Tile make(int x, int y, VariableCache data) throws IllegalArgumentException {
        if (x < 0) {
            throw new IllegalArgumentException("TileFactoryImpl.make: x cannot be negative");
        }
        if (y < 0) {
            throw new IllegalArgumentException("TileFactoryImpl.make: y cannot be negative");
        }
        return new TileImpl(x, y, COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, COLLECTION_FACTORY, MAP_FACTORY, data);
    }

    @Override
    public String getInterfaceName() {
        return TileFactory.class.getCanonicalName();
    }
}
