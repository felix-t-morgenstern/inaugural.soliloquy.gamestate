package inaugural.soliloquy.gamestate.test.spydoubles;

import inaugural.soliloquy.gamestate.test.fakes.FakeCollection;
import inaugural.soliloquy.gamestate.test.fakes.FakePair;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

public class TileVisibilitySpyDouble implements TileVisibility {
    public Collection<Pair<ReadableCoordinate, ReadableCoordinate>> _tilesChecked;

    public TileVisibilitySpyDouble() {
        _tilesChecked = new FakeCollection<>();
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
