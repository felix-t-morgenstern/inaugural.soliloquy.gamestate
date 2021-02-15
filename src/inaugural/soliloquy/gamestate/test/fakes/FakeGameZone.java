package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

public class FakeGameZone implements GameZone {
    public int _maxX = 99;
    public int _maxY = 99;

    public Coordinate FAKE_MAX_COORDINATES = null;

    public String ID = "GameZoneStubId";
    public Tile[][] TILES = new Tile[_maxX+1][_maxY+1];
    public boolean RETURN_ACTUAL_TILE_AT_LOCATION = false;

    @SuppressWarnings("rawtypes")
    private final List<Action> ON_ENTRY = new FakeList<>();
    @SuppressWarnings("rawtypes")
    private final List<Action> ON_EXIT = new FakeList<>();

    private final boolean THROW_EXCEPTION_ON_GET_MAX_COORDINATES;

    private boolean _isDeleted;
    public String _customId;
    private String _type;
    private VariableCache _data;
    private String _name;

    public FakeGameZone() {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
        for(int x = 0; x <= _maxX; x++) {
            for(int y = 0; y <= _maxY; y++) {
                TILES[x][y] = new FakeTile(this, x, y);
            }
        }
    }

    public FakeGameZone(boolean throwExceptionOnGetMaxCoordinates) {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = throwExceptionOnGetMaxCoordinates;
    }

    public FakeGameZone(String customId) {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
        _customId = customId;
    }

    public FakeGameZone(String id, String type, Tile[][] tiles, VariableCache data) {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
        RETURN_ACTUAL_TILE_AT_LOCATION = true;
        _customId = id;
        _type = type;
        TILES = tiles;
        _data = data;
    }

    @Override
    public String type() {
        return _type;
    }

    @SuppressWarnings("ReplaceNullCheck")
    @Override
    public Coordinate maxCoordinates() {
        if (THROW_EXCEPTION_ON_GET_MAX_COORDINATES) {
            throw new GameZoneStubException(this);
        }
        if (FAKE_MAX_COORDINATES != null) {
            return FAKE_MAX_COORDINATES;
        }
        return new FakeCoordinate(TILES.length-1,TILES[0].length-1);
    }

    @Override
    public Tile tile(int x, int y) throws IllegalArgumentException {
        if (RETURN_ACTUAL_TILE_AT_LOCATION) {
            return TILES[x][y];
        } else {
            return new FakeTile(x, y, null);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Action> onEntry() {
        return ON_ENTRY;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Action> onExit() {
        return ON_EXIT;
    }

    @Override
    public List<Character> charactersRepresentation() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return _customId == null ? ID : _customId;
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
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
        for (Tile[] row : TILES) {
            for (Tile tile : row) {
                if (tile != null) {
                    tile.delete();
                }
            }
        }
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class GameZoneStubException extends RuntimeException {
        final GameZone GAME_ZONE;

        GameZoneStubException(GameZone gameZone) {
            GAME_ZONE = gameZone;
        }
    }
}
