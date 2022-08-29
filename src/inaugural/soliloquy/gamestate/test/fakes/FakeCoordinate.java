package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.valueobjects.Coordinate;

public class FakeCoordinate implements Coordinate {
    private int _x;
    private int _y;

    public FakeCoordinate(int x, int y) {
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
    public Coordinate makeClone() {
        return new FakeCoordinate(_x, _y);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public int compareTo(Coordinate coordinate) {
        return 0;
    }
}
