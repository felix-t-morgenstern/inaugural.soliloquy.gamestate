package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtures;

import java.util.ArrayList;
import java.util.List;

public class TileFixturesStub implements TileFixtures {
    public final Tile TILE;

    public final static List<TileFixture> FIXTURES = new ArrayList<>();

    private boolean _isDeleted;

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
        tileFixture.assignTileFixtureToTileAfterAddingToTileFixtures(TILE);
    }

    @Override
    public void add(TileFixture iTileFixture, int i) throws IllegalArgumentException {
    }

    @Override
    public boolean remove(TileFixture tileFixture) {
        return FIXTURES.remove(tileFixture);
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
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }
}
