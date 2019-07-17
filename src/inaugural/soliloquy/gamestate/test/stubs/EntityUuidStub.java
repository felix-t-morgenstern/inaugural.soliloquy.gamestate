package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.EntityUuid;

public class EntityUuidStub implements EntityUuid {
    @Override
    public long getMostSignificantBits() {
        return 0;
    }

    @Override
    public long getLeastSignificantBits() {
        return 0;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
