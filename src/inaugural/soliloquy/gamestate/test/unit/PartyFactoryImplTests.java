package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.PartyFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.PartyFactory;

import static org.junit.jupiter.api.Assertions.*;

class PartyFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final VariableCache DATA = new VariableCacheStub();

    private PartyFactory _partyFactory;

    @BeforeEach
    void setUp() {
        _partyFactory = new PartyFactoryImpl(COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new PartyFactoryImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PartyFactory.class.getCanonicalName(), _partyFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Party party = _partyFactory.make(DATA);

        assertNotNull(party);
        assertSame(DATA, party.attributes());
    }
}
