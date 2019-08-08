package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
import soliloquy.specs.ruleset.entities.FixtureType;

public class TileFixtureStub implements TileFixture {
    private boolean _isDeleted;

    @Override
    public Tile tile() throws IllegalStateException {
        return null;
    }

    @Override
    public FixtureType fixtureType() throws IllegalStateException {
        return null;
    }

    @Override
    public Coordinate pixelOffset() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixtureItems containedItems() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignTileFixtureToTileAfterAddingToTileFixtures(Tile tile) throws
            IllegalArgumentException, IllegalStateException {

    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public Collection<GameEvent> events() throws IllegalStateException {
        return null;
    }
}
