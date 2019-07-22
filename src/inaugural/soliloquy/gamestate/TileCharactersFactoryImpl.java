package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileCharacters;
import soliloquy.specs.gamestate.factories.TileCharactersFactory;

public class TileCharactersFactoryImpl implements TileCharactersFactory {
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileCharactersFactoryImpl(MapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "TileCharactersFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public TileCharacters make(Tile tile)
            throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException(
                    "TileCharactersFactory.make: tile must be non-null");
        }
        return new TileCharactersImpl(tile, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileCharactersFactory.class.getCanonicalName();
    }
}
