package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.TileStub;
import soliloquy.specs.gamestate.entities.Tile;

public class PersistentTileHandlerStub extends PersistentValueTypeHandlerStub<Tile> {
    @Override
    public String typeName() {
        return "Tile";
    }

    @Override
    protected Tile generateInstance() {
        return new TileStub();
    }
}
