package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static org.mockito.Mockito.mock;

public class FakeGameZone implements GameZone {
    public int _maxX = 99;
    public int _maxY = 99;

    public Coordinate2d FAKE_MAX_Coordinate2dS = null;

    public String ID = "GameZoneStubId";
    public Tile[][] TILES = new Tile[_maxX + 1][_maxY + 1];
    public boolean RETURN_ACTUAL_TILE_AT_LOCATION = false;

    @SuppressWarnings("rawtypes")
    private final List<Action> ON_ENTRY = listOf();
    @SuppressWarnings("rawtypes")
    private final List<Action> ON_EXIT = listOf();

    private final boolean THROW_EXCEPTION_ON_GET_MAX_Coordinate2dS;

    private boolean _isDeleted;
    public String _customId;
    private String _name;

    public FakeGameZone() {
        THROW_EXCEPTION_ON_GET_MAX_Coordinate2dS = false;
        for (int x = 0; x <= _maxX; x++) {
            for (int y = 0; y <= _maxY; y++) {
                TILES[x][y] = mock(Tile.class);
            }
        }
    }


    public FakeGameZone(String customId) {
        THROW_EXCEPTION_ON_GET_MAX_Coordinate2dS = false;
        _customId = customId;
    }

    @Override
    public String type() {
        return null;
    }

    @SuppressWarnings("ReplaceNullCheck")
    @Override
    public Coordinate2d maxCoordinates() {
        if (THROW_EXCEPTION_ON_GET_MAX_Coordinate2dS) {
            throw new GameZoneStubException();
        }
        if (FAKE_MAX_Coordinate2dS != null) {
            return FAKE_MAX_Coordinate2dS;
        }
        return Coordinate2d.of(TILES.length - 1, TILES[0].length - 1);
    }

    @Override
    public Tile tile(Coordinate2d Coordinate2d) throws IllegalArgumentException {
        if (RETURN_ACTUAL_TILE_AT_LOCATION) {
            return TILES[Coordinate2d.X][Coordinate2d.Y];
        }
        else {
            return mock(Tile.class);
        }
    }

    @Override
    public Map<WallSegmentDirection, Map<Coordinate3d, WallSegment>> getSegments(
            Coordinate2d coordinate2d) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void setSegment(Coordinate3d coordinate3d, WallSegment wallSegment)
            throws IllegalArgumentException {

    }

    @Override
    public boolean removeSegment(Coordinate3d coordinate3d,
                                 WallSegmentDirection wallSegmentDirection)
            throws IllegalArgumentException {
        return false;
    }

    @Override
    public void removeAllSegments(Coordinate2d coordinate2d,
                                  WallSegmentDirection wallSegmentDirection)
            throws IllegalArgumentException {

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
