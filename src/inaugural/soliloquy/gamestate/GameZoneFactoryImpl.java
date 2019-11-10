package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.GameZoneFactory;
import soliloquy.specs.gamestate.factories.TileFactory;

public class GameZoneFactoryImpl implements GameZoneFactory {
    private final TileFactory TILE_FACTORY;
    private final CoordinateFactory COORDINATE_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public GameZoneFactoryImpl(TileFactory tileFactory,
                               CoordinateFactory coordinateFactory,
                               CollectionFactory collectionFactory,
                               GenericParamsSetFactory genericParamsSetFactory) {
        if (tileFactory == null) {
            throw new IllegalArgumentException("GameZoneFactoryImpl: tileFactory cannot be null");
        }
        TILE_FACTORY = tileFactory;
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
        if (genericParamsSetFactory == null) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl: genericParamsSetFactory cannot be null");
        }
        GENERIC_PARAMS_SET_FACTORY = genericParamsSetFactory;
    }

    @Override
    public GameZone make(String id, String name, String zoneType,
                         ReadableCoordinate maxCoordinates, GenericParamsSet data)
            throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("GameZoneFactoryImpl.make: id cannot be null");
        }
        if (id.equals("")) {
            throw new IllegalArgumentException("GameZoneFactoryImpl.make: id cannot be empty");
        }
        if (name == null) {
            throw new IllegalArgumentException("GameZoneFactoryImpl.make: name cannot be null");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("GameZoneFactoryImpl.make: name cannot be empty");
        }
        if (maxCoordinates == null) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl.make: maxCoordinates cannot be null");
        }
        if (maxCoordinates.getX() < 0) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl.make: maxCoordinates has negative x value (" +
                            maxCoordinates.getX() + ")");
        }
        if (maxCoordinates.getY() < 0) {
            throw new IllegalArgumentException(
                    "GameZoneFactoryImpl.make: maxCoordinates has negative y value (" +
                            maxCoordinates.getY() + ")");
        }
        return new GameZoneImpl(id, name, zoneType, maxCoordinates, TILE_FACTORY,
                COORDINATE_FACTORY, COLLECTION_FACTORY,
                data == null ? GENERIC_PARAMS_SET_FACTORY.make() : data);
    }

    @Override
    public String getInterfaceName() {
        return GameZoneFactory.class.getCanonicalName();
    }
}
