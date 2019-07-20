package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtures;

import java.util.ArrayList;
import java.util.List;

public class TileFixturesStub implements TileFixtures {
    private final Tile TILE;

    public final List<TileFixture> FIXTURES = new ArrayList<>();

    TileFixturesStub(Tile tile) {
        TILE = tile;
    }

    @Override
    public ReadableMap<TileFixture, Integer> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void add(TileFixture tileFixture) throws IllegalArgumentException {
        FIXTURES.add(tileFixture);
        tileFixture.assignTileFixtureToTile(TILE);
    }

    @Override
    public void add(TileFixture iTileFixture, int i) throws IllegalArgumentException {
    }

    @Override
    public boolean remove(TileFixture iTileFixture) {
        return false;
    }

    @Override
    public boolean contains(TileFixture tileFixture) throws IllegalArgumentException {
        return FIXTURES.contains(tileFixture);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }
}
