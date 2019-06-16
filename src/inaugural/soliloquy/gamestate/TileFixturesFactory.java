package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileFixtures;
import soliloquy.specs.gamestate.factories.ITileFixturesFactory;

public class TileFixturesFactory implements ITileFixturesFactory {
    private final IMapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileFixturesFactory(IMapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException("TileFixturesFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public ITileFixtures make(ITile tile) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException("TileFixturesFactory.make: tile must be non-null");
        }
        return new TileFixtures(tile, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ITileFixturesFactory.class.getCanonicalName();
    }
}
