package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileCharacters;
import soliloquy.gamestate.specs.ITileCharactersFactory;

public class TileCharactersFactory implements ITileCharactersFactory {
    private final IMapFactory MAP_FACTORY;

    public TileCharactersFactory(IMapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "TileCharactersFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public ITileCharacters make(ITile tile) throws IllegalArgumentException {
        if (tile == null) {
            throw new IllegalArgumentException(
                    "TileCharactersFactory.make: tile must be non-null");
        }
        return new TileCharacters(tile, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ITileCharactersFactory.class.getCanonicalName();
    }
}
