package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.PartyHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.PartyFactory;

import java.util.List;
import java.util.UUID;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PartyHandlerTests {
    @Mock private PartyFactory mockPartyFactory;

    @Mock private GameZone mockGameZone;

    @Mock private Tile mockTile;

    @Mock private TypeHandler<VariableCache> mockAttributesHandler;
    private final String ATTRIBUTES = randomString();
    @Mock private VariableCache mockAttributes;

    @Mock private TypeHandler<Character> mockCharacterHandler;

    @Mock private Character mockPc1;
    @Mock private Character mockPc2;
    @Mock private Character mockPc3;
    @Mock private Character mockNpc;

    private final String PC_1_UUID = UUID.randomUUID().toString();
    private final String PC_2_UUID = UUID.randomUUID().toString();
    private final String PC_3_UUID = UUID.randomUUID().toString();
    private final String NPC_UUID = UUID.randomUUID().toString();

    private final String PC_2_CHARACTER = randomString();

    @Mock private Party mockParty;

    private TypeHandler<Party> partyHandler;

    private final String WRITTEN_VALUE = String.format(
            "{\"pcs\":[{\"uuid\":\"%s\"},{\"uuid\":\"%s\",\"character\":\"%s\"}," +
                    "{\"uuid\":\"%s\"}],\"attributes\":\"%s\"}",
            PC_1_UUID, PC_2_UUID, PC_2_CHARACTER, PC_3_UUID, ATTRIBUTES);

    @Before
    public void setUp() {
        when(mockPc1.uuid()).thenReturn(UUID.fromString(PC_1_UUID));
        when(mockPc2.uuid()).thenReturn(UUID.fromString(PC_2_UUID));
        when(mockPc3.uuid()).thenReturn(UUID.fromString(PC_3_UUID));

        when(mockGameZone.charactersRepresentation()).thenReturn(mapOf(
                pairOf(UUID.fromString(PC_1_UUID), mockPc1),
                pairOf(UUID.fromString(PC_3_UUID), mockPc3),
                pairOf(UUID.fromString(NPC_UUID), mockNpc)
        ));

        when(mockPc1.tile()).thenReturn(mockTile);
        when(mockPc2.tile()).thenReturn(null);
        when(mockPc3.tile()).thenReturn(mockTile);

        when(mockAttributesHandler.read(anyString())).thenReturn(mockAttributes);
        when(mockAttributesHandler.write(any())).thenReturn(ATTRIBUTES);

        when(mockCharacterHandler.read(PC_2_CHARACTER)).thenReturn(mockPc2);
        when(mockCharacterHandler.write(mockPc2)).thenReturn(PC_2_CHARACTER);

        when(mockParty.characters()).thenReturn(listOf(mockPc1, mockPc2, mockPc3));
        when(mockParty.attributes()).thenReturn(mockAttributes);

        when(mockPartyFactory.make(any())).thenReturn(mockParty);

        partyHandler = new PartyHandler(mockPartyFactory, () -> mockGameZone, mockAttributesHandler,
                mockCharacterHandler);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PartyHandler(null, () -> mockGameZone,
                        mockAttributesHandler, mockCharacterHandler));
        assertThrows(IllegalArgumentException.class,
                () -> new PartyHandler(mockPartyFactory, null,
                        mockAttributesHandler, mockCharacterHandler));
        assertThrows(IllegalArgumentException.class,
                () -> new PartyHandler(mockPartyFactory, () -> mockGameZone,
                        null, mockCharacterHandler));
        assertThrows(IllegalArgumentException.class,
                () -> new PartyHandler(mockPartyFactory, () -> mockGameZone,
                        mockAttributesHandler, null));
    }

    @Test
    public void testWrite() {
        var writtenValue = partyHandler.write(mockParty);

        assertEquals(WRITTEN_VALUE, writtenValue);
        verify(mockAttributesHandler, times(1)).write(mockAttributes);
        verify(mockCharacterHandler, times(1)).write(mockPc2);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> partyHandler.write(null));
    }

    @Test
    public void testRead() {
        List<Character> partyCharacters = listOf();
        when(mockParty.characters()).thenReturn(partyCharacters);

        var readParty = partyHandler.read(WRITTEN_VALUE);

        assertNotNull(readParty);
        assertSame(mockParty, readParty);
        verify(mockAttributesHandler, times(1)).read(ATTRIBUTES);
        verify(mockPartyFactory, times(1)).make(mockAttributes);
        assertEquals(3, partyCharacters.size());
        assertTrue(partyCharacters.contains(mockPc1));
        assertTrue(partyCharacters.contains(mockPc2));
        assertTrue(partyCharacters.contains(mockPc3));
        verify(mockCharacterHandler, times(1)).read(PC_2_CHARACTER);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> partyHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> partyHandler.read(""));
    }

    @Test
    public void testArchetype() {
        assertNotNull(partyHandler.archetype());
        assertEquals(Party.class.getCanonicalName(),
                partyHandler.archetype().getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(
                TypeHandler.class.getCanonicalName() + "<" + Party.class.getCanonicalName() + ">",
                partyHandler.getInterfaceName());
    }
}
