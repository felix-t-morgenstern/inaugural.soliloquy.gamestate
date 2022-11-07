package inaugural.soliloquy.gamestate.test.spydoubles;

import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import java.util.ArrayList;
import java.util.List;

public class TileVisibilitySpyDouble implements TileVisibility {
    public List<Pair<Coordinate, Coordinate>> _tilesChecked;

    public TileVisibilitySpyDouble() {
        _tilesChecked = new ArrayList<>();
    }

    @Override
    public boolean canSeeTile(Tile origin, Tile target) throws IllegalArgumentException {
        _tilesChecked.add(new Pair<>(origin.location(), target.location()));
        return true;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
