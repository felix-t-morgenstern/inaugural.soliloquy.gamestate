package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.FixtureType;

public class FakeTileFixture implements TileFixture {
    private boolean _isDeleted;
    private float _xTileWidthOffset;
    private float _yTileHeightOffset;
    private EntityUuid _id;
    private FixtureType _fixtureType;
    private VariableCache _data;
    private String _name;

    private final TileFixtureItems TILE_FIXTURE_ITEMS;

    public Tile _tile;

    public FakeTileFixture() {
        TILE_FIXTURE_ITEMS = new FakeTileFixtureItems(this);
    }

    public FakeTileFixture(EntityUuid id, FixtureType fixtureType, VariableCache data) {
        TILE_FIXTURE_ITEMS = new FakeTileFixtureItems(this);
        _id = id;
        _fixtureType = fixtureType;
        _data = data;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        return _tile;
    }

    @Override
    public FixtureType type() throws IllegalStateException {
        return _fixtureType;
    }

    @Override
    public TileFixtureItems items() throws IllegalStateException {
        return TILE_FIXTURE_ITEMS;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile) throws
            IllegalArgumentException, IllegalStateException {
        _tile = tile;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public void setName(String name) {
        _name = name;
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
        return _id;
    }

    @Override
    public float getXTileWidthOffset() throws IllegalStateException, EntityDeletedException {
        return _xTileWidthOffset;
    }

    @Override
    public float getYTileHeightOffset() throws IllegalStateException, EntityDeletedException {
        return _yTileHeightOffset;
    }

    @Override
    public void setXTileWidthOffset(float v) throws IllegalStateException, EntityDeletedException {
        _xTileWidthOffset = v;
    }

    @Override
    public void setYTileHeightOffset(float v) throws IllegalStateException, EntityDeletedException {
        _yTileHeightOffset = v;
    }
}
