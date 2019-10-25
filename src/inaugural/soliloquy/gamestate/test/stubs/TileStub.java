package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

public class TileStub implements Tile {
    private final GameZone GAME_ZONE;

    private final TileCharacters CHARACTERS = new TileCharactersStub(this);
    private final TileFixtures FIXTURES = new TileFixturesStub(this);
    private final TileItems TILE_ITEMS = new TileItemsStub(this);
    private final TileWallSegments TILE_WALL_SEGMENTS = new TileWallSegmentsStub(this);

    private boolean _isDeleted;

    private ReadableCoordinate _tileLocation;

    public TileStub() {
        GAME_ZONE = new GameZoneStub();
    }

    public TileStub(ReadableCoordinate tileLocation) {
        _tileLocation = tileLocation;
        GAME_ZONE = new GameZoneStub();
    }

    public TileStub(ReadableCoordinate tileLocation, GameZone gameZone)
    {
        _tileLocation = tileLocation;
        GAME_ZONE = gameZone;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public void deleteAfterDeletingContainingGameZone() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public GameZone gameZone() throws IllegalStateException {
        return GAME_ZONE;
    }

    @Override
    public ReadableCoordinate location() throws IllegalStateException {
        return _tileLocation;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setHeight(int i) throws IllegalStateException {

    }

    @Override
    public GroundType getGroundType() throws IllegalStateException {
        return null;
    }

    @Override
    public void setGroundType(GroundType iGroundType) throws IllegalStateException {

    }

    @Override
    public TileCharacters characters() {
        return CHARACTERS;
    }

    @Override
    public TileItems items() {
        return TILE_ITEMS;
    }

    @Override
    public TileFixtures fixtures() throws IllegalStateException {
        return FIXTURES;
    }

    @Override
    public TileWallSegments wallSegments() throws IllegalStateException {
        return TILE_WALL_SEGMENTS;
    }

    @Override
    public Map<Integer, Collection<Sprite>> sprites() throws IllegalStateException {
        return null;
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
}
