package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.factories.IEntityUuidFactory;
import soliloquy.specs.common.valueobjects.IEntityUuid;

public class EntityUuidFactoryStub implements IEntityUuidFactory {
    public static final IEntityUuid RANDOM_ENTITY_UUID = new EntityUuidStub();

    @Override
    public IEntityUuid createFromLongs(long l, long l1) {
        return null;
    }

    @Override
    public IEntityUuid createFromString(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public IEntityUuid createRandomEntityUuid() {
        return RANDOM_ENTITY_UUID;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
