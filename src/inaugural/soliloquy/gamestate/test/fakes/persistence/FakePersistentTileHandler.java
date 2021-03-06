package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import soliloquy.specs.gamestate.entities.Tile;

public class FakePersistentTileHandler extends FakePersistentValueTypeHandler<Tile> {
    @Override
    public String typeName() {
        return "Tile";
    }

    @Override
    protected Tile generateInstance() {
        return new FakeTile();
    }
}
