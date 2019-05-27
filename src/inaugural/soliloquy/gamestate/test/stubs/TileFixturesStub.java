package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtures;

import java.util.ArrayList;
import java.util.List;

public class TileFixturesStub implements ITileFixtures {
    private final ITile TILE;

    public final List<ITileFixture> FIXTURES = new ArrayList<>();

    public TileFixturesStub(ITile tile) {
        TILE = tile;
    }

    @Override
    public IMap<ITileFixture, Integer> getRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void add(ITileFixture tileFixture) throws IllegalArgumentException {
        FIXTURES.add(tileFixture);
        tileFixture.assignTileFixtureToTile(TILE);
    }

    @Override
    public void add(ITileFixture iTileFixture, int i) throws IllegalArgumentException {
    }

    @Override
    public boolean remove(ITileFixture iTileFixture) {
        return false;
    }

    @Override
    public boolean contains(ITileFixture tileFixture) throws IllegalArgumentException {
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