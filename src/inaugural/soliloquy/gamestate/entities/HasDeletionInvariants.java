package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.ArrayList;
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
        @SuppressWarnings("rawtypes") ArrayList<Class> classes = new ArrayList<>() {{
            add(HasDeletionInvariants.class);
            add(Thread.class);
            add(Check.class);
        }};
        throw exceptionFactory.apply(Check.getFirstStackTraceElementNotInClasses(classes) +
                ": " + exceptionMessage);
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
