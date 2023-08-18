package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.List;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public abstract class HasDeletionInvariants implements Deletable {
    private boolean isDeleted;

    protected abstract String containingClassName();

    protected abstract Deletable getContainingObject();

    protected void enforceDeletionInvariants() {
        if (isDeleted) {
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
        @SuppressWarnings("rawtypes") List<Class> classes =
                listOf(HasDeletionInvariants.class, Thread.class, Check.class);
        throw exceptionFactory.apply(Check.getFirstStackTraceElementNotInClasses(classes) +
                ": " + exceptionMessage);
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public void delete() {
        isDeleted = true;
        afterDeleted();
    }

    protected void afterDeleted() {

    }
}
