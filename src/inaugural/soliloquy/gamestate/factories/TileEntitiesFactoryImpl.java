package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileEntitiesImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileEntity;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;

public class TileEntitiesFactoryImpl implements TileEntitiesFactory {
    private final MapFactory MAP_FACTORY;

    public TileEntitiesFactoryImpl(MapFactory mapFactory) {
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public <TEntity extends TileEntity> TileEntities<TEntity> make(Tile tile,
                                                                   TEntity archetype)
            throws IllegalArgumentException {
        Check.ifNull(tile, "tile");
        return new TileEntitiesImpl<>(tile, archetype, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileEntitiesFactory.class.getCanonicalName();
    }
}
