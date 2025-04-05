package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.TileImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileFactory;

public class TileFactoryImpl implements TileFactory {
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY;

    public TileFactoryImpl(TileEntitiesFactory tileEntitiesFactory) {
        TILE_ENTITIES_FACTORY = Check.ifNull(tileEntitiesFactory, "tileEntitiesFactory");
    }

    @Override
    public Tile make(VariableCache data) throws IllegalArgumentException {
        return new TileImpl(TILE_ENTITIES_FACTORY, Check.ifNull(data, "data"));
    }

    @Override
    public String getInterfaceName() {
        return TileFactory.class.getCanonicalName();
    }
}
