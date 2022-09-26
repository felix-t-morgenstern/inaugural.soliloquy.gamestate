package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.PartyImpl;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;

import static org.junit.jupiter.api.Assertions.*;

class PartyImplTests {
    private final static VariableCache DATA = new VariableCacheStub();

    private Party _party;

    @BeforeEach
    void setUp() {
        _party = new PartyImpl(DATA);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new PartyImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Party.class.getCanonicalName(), _party.getInterfaceName());
    }

    @Test
    void testCharacters() {
        assertNotNull(_party.characters());
    }

    @Test
    void testAttributes() {
        assertSame(DATA, _party.attributes());
    }
}
