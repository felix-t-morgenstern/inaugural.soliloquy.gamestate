package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.PartyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Party;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class PartyImplTests {
    private Map<String, Object> data;

    private Party party;

    @BeforeEach
    public void setUp() {
        data = mapOf(pairOf(randomString(), randomInt()));

        party = new PartyImpl(data);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new PartyImpl(null));
    }

    @Test
    public void testCharacters() {
        assertNotNull(party.characters());
    }

    @Test
    public void testAttributes() {
        assertEquals(data, party.attributes());
        assertNotSame(data, party.attributes());
    }
}
