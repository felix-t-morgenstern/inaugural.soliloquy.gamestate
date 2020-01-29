package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.PartyImpl;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
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
        _party = new PartyImpl(new CollectionFactoryStub(), DATA);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Party.class.getCanonicalName(), _party.getInterfaceName());
    }

    @Test
    void testPlayerCharacters() {
        assertNotNull(_party.playerCharacters());
    }

    @Test
    void testAttributes() {
        assertSame(DATA, _party.attributes());
    }
}
