package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.EntityUuidStub;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class PersistentEntityUuidHandlerStub implements PersistentValueTypeHandler<EntityUuid> {
    public String readValue;
    public static final EntityUuid READ_RESULT = new EntityUuidStub();

    public EntityUuid readGenericParamsSet;
    public static final String WRITE_RESULT = "PersistentEntityUuidHandlerStub write result";

    @Override
    public EntityUuid read(String data) throws IllegalArgumentException {
        readValue = data;
        return READ_RESULT;
    }

    @Override
    public String write(EntityUuid input) {
        readGenericParamsSet = input;
        return WRITE_RESULT;
    }

    @Override
    public EntityUuid getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
