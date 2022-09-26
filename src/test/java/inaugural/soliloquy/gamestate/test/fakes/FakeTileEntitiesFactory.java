package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileEntity;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;

public class FakeTileEntitiesFactory implements TileEntitiesFactory {
    @Override
    public <TEntity extends TileEntity> TileEntities<TEntity> make(Tile tile, TEntity tEntity)
            throws IllegalArgumentException {
        return new FakeTileEntities<>(tile);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
