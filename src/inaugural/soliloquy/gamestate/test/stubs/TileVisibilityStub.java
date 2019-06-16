package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.ICoordinate;
import soliloquy.specs.common.valueobjects.IPair;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.ruleset.gameconcepts.ITileVisibility;

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
