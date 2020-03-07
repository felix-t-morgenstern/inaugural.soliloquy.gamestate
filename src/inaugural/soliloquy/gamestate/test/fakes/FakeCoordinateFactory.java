package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.valueobjects.Coordinate;

public class FakeCoordinateFactory implements CoordinateFactory {
    @Override
    public Coordinate make(int x, int y) {
        return new FakeCoordinate(x,y);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
