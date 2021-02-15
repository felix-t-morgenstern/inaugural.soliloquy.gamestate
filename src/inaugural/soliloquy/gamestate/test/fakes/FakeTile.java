package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

public class FakeTile implements Tile {
    private GameZone _gameZone;

    private final TileEntities<Character> CHARACTERS = new FakeTileEntities<>(this);
    private final TileEntities<TileFixture> FIXTURES = new FakeTileEntities<>(this);
    private final TileEntities<Item> TILE_ITEMS = new FakeTileEntities<>(this);
    private final TileWallSegments TILE_WALL_SEGMENTS = new FakeTileWallSegments(this);
    private final List<GameMovementEvent> MOVEMENT_EVENTS = new FakeList<>();
    private final List<GameAbilityEvent> ABILITY_EVENTS = new FakeList<>();

    private final Map<Sprite, Integer> SPRITES = new FakeMap<>();

    private int _height;
    private GroundType _groundType;
    private boolean _isDeleted;

    private Coordinate _tileLocation;
    private VariableCache _data;

    public FakeTile() {
        _gameZone = new FakeGameZone();
    }

    public FakeTile(GameZone gameZone, int x, int y) {
        _gameZone = gameZone;
        _tileLocation = new FakeCoordinate(x, y);
    }

    public FakeTile(Coordinate tileLocation) {
        _tileLocation = tileLocation;
        _gameZone = new FakeGameZone();
    }

    public FakeTile(int x, int y, VariableCache data) {
        _tileLocation = new FakeCoordinate(x, y);
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
    public Coordinate location() throws IllegalStateException {
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
    public List<GameMovementEvent> movementEvents() throws IllegalStateException {
        return MOVEMENT_EVENTS;
    }

    @Override
    public List<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        return ABILITY_EVENTS;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        return null;
    }
}
