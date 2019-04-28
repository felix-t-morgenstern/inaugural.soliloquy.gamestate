package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.Party;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.GenericParamsSetFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.IParty;

import static org.junit.jupiter.api.Assertions.*;

class PartyTests {
    private IParty _party;

    @BeforeEach
    void setUp() {
        _party = new Party(new CollectionFactoryStub(), new GenericParamsSetFactoryStub());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IParty.class.getCanonicalName(), _party.getInterfaceName());
    }

    @Test
    void testPlayerCharacters() {
        assertNotNull(_party.playerCharacters());
    }

    @Test
    void testPartyAttributes() {
        assertNotNull(_party.partyAttributes());
    }
}
