package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.ITile;
import soliloquy.gamestate.specs.ITileFixture;
import soliloquy.gamestate.specs.ITileFixtures;

public class TileFixtures implements ITileFixtures {
    private final ITile TILE;
    private final IMapFactory MAP_FACTORY;

    public TileFixtures(ITile tile, IMapFactory mapFactory) {
        TILE = tile;
        MAP_FACTORY = mapFactory;
    }

    @Override
    public IMap<ITileFixture, Integer> getTileFixturesRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void addTileFixture(ITileFixture iTileFixture) throws IllegalArgumentException {

    }

    @Override
    public void addTileFixture(ITileFixture iTileFixture, int i) throws IllegalArgumentException {

    }

    @Override
    public boolean removeTileFixture(ITileFixture iTileFixture) {
        return false;
    }

    @Override
    public boolean containsTileFixture(ITileFixture iTileFixture) throws IllegalArgumentException {
        return false;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
