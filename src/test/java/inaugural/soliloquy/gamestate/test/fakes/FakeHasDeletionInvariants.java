package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.entities.HasDeletionInvariants;
import soliloquy.specs.gamestate.entities.Deletable;

public class FakeHasDeletionInvariants extends HasDeletionInvariants {
    public FakeDeletable ContainingObject;
    public String ContainingClassName;

    @Override
    protected String containingClassName() {
        return ContainingClassName;
    }

    @Override
    protected Deletable getContainingObject() {
        return ContainingObject;
    }

    public void callEnforceInvariants() {
        enforceDeletionInvariants();
    }
}
