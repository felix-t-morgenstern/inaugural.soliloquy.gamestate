package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.test.fakes.FakeDeletable;
import inaugural.soliloquy.gamestate.test.fakes.FakeHasDeletionInvariants;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class HasDeletionInvariantsTests {
    private final FakeDeletable DELETABLE = new FakeDeletable();
    private final String CONTAINING_CLASS_NAME = "ContainingClassName";

    private FakeHasDeletionInvariants _hasDeletionInvariants;

    @Before
    public void setUp() {
        _hasDeletionInvariants = new FakeHasDeletionInvariants();
        _hasDeletionInvariants.ContainingObject = DELETABLE;
        _hasDeletionInvariants.ContainingClassName = CONTAINING_CLASS_NAME;
    }

    @Test
    public void testDeletedInvariant() {
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
    public void testContainingObjectDeletedInvariant() {
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
