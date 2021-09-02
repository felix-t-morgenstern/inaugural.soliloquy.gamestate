package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileFixtureImpl;
import inaugural.soliloquy.tools.Check;
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
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;

    public TileFixtureFactoryImpl(EntityUuidFactory entityUuidFactory,
                                  TileFixtureItemsFactory tileFixtureItemsFactory,
                                  VariableCacheFactory dataFactory) {
        ENTITY_UUID_FACTORY = Check.ifNull(entityUuidFactory, "entityUuidFactory");
        TILE_FIXTURE_ITEMS_FACTORY = Check.ifNull(tileFixtureItemsFactory,
                "tileFixtureItemsFactory");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
    }

    @Override
    public TileFixture make(FixtureType fixtureType, VariableCache data)
            throws IllegalArgumentException {
        return make(fixtureType, data, ENTITY_UUID_FACTORY.createRandomEntityUuid());
    }

    @Override
    public TileFixture make(FixtureType fixtureType, VariableCache data, EntityUuid entityUuid)
            throws IllegalArgumentException {
        Check.ifNull(fixtureType, "fixtureType");
        Check.ifNull(entityUuid, "entityUuid");
        return new TileFixtureImpl(entityUuid, fixtureType,
                TILE_FIXTURE_ITEMS_FACTORY,
                data == null ? DATA_FACTORY.make() : data);
    }

    @Override
    public String getInterfaceName() {
        return TileFixtureFactory.class.getCanonicalName();
    }
}
