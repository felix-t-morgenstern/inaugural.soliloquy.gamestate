package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

abstract class HasDeletionInvariants implements Deletable {
    private boolean _isDeleted;

    protected abstract String className();
    protected abstract String containingClassName();
    abstract Deletable getContainingObject();

    void enforceDeletionInvariants(String methodName) {
        if (_isDeleted) {
            throw new EntityDeletedException(className() + "." + methodName +
                    ": object is deleted");
        }
        if (containingClassName() != null && getContainingObject() != null &&
                getContainingObject().isDeleted()) {
            throw new IllegalStateException(className() + "." + methodName + ": containing " +
                    containingClassName() + " is deleted");
        }
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public void delete() {
        _isDeleted = true;
        afterDeleted();
    }

    protected void afterDeleted() {

    }
}
