package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.PartyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.PartyFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PartyHandlerTests {
    private PartyFactory _mockPartyFactory;

    private GameZone _mockGameZone;

    private Tile _mockTile;

    private TypeHandler<VariableCache> _mockAttributesHandler;
    private final String ATTRIBUTES = "attributes";
    private VariableCache _mockAttributes;

    private TypeHandler<Character> _mockCharacterHandler;

    private Character _pc1;
    private Character _pc2;
    private Character _pc3;
    private Character _npc;

    private final String PC_1_UUID = "1f04a671-e85e-427a-a806-1d754147def2";
    private final String PC_2_UUID = "2e123cf3-35c3-4a6d-b64b-47ebbe5cc1d4";
    private final String PC_3_UUID = "da157ca9-9ef9-4026-b7cd-5824b52f9323";

    private final String PC_2_CHARACTER = "pc2Character";

    private Party _mockParty;

    private TypeHandler<Party> _partyHandler;

    private final String WRITTEN_VALUE = "{\"pcs\":[{\"uuid\":\"1f04a671-e85e-427a-a806" +
            "-1d754147def2\"},{\"uuid\":\"2e123cf3-35c3-4a6d-b64b-47ebbe5cc1d4\"," +
            "\"character\":\"pc2Character\"}," +
            "{\"uuid\":\"da157ca9-9ef9-4026-b7cd-5824b52f9323\"}],\"attributes\":\"attributes\"}";

    @BeforeEach
    void setUp() {
        _pc1 = mock(Character.class);
        _pc2 = mock(Character.class);
        _pc3 = mock(Character.class);
        _npc = mock(Character.class);

        when(_pc1.uuid()).thenReturn(UUID.fromString(PC_1_UUID));
        when(_pc2.uuid()).thenReturn(UUID.fromString(PC_2_UUID));
        when(_pc3.uuid()).thenReturn(UUID.fromString(PC_3_UUID));
        when(_npc.uuid()).thenReturn(UUID.randomUUID());

        _mockGameZone = mock(GameZone.class);
        when(_mockGameZone.charactersRepresentation()).thenReturn(new ArrayList<>() {{
            add(_pc1);
            add(_pc3);
            add(_npc);
        }});

        _mockTile = mock(Tile.class);

        when(_pc1.tile()).thenReturn(_mockTile);
        when(_pc2.tile()).thenReturn(null);
        when(_pc3.tile()).thenReturn(_mockTile);

        _mockAttributes = mock(VariableCache.class);

        //noinspection unchecked
        _mockAttributesHandler = mock(TypeHandler.class);
        when(_mockAttributesHandler.read(anyString())).thenReturn(_mockAttributes);
        when(_mockAttributesHandler.write(any())).thenReturn(ATTRIBUTES);

        //noinspection unchecked
        _mockCharacterHandler = mock(TypeHandler.class);
        when(_mockCharacterHandler.read(PC_2_CHARACTER)).thenReturn(_pc2);
        when(_mockCharacterHandler.write(_pc2)).thenReturn(PC_2_CHARACTER);

        _mockParty = mock(Party.class);
        when(_mockParty.characters()).thenReturn(new ArrayList<>() {{
            add(_pc1);
            add(_pc2);
            add(_pc3);
        }});
        when(_mockParty.attributes()).thenReturn(_mockAttributes);

        _mockPartyFactory = mock(PartyFactory.class);
        when(_mockPartyFactory.make(any())).thenReturn(_mockParty);

        _partyHandler =
                new PartyHandler(_mockPartyFactory, () -> _mockGameZone, _mockAttributesHandler,
                        _mockCharacterHandler);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PartyHandler(null, () -> _mockGameZone,
                        _mockAttributesHandler, _mockCharacterHandler));
        assertThrows(IllegalArgumentException.class,
                () -> new PartyHandler(_mockPartyFactory, null,
                        _mockAttributesHandler, _mockCharacterHandler));
        assertThrows(IllegalArgumentException.class,
                () -> new PartyHandler(_mockPartyFactory, () -> _mockGameZone,
                        null, _mockCharacterHandler));
        assertThrows(IllegalArgumentException.class,
                () -> new PartyHandler(_mockPartyFactory, () -> _mockGameZone,
                        _mockAttributesHandler, null));
    }

    @Test
    void testWrite() {
        String writtenValue = _partyHandler.write(_mockParty);

        assertEquals(WRITTEN_VALUE, writtenValue);
        verify(_mockAttributesHandler, times(1)).write(_mockAttributes);
        verify(_mockCharacterHandler, times(1)).write(_pc2);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _partyHandler.write(null));
    }

    @Test
    void testRead() {
        ArrayList<Character> partyCharacters = new ArrayList<>();
        when(_mockParty.characters()).thenReturn(partyCharacters);

        Party readParty = _partyHandler.read(WRITTEN_VALUE);

        assertNotNull(readParty);
        assertSame(_mockParty, readParty);
        verify(_mockAttributesHandler, times(1)).read(ATTRIBUTES);
        verify(_mockPartyFactory, times(1)).make(_mockAttributes);
        assertEquals(3, partyCharacters.size());
        assertTrue(partyCharacters.contains(_pc1));
        assertTrue(partyCharacters.contains(_pc2));
        assertTrue(partyCharacters.contains(_pc3));
        verify(_mockCharacterHandler, times(1)).read(PC_2_CHARACTER);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _partyHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _partyHandler.read(""));
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_partyHandler.getArchetype());
        assertEquals(Party.class.getCanonicalName(),
                _partyHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(
                TypeHandler.class.getCanonicalName() + "<" + Party.class.getCanonicalName() + ">",
                _partyHandler.getInterfaceName());
    }
}
