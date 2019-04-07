package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IPair;
import soliloquy.gamestate.specs.ITile;
import soliloquy.ruleset.gameconcepts.specs.ITileVisibility;

public class TileVisibilityStub implements ITileVisibility {
    public ICollection<IPair<ICoordinate, ICoordinate>> _tilesChecked;

    public TileVisibilityStub() {
        _tilesChecked = new CollectionStub<>();
    }

    @Override
    public boolean canSeeTile(ITile origin, ITile target) throws IllegalArgumentException {
        _tilesChecked.add(new PairStub<>(origin.getLocation(),target.getLocation()));
        return true;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
