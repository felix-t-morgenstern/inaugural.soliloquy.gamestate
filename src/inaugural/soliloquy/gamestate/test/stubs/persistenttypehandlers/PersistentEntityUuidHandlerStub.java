package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.EntityUuidStub;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class PersistentEntityUuidHandlerStub extends PersistentValueTypeHandlerStub<EntityUuid> {
    @Override
    public String typeName() {
        return "EntityUuid";
    }

    @Override
    protected EntityUuid generateInstance() {
        return new EntityUuidStub();
    }
}
