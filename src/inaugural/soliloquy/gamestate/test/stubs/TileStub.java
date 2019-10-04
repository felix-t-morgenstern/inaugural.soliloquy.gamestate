package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEvent;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

public class TileStub implements Tile {
    private final GameZone GAME_ZONE;

    private final TileCharacters CHARACTERS = new TileCharactersStub(this);
    private final TileFixtures FIXTURES = new TileFixturesStub(this);
    private final TileWallSegments TILE_WALL_SEGMENTS = new TileWallSegmentsStub(this);

    private boolean _isDeleted;

    private Coordinate _tileLocation;

    public TileStub() {
        GAME_ZONE = new GameZoneStub();
    }

    public TileStub(Coordinate tileLocation) {
        _tileLocation = tileLocation;
        GAME_ZONE = new GameZoneStub();
    }

    public TileStub(Coordinate tileLocation, GameZone gameZone)
    {
        _tileLocation = tileLocation;
        GAME_ZONE = gameZone;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
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
        return GAME_ZONE;
    }

    @Override
    public Coordinate getLocation() throws IllegalStateException {
        return _tileLocation;
    }

    @Override
    public void setLocation(Coordinate coordinate) throws IllegalArgumentException, IllegalStateException {

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
        return null;
    }

    @Override
    public TileFixtures fixtures() throws IllegalStateException {
        return FIXTURES;
    }

    @Override
    public TileWallSegments tileWallSegments() throws IllegalStateException {
        return TILE_WALL_SEGMENTS;
    }

    @Override
    public Map<Integer, Collection<Sprite>> sprites() throws IllegalStateException {
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
    public String getInterfaceName() {
        return null;
    }

    @Override
    public Collection<GameEvent> events() throws IllegalStateException {
        return null;
    }
}
