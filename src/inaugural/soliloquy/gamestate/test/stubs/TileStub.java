package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

public class TileStub implements Tile {
    private GameZone _gameZone;

    private final TileEntities<Character> CHARACTERS = new TileEntitiesStub<>(this);
    private final TileEntities<TileFixture> FIXTURES = new TileEntitiesStub<>(this);
    private final TileEntities<Item> TILE_ITEMS = new TileEntitiesStub<>(this);
    private final TileWallSegments TILE_WALL_SEGMENTS = new TileWallSegmentsStub(this);
    private final Collection<GameMovementEvent> MOVEMENT_EVENTS = new CollectionStub<>();
    private final Collection<GameAbilityEvent> ABILITY_EVENTS = new CollectionStub<>();

    private final Map<Sprite, Integer> SPRITES = new MapStub<>();

    private int _height;
    private GroundType _groundType;
    private boolean _isDeleted;

    private ReadableCoordinate _tileLocation;
    private VariableCache _data;

    public TileStub() {
        _gameZone = new GameZoneStub();
    }

    public TileStub(ReadableCoordinate tileLocation) {
        _tileLocation = tileLocation;
        _gameZone = new GameZoneStub();
    }

    public TileStub(int x, int y, VariableCache data) {
        _tileLocation = new CoordinateStub(x, y);
        _data = data;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
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
    public GameZone gameZone() throws IllegalStateException {
        return _gameZone;
    }

    @Override
    public ReadableCoordinate location() throws IllegalStateException {
        return _tileLocation;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        return _height;
    }

    @Override
    public void setHeight(int height) throws IllegalStateException {
        _height = height;
    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        return _groundType;
    }

    @Override
    public void setGroundType(GroundType groundType) throws IllegalStateException {
        _groundType = groundType;
    }

    @Override
    public TileEntities<Character> characters() {
        return CHARACTERS;
    }

    @Override
    public TileEntities<Item> items() {
        return TILE_ITEMS;
    }

    @Override
    public TileEntities<TileFixture> fixtures() throws IllegalStateException {
        return FIXTURES;
    }

    @Override
    public TileWallSegments wallSegments() throws IllegalStateException {
        return TILE_WALL_SEGMENTS;
    }

    @Override
    public Map<Sprite, Integer> sprites() throws IllegalStateException {
        return SPRITES;
    }

    @Override
    public void assignGameZoneAfterAddedToGameZone(GameZone gameZone) throws IllegalArgumentException, IllegalStateException {
        _gameZone = gameZone;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public Collection<GameMovementEvent> movementEvents() throws IllegalStateException {
        return MOVEMENT_EVENTS;
    }

    @Override
    public Collection<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        return ABILITY_EVENTS;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        return null;
    }
}
