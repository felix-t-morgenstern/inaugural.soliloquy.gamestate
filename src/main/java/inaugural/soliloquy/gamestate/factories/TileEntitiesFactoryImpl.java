package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileEntitiesImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileEntity;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;

public class TileEntitiesFactoryImpl implements TileEntitiesFactory {
    public TileEntitiesFactoryImpl() {
    }

    @Override
    public <TEntity extends TileEntity> TileEntities<TEntity> make(Tile tile, TEntity archetype)
            throws IllegalArgumentException {
        Check.ifNull(tile, "tile");
        return new TileEntitiesImpl<>(tile, archetype);
    }

    @Override
    public String getInterfaceName() {
        return TileEntitiesFactory.class.getCanonicalName();
    }
}
