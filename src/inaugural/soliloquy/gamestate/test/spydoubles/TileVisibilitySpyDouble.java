package inaugural.soliloquy.gamestate.test.spydoubles;

import inaugural.soliloquy.gamestate.test.fakes.FakeList;
import inaugural.soliloquy.gamestate.test.fakes.FakePair;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

public class TileVisibilitySpyDouble implements TileVisibility {
    public List<Pair<Coordinate, Coordinate>> _tilesChecked;

    public TileVisibilitySpyDouble() {
        _tilesChecked = new FakeList<>();
    }

    @Override
    public boolean canSeeTile(Tile origin, Tile target) throws IllegalArgumentException {
        _tilesChecked.add(new FakePair<>(origin.location(),target.location()));
        return true;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
