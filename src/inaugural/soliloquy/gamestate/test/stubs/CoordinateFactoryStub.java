package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.ICoordinateFactory;
import soliloquy.specs.common.valueobjects.ICoordinate;

public class CoordinateFactoryStub implements ICoordinateFactory {
    @Override
    public ICoordinate make(int x, int y) {
        return new CoordinateStub(x,y);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
