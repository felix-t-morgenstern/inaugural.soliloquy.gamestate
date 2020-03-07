package inaugural.soliloquy.gamestate.test.stubs;

import inaugural.soliloquy.gamestate.test.fakes.FakeEntityUuid;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class EntityUuidFactoryStub implements EntityUuidFactory {
    public static final EntityUuid RANDOM_ENTITY_UUID = new FakeEntityUuid();

    @Override
    public EntityUuid createFromLongs(long l, long l1) {
        return null;
    }

    @Override
    public EntityUuid createFromString(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public EntityUuid createRandomEntityUuid() {
        return RANDOM_ENTITY_UUID;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
