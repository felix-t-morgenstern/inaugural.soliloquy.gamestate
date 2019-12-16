package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.TileFixtureStub;
import soliloquy.specs.gamestate.entities.TileFixture;

public class PersistentTileFixtureHandlerStub extends PersistentValueTypeHandlerStub<TileFixture> {
    @Override
    public String typeName() {
        return "TileFixture";
    }

    @Override
    protected TileFixture generateInstance() {
        return new TileFixtureStub();
    }
}
