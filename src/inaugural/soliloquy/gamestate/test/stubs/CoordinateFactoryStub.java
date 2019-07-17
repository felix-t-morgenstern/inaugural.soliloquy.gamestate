package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.valueobjects.Coordinate;

public class CoordinateFactoryStub implements CoordinateFactory {
    @Override
    public Coordinate make(int x, int y) {
        return new CoordinateStub(x,y);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
