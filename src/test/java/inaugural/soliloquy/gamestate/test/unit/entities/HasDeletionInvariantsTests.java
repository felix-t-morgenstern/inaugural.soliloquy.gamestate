package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.test.fakes.FakeDeletable;
import inaugural.soliloquy.gamestate.test.fakes.FakeHasDeletionInvariants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HasDeletionInvariantsTests {
    private final FakeDeletable DELETABLE = new FakeDeletable();
    private final String CONTAINING_CLASS_NAME = "ContainingClassName";

    private FakeHasDeletionInvariants hasDeletionInvariants;

    @BeforeEach
    public void setUp() {
        hasDeletionInvariants = new FakeHasDeletionInvariants();
        hasDeletionInvariants.ContainingObject = DELETABLE;
        hasDeletionInvariants.ContainingClassName = CONTAINING_CLASS_NAME;
    }

    @Test
    public void testDeletedInvariant() {
        hasDeletionInvariants.delete();

        try {
            hasDeletionInvariants.callEnforceInvariants();
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
    public void testContainingObjectDeletedInvariant() {
        DELETABLE.delete();

        try {
            hasDeletionInvariants.callEnforceInvariants();
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
