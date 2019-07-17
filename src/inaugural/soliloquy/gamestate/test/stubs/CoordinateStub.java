package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.Coordinate;

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
    public int compareTo(Coordinate o) {
        return 0;
    }

    @Override
    public Coordinate makeClone() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
