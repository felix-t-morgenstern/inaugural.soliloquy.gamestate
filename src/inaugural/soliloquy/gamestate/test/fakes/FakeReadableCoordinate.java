package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;

public class FakeReadableCoordinate implements ReadableCoordinate {
    private int _x;
    private int _y;

    public FakeReadableCoordinate(int x, int y) {
        _x = x;
        _y = y;
    }

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public int compareTo(ReadableCoordinate o) {
        return 0;
    }

    @Override
    public Coordinate makeClone() {
        return new FakeCoordinate(_x, _y);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
