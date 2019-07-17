package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Deletable;

abstract class HasDeletionInvariants implements Deletable {
    protected boolean _isDeleted;

    protected abstract String className();
    protected abstract String containingClassName();
    protected abstract boolean containingObjectIsDeleted();

    void enforceDeletionInvariants(String methodName) {
        if (_isDeleted) {
            throw new IllegalStateException(className() + "." + methodName +
                    ": object is deleted");
        }
        if (containingClassName() != null && containingObjectIsDeleted()) {
            throw new IllegalStateException(className() + "." + methodName + ": containing " +
                    containingClassName() + " is deleted");
        }
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }
}
