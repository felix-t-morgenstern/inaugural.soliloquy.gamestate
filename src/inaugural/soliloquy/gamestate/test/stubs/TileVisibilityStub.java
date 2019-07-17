package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

public class TileVisibilityStub implements TileVisibility {
    public Collection<Pair<Coordinate, Coordinate>> _tilesChecked;

    public TileVisibilityStub() {
        _tilesChecked = new CollectionStub<>();
    }

    @Override
    public boolean canSeeTile(Tile origin, Tile target) throws IllegalArgumentException {
        _tilesChecked.add(new PairStub<>(origin.getLocation(),target.getLocation()));
        return true;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
