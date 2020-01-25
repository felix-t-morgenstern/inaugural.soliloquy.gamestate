package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

public class GameZoneStub implements GameZone {
    public static int _maxX = 999;
    public static int _maxY = 999;

    public static String ID = "GameZoneStubId";
    public Tile[][] TILES = new Tile[_maxX][_maxY];
    public boolean RETURN_ACTUAL_TILE_AT_LOCATION = false;

    private final boolean THROW_EXCEPTION_ON_GET_MAX_COORDINATES;

    private boolean _isDeleted;
    private String _customId;

    public GameZoneStub() {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
    }

    public GameZoneStub(boolean throwExceptionOnGetMaxCoordinates) {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = throwExceptionOnGetMaxCoordinates;
    }

    public GameZoneStub(String customId) {
        this();
        _customId = customId;
    }

    @Override
    public String type() {
        return null;
    }

    @Override
    public Coordinate maxCoordinates() {
        if (THROW_EXCEPTION_ON_GET_MAX_COORDINATES) {
            throw new GameZoneStubException(this);
        }
        return new CoordinateStub(_maxX,_maxY);
    }

    @Override
    public Tile tile(int x, int y) throws IllegalArgumentException {
        if (RETURN_ACTUAL_TILE_AT_LOCATION) {
            return TILES[x][y];
        } else {
            return new TileStub(x, y, null);
        }
    }

    @Override
    public Collection<Action<Void>> onEntry() {
        return null;
    }

    @Override
    public Collection<Action<Void>> onExit() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return _customId == null ? ID : _customId;
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
        return null;
    }

    public class GameZoneStubException extends RuntimeException {
        final GameZone GAME_ZONE;

        GameZoneStubException(GameZone gameZone) {
            GAME_ZONE = gameZone;
        }
    }
}
