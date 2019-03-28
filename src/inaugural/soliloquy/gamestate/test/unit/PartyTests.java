package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.Party;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.GenericParamsSetFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.IParty;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartyTests {
    private IParty _party;

    @BeforeEach
    protected void setUp() {
        _party = new Party(new CollectionFactoryStub(), new GenericParamsSetFactoryStub());
    }

    @Test
    public void testGetInterfaceName() {
        assertTrue(_party.getInterfaceName().equals("soliloquy.gamestate.specs.IParty"));
    }

    @Test
    public void testPlayerCharacters() {
        assertTrue(_party.playerCharacters() != null);
        assertTrue(_party.playerCharacters() instanceof ICollection);
    }

    @Test
    public void testPartyAttributes() {
        assertTrue(_party.partyAttributes() != null);
        assertTrue(_party.partyAttributes() instanceof IGenericParamsSet);
    }
}
