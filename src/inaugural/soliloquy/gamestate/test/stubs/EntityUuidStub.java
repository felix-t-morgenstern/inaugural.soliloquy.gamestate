package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.IEntityUuid;

public class EntityUuidStub implements IEntityUuid {
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
