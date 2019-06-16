package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.gamestate.entities.ITileCharacters;
import soliloquy.specs.gamestate.factories.ITileCharactersFactory;

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
