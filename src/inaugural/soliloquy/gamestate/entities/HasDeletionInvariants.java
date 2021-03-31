package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.function.Function;

public abstract class HasDeletionInvariants implements Deletable {
    private boolean _isDeleted;

    protected abstract String containingClassName();
    protected abstract Deletable getContainingObject();

    protected void enforceDeletionInvariants() {
        if (_isDeleted) {
            throwException("object is deleted", EntityDeletedException::new);
        }
        if (containingClassName() != null && getContainingObject() != null &&
                getContainingObject().isDeleted()) {
            throwException("containing " + containingClassName() + " is deleted",
                    IllegalStateException::new);
        }
    }

    private static void throwException(String exceptionMessage,
                                       Function<String, RuntimeException> exceptionFactory) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement callingMethod = null;
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!stackTraceElement.getClassName().equals(HasDeletionInvariants.class.getName()) &&
                    !stackTraceElement.getClassName().equals(Thread.class.getName())) {
                callingMethod = stackTraceElement;
                break;
            }
        }
        assert callingMethod != null;
        String className = callingMethod.getClassName();
        String methodName = callingMethod.getMethodName();
        throw exceptionFactory.apply(className +
                (!methodName.equals("<init>") ? "." + methodName : "") + ": " + exceptionMessage);
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
