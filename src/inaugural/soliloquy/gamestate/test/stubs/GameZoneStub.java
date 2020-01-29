package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

public class GameZoneStub implements GameZone {
    public int _maxX = 999;
    public int _maxY = 999;

    public ReadableCoordinate FAKE_MAX_COORDINATES = null;

    public String ID = "GameZoneStubId";
    public Tile[][] TILES = new Tile[_maxX][_maxY];
    public boolean RETURN_ACTUAL_TILE_AT_LOCATION = false;

    @SuppressWarnings("rawtypes")
    private final Collection<Action> ON_ENTRY = new CollectionStub<>();
    @SuppressWarnings("rawtypes")
    private final Collection<Action> ON_EXIT = new CollectionStub<>();

    private final boolean THROW_EXCEPTION_ON_GET_MAX_COORDINATES;

    private boolean _isDeleted;
    private String _customId;
    private String _type;
    private VariableCache _data;
    private String _name;

    public GameZoneStub() {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
    }

    public GameZoneStub(boolean throwExceptionOnGetMaxCoordinates) {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = throwExceptionOnGetMaxCoordinates;
    }

    public GameZoneStub(String customId) {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
        _customId = customId;
    }

    public GameZoneStub(String id, String type, Tile[][] tiles, VariableCache data) {
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
    public ReadableCoordinate maxCoordinates() {
        if (THROW_EXCEPTION_ON_GET_MAX_COORDINATES) {
            throw new GameZoneStubException(this);
        }
        if (FAKE_MAX_COORDINATES != null) {
            return FAKE_MAX_COORDINATES;
        }
        return new CoordinateStub(TILES.length-1,TILES[0].length-1);
    }

    @Override
    public Tile tile(int x, int y) throws IllegalArgumentException {
        if (RETURN_ACTUAL_TILE_AT_LOCATION) {
            return TILES[x][y];
        } else {
            return new TileStub(x, y, null);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Action> onEntry() {
        return ON_ENTRY;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Action> onExit() {
        return ON_EXIT;
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
        for (Tile[] col : TILES) {
            for (Tile tile : col) {
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

    public class GameZoneStubException extends RuntimeException {
        final GameZone GAME_ZONE;

        GameZoneStubException(GameZone gameZone) {
            GAME_ZONE = gameZone;
        }
    }
}
