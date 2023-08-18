package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.PartyFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.PartyFactory;

import static org.junit.Assert.*;

public class PartyFactoryImplTests {
    private final VariableCache DATA = new VariableCacheStub();

    private PartyFactory _partyFactory;

    @Before
    public void setUp() {
        _partyFactory = new PartyFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(PartyFactory.class.getCanonicalName(), _partyFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        Party party = _partyFactory.make(DATA);

        assertNotNull(party);
        assertSame(DATA, party.attributes());
    }
}
