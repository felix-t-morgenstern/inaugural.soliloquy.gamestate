package inaugural.soliloquy.gamestate.test.fakes.persistence;

import inaugural.soliloquy.gamestate.test.fakes.FakeEntityUuid;
import soliloquy.specs.common.valueobjects.EntityUuid;

public class FakePersistentEntityUuidHandler extends FakePersistentValueTypeHandler<EntityUuid> {
    @Override
    public String typeName() {
        return "EntityUuid";
    }

    @Override
    protected EntityUuid generateInstance() {
        return new FakeEntityUuid();
    }
}
