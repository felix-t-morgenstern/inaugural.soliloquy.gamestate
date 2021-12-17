package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeTileFixture;
import soliloquy.specs.gamestate.entities.TileFixture;

public class FakePersistentTileFixtureHandler extends FakeTypeHandler<TileFixture> {
    @Override
    public String typeName() {
        return "TileFixture";
    }

    @Override
    protected TileFixture generateInstance() {
        return new FakeTileFixture();
    }
}
