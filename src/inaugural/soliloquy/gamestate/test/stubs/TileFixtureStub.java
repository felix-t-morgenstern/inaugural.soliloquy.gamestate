package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.FixtureType;

public class TileFixtureStub implements TileFixture {
    private boolean _isDeleted;

    private final TileFixtureItems TILE_FIXTURE_ITEMS;

    public Tile _tile;

    public TileFixtureStub() {
        TILE_FIXTURE_ITEMS = new TileFixtureItemsStub(this);
    }

    @Override
    public Tile tile() throws IllegalStateException {
        return _tile;
    }

    @Override
    public FixtureType type() throws IllegalStateException {
        return null;
    }

    @Override
    public Coordinate pixelOffset() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixtureItems items() throws IllegalStateException {
        return TILE_FIXTURE_ITEMS;
    }

    @Override
    public void assignTileFixtureToTileAfterAddingToTileFixtures(Tile tile) throws
            IllegalArgumentException, IllegalStateException {
        _tile = tile;
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
    public Collection<GameMovementEvent> movementEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public Collection<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        return null;
    }

    @Override
    public EntityUuid id() {
        return null;
    }
}
