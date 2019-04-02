package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.ICoordinateFactory;

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
