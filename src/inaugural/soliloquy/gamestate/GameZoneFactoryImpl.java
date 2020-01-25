package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

public class GameZoneFactoryImpl implements GameZoneFactory {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public GameZoneFactoryImpl(CoordinateFactory coordinateFactory,
                               CollectionFactory collectionFactory) {
        if (coordinateFactory == null) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl: coordinateFactory cannot be null");
        }
        COORDINATE_FACTORY = coordinateFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public GameZone make(String id, String zoneType, Tile[][] tiles, VariableCache data)
            throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("GameZoneFactoryImpl.make: id cannot be null");
        }
        if (id.equals("")) {
            throw new IllegalArgumentException("GameZoneFactoryImpl.make: id cannot be empty");
        }
        return new GameZoneImpl(id, zoneType, tiles, COORDINATE_FACTORY, COLLECTION_FACTORY, data);
    }

    @Override
    public String getInterfaceName() {
        return GameZoneFactory.class.getCanonicalName();
    }
}
