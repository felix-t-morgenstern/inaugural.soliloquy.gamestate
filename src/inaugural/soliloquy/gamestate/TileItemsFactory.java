package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileItems;
import soliloquy.gamestate.specs.ITileItemsFactory;

public class TileItemsFactory implements ITileItemsFactory {
    private final IMapFactory MAP_FACTORY;

    public TileItemsFactory(IMapFactory mapFactory) {
        if(mapFactory == null) {
            throw new IllegalArgumentException("TileItemsFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public ITileItems make(ITile tile) throws IllegalArgumentException {
        if(tile == null) {
            throw new IllegalArgumentException("TileItemsFactory.make: tile must be non-null");
        }
        return new TileItems(tile, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return ITileItemsFactory.class.getCanonicalName();
    }
}
