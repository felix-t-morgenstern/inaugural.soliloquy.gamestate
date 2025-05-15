package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.gamestate.entities.PartyImpl;
import inaugural.soliloquy.gamestate.persistence.PartyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.Tile;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartyHandlerTests {
    @Mock private Function<Map<String, Object>, Party> mockPartyFactory;

    @Mock private GameZone mockGameZone;

    @Mock private Tile mockTile;

    @SuppressWarnings("rawtypes") @Mock private TypeHandler<Map> mockAttributesHandler;
    private final String ATTRIBUTES = randomString();
    @Mock private Map<String, Object> mockAttributes;

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

    @BeforeEach
    public void setUp() {
        lenient().when(mockPc1.uuid()).thenReturn(UUID.fromString(PC_1_UUID));
        lenient().when(mockPc2.uuid()).thenReturn(UUID.fromString(PC_2_UUID));
        lenient().when(mockPc3.uuid()).thenReturn(UUID.fromString(PC_3_UUID));

        lenient().when(mockGameZone.charactersRepresentation()).thenReturn(mapOf(
                pairOf(UUID.fromString(PC_1_UUID), mockPc1),
                pairOf(UUID.fromString(PC_3_UUID), mockPc3),
                pairOf(UUID.fromString(NPC_UUID), mockNpc)
        ));

        lenient().when(mockPc1.tile()).thenReturn(mockTile);
        lenient().when(mockPc2.tile()).thenReturn(null);
        lenient().when(mockPc3.tile()).thenReturn(mockTile);

        lenient().when(mockAttributesHandler.read(anyString())).thenReturn(mockAttributes);
        lenient().when(mockAttributesHandler.write(any())).thenReturn(ATTRIBUTES);

        lenient().when(mockCharacterHandler.read(PC_2_CHARACTER)).thenReturn(mockPc2);
        lenient().when(mockCharacterHandler.write(mockPc2)).thenReturn(PC_2_CHARACTER);

        lenient().when(mockParty.characters()).thenReturn(listOf(mockPc1, mockPc2, mockPc3));
        lenient().when(mockParty.attributes()).thenReturn(mockAttributes);

        lenient().when(mockPartyFactory.apply(any())).thenReturn(mockParty);

        partyHandler = new PartyHandler(mockPartyFactory, () -> mockGameZone, mockAttributesHandler,
                mockCharacterHandler);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
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
    public void testTypeHandled() {
        assertEquals(PartyImpl.class.getCanonicalName(), partyHandler.typeHandled());
    }

    @Test
    public void testWrite() {
        var writtenValue = partyHandler.write(mockParty);

        assertEquals(WRITTEN_VALUE, writtenValue);
        verify(mockAttributesHandler, once()).write(mockAttributes);
        verify(mockCharacterHandler, once()).write(mockPc2);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> partyHandler.write(null));
    }

    @Test
    public void testRead() {
        List<Character> partyCharacters = listOf();
        when(mockParty.characters()).thenReturn(partyCharacters);

        var readParty = partyHandler.read(WRITTEN_VALUE);

        assertNotNull(readParty);
        assertSame(mockParty, readParty);
        verify(mockAttributesHandler, once()).read(ATTRIBUTES);
        verify(mockPartyFactory, once()).apply(mockAttributes);
        assertEquals(3, partyCharacters.size());
        assertTrue(partyCharacters.contains(mockPc1));
        assertTrue(partyCharacters.contains(mockPc2));
        assertTrue(partyCharacters.contains(mockPc3));
        verify(mockCharacterHandler, once()).read(PC_2_CHARACTER);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> partyHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> partyHandler.read(""));
    }
}
