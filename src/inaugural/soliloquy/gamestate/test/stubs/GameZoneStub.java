package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.logger.Logger;

public class GameZoneStub implements GameZone {
    public static int _maxX = 999;
    public static int _maxY = 999;

    public Tile[][] TILES = new Tile[200][200];
    public boolean RETURN_ACTUAL_TILE_AT_LOCATION = false;

    private final boolean THROW_EXCEPTION_ON_GET_MAX_COORDINATES;

    private boolean _isDeleted;

    public GameZoneStub() {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = false;
    }

    public GameZoneStub(boolean throwExceptionOnGetMaxCoordinates) {
        THROW_EXCEPTION_ON_GET_MAX_COORDINATES = throwExceptionOnGetMaxCoordinates;
    }

    @Override
    public String zoneType() {
        return null;
    }

    @Override
    public Coordinate getMaxCoordinates() {
        if (THROW_EXCEPTION_ON_GET_MAX_COORDINATES) {
            throw new GameZoneStubException(this);
        }
        return new CoordinateStub(_maxX,_maxY);
    }

    @Override
    public void setDimensions(Coordinate iCoordinate) throws IllegalArgumentException {

    }

    @Override
    public Tile tile(ReadableCoordinate tileLocation) throws IllegalArgumentException {
        if (RETURN_ACTUAL_TILE_AT_LOCATION) {
            return TILES[tileLocation.getX()][tileLocation.getY()];
        } else {
            return new TileStub(tileLocation, this);
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
    public Game game() {
        return null;
    }

    @Override
    public Logger logger() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
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
    public void delete() throws IllegalStateException {
        _isDeleted = true;
        for (Tile[] col : TILES) {
            for (Tile tile : col) {
                if (tile != null) {
                    tile.deleteAfterDeletingContainingGameZone();
                }
            }
        }
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    public class GameZoneStubException extends RuntimeException {
        public final GameZone GAME_ZONE;

        GameZoneStubException(GameZone gameZone) {
            GAME_ZONE = gameZone;
        }
    }
}
