package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileFixtureImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class TileFixtureFactoryImpl implements TileFixtureFactory {
    private final Function<TileFixture, TileFixtureItems> TILE_FIXTURE_ITEMS_FACTORY;

    public TileFixtureFactoryImpl(Function<TileFixture, TileFixtureItems> tileFixtureItemsFactory) {
        TILE_FIXTURE_ITEMS_FACTORY = Check.ifNull(tileFixtureItemsFactory,
                "tileFixtureItemsFactory");
    }

    @Override
    public TileFixture make(FixtureType fixtureType, Map<String, Object> data)
            throws IllegalArgumentException {
        return make(fixtureType, data, UUID.randomUUID());
    }

    @Override
    public TileFixture make(FixtureType fixtureType, Map<String, Object> data, UUID uuid)
            throws IllegalArgumentException {
        Check.ifNull(fixtureType, "fixtureType");
        Check.ifNull(uuid, "uuid");
        return new TileFixtureImpl(uuid, fixtureType,
                TILE_FIXTURE_ITEMS_FACTORY,
                data == null ? mapOf() : data);
    }
}
