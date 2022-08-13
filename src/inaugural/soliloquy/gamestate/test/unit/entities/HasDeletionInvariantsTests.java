package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.test.fakes.FakeDeletable;
import inaugural.soliloquy.gamestate.test.fakes.FakeHasDeletionInvariants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class HasDeletionInvariantsTests {
    private final FakeDeletable DELETABLE = new FakeDeletable();
    private final String CONTAINING_CLASS_NAME = "ContainingClassName";

    private FakeHasDeletionInvariants _hasDeletionInvariants;

    @BeforeEach
    void setUp() {
        _hasDeletionInvariants = new FakeHasDeletionInvariants();
        _hasDeletionInvariants.ContainingObject = DELETABLE;
        _hasDeletionInvariants.ContainingClassName = CONTAINING_CLASS_NAME;
    }

    @Test
    void testDeletedInvariant() {
        _hasDeletionInvariants.delete();

        try {
            _hasDeletionInvariants.callEnforceInvariants();
        }
        catch (EntityDeletedException e) {
            assertEquals(FakeHasDeletionInvariants.class.getCanonicalName() +
                            ".callEnforceInvariants: object is deleted",
                    e.getMessage());
        }
        catch (Exception e) {
            fail("Incorrect exception caught");
        }
    }

    @Test
    void testContainingObjectDeletedInvariant() {
        DELETABLE.delete();

        try {
            _hasDeletionInvariants.callEnforceInvariants();
        }
        catch (IllegalStateException e) {
            assertEquals(FakeHasDeletionInvariants.class.getCanonicalName() +
                            ".callEnforceInvariants: containing " + CONTAINING_CLASS_NAME +
                            " is deleted",
                    e.getMessage());
        }
        catch (Exception e) {
            fail("Incorrect exception caught");
        }
    }
}
