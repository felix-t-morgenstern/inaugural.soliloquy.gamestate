package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileItems;
import soliloquy.specs.gamestate.factories.TileItemsFactory;

public class TileItemsFactoryImpl implements TileItemsFactory {
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileItemsFactoryImpl(MapFactory mapFactory) {
        if(mapFactory == null) {
            throw new IllegalArgumentException("TileItemsFactory: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public TileItems make(Tile tile) throws IllegalArgumentException {
        if(tile == null) {
            throw new IllegalArgumentException("TileItemsFactory.make: tile must be non-null");
        }
        return new TileItemsImpl(tile, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileItemsFactory.class.getCanonicalName();
    }
}
