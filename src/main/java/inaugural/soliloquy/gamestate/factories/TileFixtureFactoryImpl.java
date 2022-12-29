package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileFixtureImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.gamestate.factories.TileFixtureItemsFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.UUID;

public class TileFixtureFactoryImpl implements TileFixtureFactory {
    private final TileFixtureItemsFactory TILE_FIXTURE_ITEMS_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;

    public TileFixtureFactoryImpl(TileFixtureItemsFactory tileFixtureItemsFactory,
                                  VariableCacheFactory dataFactory) {
        TILE_FIXTURE_ITEMS_FACTORY = Check.ifNull(tileFixtureItemsFactory,
                "tileFixtureItemsFactory");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
    }

    @Override
    public TileFixture make(FixtureType fixtureType, VariableCache data)
            throws IllegalArgumentException {
        return make(fixtureType, data, UUID.randomUUID());
    }

    @Override
    public TileFixture make(FixtureType fixtureType, VariableCache data, UUID uuid)
            throws IllegalArgumentException {
        Check.ifNull(fixtureType, "fixtureType");
        Check.ifNull(uuid, "uuid");
        return new TileFixtureImpl(uuid, fixtureType,
                TILE_FIXTURE_ITEMS_FACTORY,
                data == null ? DATA_FACTORY.make() : data);
    }

    @Override
    public String getInterfaceName() {
        return TileFixtureFactory.class.getCanonicalName();
    }
}
