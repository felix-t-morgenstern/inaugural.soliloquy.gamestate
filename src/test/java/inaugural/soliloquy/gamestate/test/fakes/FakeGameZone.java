package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FakeGameZone implements GameZone {
    public int _maxX = 99;
    public int _maxY = 99;

    public Coordinate FAKE_MAX_COORDINATES = null;

    public String ID = "GameZoneStubId";
    public Tile[][] TILES = new Tile[_maxX + 1][_maxY + 1];
    public boolean RETURN_ACTUAL_TILE_AT_LOCATION = false;

    @SuppressWarnings("rawtypes")
    private final List<Action> ON_ENTRY = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    private final List<Action> ON_EXIT = new ArrayList<>();

    private final boolean THROW_EXCEPTION_ON_GET_MAX_COORDINATES;

    private boolean _isDeleted;
    public String _customId;
    private String _name;

    public FakeGameZone() {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
        for (int x = 0; x <= _maxX; x++) {
            for (int y = 0; y <= _maxY; y++) {
                TILES[x][y] = new FakeTile(this, x, y);
            }
        }
    }


    public FakeGameZone(String customId) {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
        _customId = customId;
    }

    @Override
    public String type() {
        return null;
    }

    @SuppressWarnings("ReplaceNullCheck")
    @Override
    public Coordinate maxCoordinates() {
        if (THROW_EXCEPTION_ON_GET_MAX_COORDINATES) {
            throw new GameZoneStubException();
        }
        if (FAKE_MAX_COORDINATES != null) {
            return FAKE_MAX_COORDINATES;
        }
        return Coordinate.of(TILES.length - 1, TILES[0].length - 1);
    }

    @Override
    public Tile tile(Coordinate coordinate) throws IllegalArgumentException {
        if (RETURN_ACTUAL_TILE_AT_LOCATION) {
            return TILES[coordinate.x()][coordinate.y()];
        }
        else {
            return new FakeTile(coordinate.x(), coordinate.y(), null);
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
    public Map<UUID, Character> charactersRepresentation() {
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
        return null;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class GameZoneStubException extends RuntimeException {
    }
}
