package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileEntity;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;

public class TileEntitiesFactoryStub implements TileEntitiesFactory {
    @Override
    public <TEntity extends TileEntity> TileEntities<TEntity> make(Tile tile, TEntity tEntity)
            throws IllegalArgumentException {
        return new TileEntitiesStub<>(tile);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
