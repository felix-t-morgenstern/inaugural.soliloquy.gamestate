package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixtures;
import soliloquy.specs.gamestate.factories.TileFixturesFactory;

public class TileFixturesFactoryImpl implements TileFixturesFactory {
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileFixturesFactoryImpl(MapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException("TileFixturesFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public TileFixtures make(Tile tile) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException("TileFixturesFactory.make: tile must be non-null");
        }
        return new TileFixturesImpl(tile, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileFixturesFactory.class.getCanonicalName();
    }
}
