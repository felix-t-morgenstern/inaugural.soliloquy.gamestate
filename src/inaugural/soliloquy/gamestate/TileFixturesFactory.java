package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileFixtures;
import soliloquy.gamestate.specs.ITileFixturesFactory;

public class TileFixturesFactory implements ITileFixturesFactory {
    private final IMapFactory MAP_FACTORY;

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
