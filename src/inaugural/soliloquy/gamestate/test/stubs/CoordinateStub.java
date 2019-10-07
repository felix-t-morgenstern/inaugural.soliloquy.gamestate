package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;

public class CoordinateStub implements Coordinate {
    private int _x;
    private int _y;

    public CoordinateStub() {
    }

    public CoordinateStub(int x, int y) {
        _x = x;
        _y = y;
    }

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public void setX(int x) {
        _x = x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public void setY(int y) {
        _y = y;
    }

    @Override
    public ReadableCoordinate readOnlyRepresentation() {
        return new ReadableCoordinateStub(_x, _y);
    }

    @Override
    public Coordinate makeClone() {
        return new CoordinateStub(_x, _y);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public int compareTo(ReadableCoordinate o) {
        return 0;
    }
}
