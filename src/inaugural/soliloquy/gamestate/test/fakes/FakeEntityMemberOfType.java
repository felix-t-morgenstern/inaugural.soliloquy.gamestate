package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.EntityMemberOfType;

public class FakeEntityMemberOfType implements EntityMemberOfType<HasId> {
    private final Deletable CONTAINING_ENTITY;
    private final HasId TYPE;

    private boolean _isDeleted;

    public FakeEntityMemberOfType(Deletable containingEntity, HasId type) {
        CONTAINING_ENTITY = containingEntity;
        TYPE = type;
    }

    @Override
    public HasId type() {
        return TYPE;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getInterfaceName() {
        return EntityMemberOfType.class.getCanonicalName() + "<" +
                HasId.class.getCanonicalName() + ">";
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }
}
