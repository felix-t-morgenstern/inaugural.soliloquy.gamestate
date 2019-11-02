package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.*;

public class TileFactoryImpl implements TileFactory {
    private final TileCharactersFactory TILE_CHARACTERS_FACTORY;
    private final TileItemsFactory TILE_ITEMS_FACTORY;
    private final TileFixturesFactory TILE_FIXTURES_FACTORY;
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileFactoryImpl(TileCharactersFactory tileCharactersFactory,
                           TileItemsFactory tileItemsFactory,
                           TileFixturesFactory tileFixturesFactory,
                           TileWallSegmentsFactory tileWallSegmentsFactory,
                           MapFactory mapFactory, CollectionFactory collectionFactory,
                           GenericParamsSetFactory genericParamsSet) {
        if (tileCharactersFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: tileCharactersFactory cannot be null");
        }
        TILE_CHARACTERS_FACTORY = tileCharactersFactory;
        if (tileItemsFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: tileItemsFactory cannot be null");
        }
        TILE_ITEMS_FACTORY = tileItemsFactory;
        if (tileFixturesFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: tileFixturesFactory cannot be null");
        }
        TILE_FIXTURES_FACTORY = tileFixturesFactory;
        if (tileWallSegmentsFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: tileWallSegmentsFactory cannot be null");
        }
        TILE_WALL_SEGMENTS_FACTORY = tileWallSegmentsFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (genericParamsSet == null) {
            throw new IllegalArgumentException(
                    "TileFactoryImpl: genericParamsSet cannot be null");
        }
        GENERIC_PARAMS_SET_FACTORY = genericParamsSet;
    }

    @Override
    public Tile make(GameZone gameZone, ReadableCoordinate location)
            throws IllegalArgumentException {
        if (gameZone == null) {
            throw new IllegalArgumentException("TileFactoryImpl.make: gameZone cannot be null");
        }
        if (location == null) {
            throw new IllegalArgumentException("TileFactoryImpl.make: location cannot be null");
        }
        return new TileImpl(gameZone, location, TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileFactory.class.getCanonicalName();
    }
}
