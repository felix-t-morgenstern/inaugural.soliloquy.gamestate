package inaugural.soliloquy.gamestate;

import soliloquy.gamestate.specs.IDeletable;

abstract class HasDeletionInvariants implements IDeletable {
    protected boolean _isDeleted;

    protected abstract String className();
    protected abstract String containingClassName();
    protected abstract boolean containingObjectIsDeleted();

    void enforceDeletionInvariants(String methodName) {
        if (isDeleted()) {
            throw new IllegalStateException(className() + "." + methodName +
                    ": object is deleted");
        }
        if (containingClassName() != null && containingObjectIsDeleted()) {
            throw new IllegalStateException(className() + "." + methodName + ": containing " +
                    containingClassName() + " is deleted");
        }
    }
}
