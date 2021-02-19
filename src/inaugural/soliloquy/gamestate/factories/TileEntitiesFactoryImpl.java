package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileEntitiesImpl;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileEntity;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;

public class TileEntitiesFactoryImpl implements TileEntitiesFactory {
    private final PairFactory PAIR_FACTORY;
    private final MapFactory MAP_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileEntitiesFactoryImpl(PairFactory pairFactory, MapFactory mapFactory) {
        if(pairFactory == null) {
            throw new IllegalArgumentException(
                    "TileEntitiesFactoryImpl: pairFactory must be non-null");
        }
        PAIR_FACTORY = pairFactory;
        if(mapFactory == null) {
            throw new IllegalArgumentException(
                    "TileEntitiesFactoryImpl: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
    }

    @Override
    public <TEntity extends TileEntity> TileEntities<TEntity> make(Tile tile,
                                                                   TEntity archetype)
            throws IllegalArgumentException {
        if(tile == null) {
            throw new IllegalArgumentException(
                    "TileEntitiesFactoryImpl.make: tile must be non-null");
        }
        return new TileEntitiesImpl<>(tile, archetype, PAIR_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return TileEntitiesFactory.class.getCanonicalName();
    }
}
