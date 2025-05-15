package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class FakeTileFixture implements TileFixture {
    private boolean _isDeleted;
    private float _xTileWidthOffset;
    private float _yTileHeightOffset;
    private String _name;

    private final TileFixtureItems TILE_FIXTURE_ITEMS;

    public Tile _tile;

    public FakeTileFixture() {
        TILE_FIXTURE_ITEMS = new FakeTileFixtureItems(this);
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
    public TileFixtureItems items() throws IllegalStateException {
        return TILE_FIXTURE_ITEMS;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile) throws
            IllegalArgumentException, IllegalStateException {
        _tile = tile;
    }

    @Override
    public Map<String, Object> data() throws IllegalStateException {
        return null;
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
    public List<GameMovementEvent> movementEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public List<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        return null;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        return null;
    }

    @Override
    public UUID uuid() {
        return null;
    }

    @Override
    public Vertex getTileOffset() throws IllegalStateException, EntityDeletedException {
        return vertexOf(_xTileWidthOffset, _yTileHeightOffset);
    }

    @Override
    public void setTileOffset(Vertex vertex)
            throws IllegalArgumentException, IllegalStateException, EntityDeletedException {
        _xTileWidthOffset = vertex.X;
        _yTileHeightOffset = vertex.Y;
    }
}
