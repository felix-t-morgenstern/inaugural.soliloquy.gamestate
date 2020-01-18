package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

public class TileFixtureFactoryImpl implements TileFixtureFactory {
    private final EntityUuidFactory ENTITY_UUID_FACTORY;
    private final CoordinateFactory COORDINATE_FACTORY;
    private final CollectionFactory COLLECTION_FACTORY;
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileFixtureFactoryImpl(EntityUuidFactory entityUuidFactory,
                                  CoordinateFactory coordinateFactory,
                                  CollectionFactory collectionFactory,
                                  TileFixtureItemsFactory tileFixtureItemsFactory,
                                  VariableCacheFactory dataFactory) {
        if (entityUuidFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureFactoryImpl: entityUuidFactory cannot be null");
        }
        ENTITY_UUID_FACTORY = entityUuidFactory;
        if (coordinateFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureFactoryImpl: coordinateFactory cannot be null");
        }
        COORDINATE_FACTORY = coordinateFactory;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (tileFixtureItemsFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureFactoryImpl: tileFixtureItemsFactory cannot be null");
        }
        TILE_FIXTURE_ITEMS_FACTORY = tileFixtureItemsFactory;
        if (dataFactory == null) {
            throw new IllegalArgumentException(
                    "TileFixtureFactoryImpl: dataFactory cannot be null");
        }
        DATA_FACTORY = dataFactory;
    }

    @Override
    public TileFixture make(FixtureType fixtureType, VariableCache data)
            throws IllegalArgumentException {
        return make(fixtureType, data, ENTITY_UUID_FACTORY.createRandomEntityUuid());
    }

    @Override
    public TileFixture make(FixtureType fixtureType, VariableCache data, EntityUuid entityUuid)
            throws IllegalArgumentException {
        if (fixtureType == null) {
            throw new IllegalArgumentException(
                    "TileFixtureFactoryImpl.make: fixtureType cannot be null");
        }
        if (entityUuid == null) {
            throw new IllegalArgumentException(
                    "TileFixtureFactoryImpl.make: entityUuid cannot be null");
        }
        return new TileFixtureImpl(entityUuid, fixtureType, COORDINATE_FACTORY, COLLECTION_FACTORY,
                TILE_FIXTURE_ITEMS_FACTORY,
                data == null ? DATA_FACTORY.make() : data);
    }

    @Override
    public String getInterfaceName() {
        return TileFixtureFactory.class.getCanonicalName();
    }
}
