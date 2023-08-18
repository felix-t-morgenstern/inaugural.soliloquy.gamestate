package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.PartyImpl;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;

import static org.junit.Assert.*;

public class PartyImplTests {
    private final static VariableCache DATA = new VariableCacheStub();

    private Party party;

    @Before
    public void setUp() {
        party = new PartyImpl(DATA);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new PartyImpl(null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(Party.class.getCanonicalName(), party.getInterfaceName());
    }

    @Test
    public void testCharacters() {
        assertNotNull(party.characters());
    }

    @Test
    public void testAttributes() {
        assertSame(DATA, party.attributes());
    }
}
