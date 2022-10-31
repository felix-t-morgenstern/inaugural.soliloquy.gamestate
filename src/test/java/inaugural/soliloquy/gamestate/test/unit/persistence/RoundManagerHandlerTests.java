package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RoundManagerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoundManagerHandlerTests {
    private final int ROUND_NUMBER = 123123;
    private final UUID CHARACTER_1_UUID = UUID.fromString("03521ec9-c6e2-49f7-80b6-aa7e212ba5cb");
    private final UUID CHARACTER_2_UUID = UUID.fromString("80e702c8-8d2e-4dd9-b96c-a840d9c70ce6");
    private final UUID CHARACTER_3_UUID = UUID.fromString("d75d02ef-9528-47a6-bb97-97b73fc5fc97");
    private final String CHARACTER_1_ROUND_DATA = "character1RoundData";
    private final String CHARACTER_2_ROUND_DATA = "character2RoundData";
    private final String CHARACTER_3_ROUND_DATA = "character3RoundData";

    @Mock private Character mockCharacter1;
    @Mock private Character mockCharacter2;
    @Mock private Character mockCharacter3;
    @Mock private VariableCache mockVariableCache1;
    @Mock private VariableCache mockVariableCache2;
    @Mock private VariableCache mockVariableCache3;
    @Mock private TypeHandler<VariableCache> mockVariableCacheHandler;
    @Mock private Function<UUID, Character> mockGetCharacterFromCurrentGameZone;
    @Mock private RoundManager mockRoundManager;

    private TypeHandler<RoundManager> roundManagerHandler;

    private final String DATA = "{\"roundNumber\":123123,\"characterIds\":[\"03521ec9-c6e2-49f7" +
            "-80b6-aa7e212ba5cb\",\"80e702c8-8d2e-4dd9-b96c-a840d9c70ce6\"," +
            "\"d75d02ef-9528-47a6-bb97-97b73fc5fc97\"]," +
            "\"characterRoundData\":[\"character1RoundData\",\"character2RoundData\"," +
            "\"character3RoundData\"]}";

    @BeforeEach
    void setUp() {
        mockCharacter1 = mock(Character.class);
        when(mockCharacter1.uuid()).thenReturn(CHARACTER_1_UUID);
        mockCharacter2 = mock(Character.class);
        when(mockCharacter2.uuid()).thenReturn(CHARACTER_2_UUID);
        mockCharacter3 = mock(Character.class);
        when(mockCharacter3.uuid()).thenReturn(CHARACTER_3_UUID);
        mockVariableCache1 = mock(VariableCache.class);
        mockVariableCache2 = mock(VariableCache.class);
        mockVariableCache3 = mock(VariableCache.class);

        //noinspection unchecked
        mockVariableCacheHandler = mock(TypeHandler.class);
        when(mockVariableCacheHandler.read(CHARACTER_1_ROUND_DATA)).thenReturn(mockVariableCache1);
        when(mockVariableCacheHandler.read(CHARACTER_2_ROUND_DATA)).thenReturn(mockVariableCache2);
        when(mockVariableCacheHandler.read(CHARACTER_3_ROUND_DATA)).thenReturn(mockVariableCache3);
        when(mockVariableCacheHandler.write(mockVariableCache1)).thenReturn(CHARACTER_1_ROUND_DATA);
        when(mockVariableCacheHandler.write(mockVariableCache2)).thenReturn(CHARACTER_2_ROUND_DATA);
        when(mockVariableCacheHandler.write(mockVariableCache3)).thenReturn(CHARACTER_3_ROUND_DATA);

        //noinspection unchecked
        mockGetCharacterFromCurrentGameZone = mock(Function.class);
        when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_1_UUID)).thenReturn(mockCharacter1);
        when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_2_UUID)).thenReturn(mockCharacter2);
        when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_3_UUID)).thenReturn(mockCharacter3);

        mockRoundManager = mock(RoundManager.class);
        when(mockRoundManager.getRoundNumber()).thenReturn(ROUND_NUMBER);
        when(mockRoundManager.characterQueueRepresentation()).thenReturn(new ArrayList<>() {{
            add(new Pair<>(mockCharacter1, mockVariableCache1));
            add(new Pair<>(mockCharacter2, mockVariableCache2));
            add(new Pair<>(mockCharacter3, mockVariableCache3));
        }});

        roundManagerHandler = new RoundManagerHandler(mockRoundManager, mockVariableCacheHandler,
                mockGetCharacterFromCurrentGameZone);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerHandler(null, mockVariableCacheHandler,
                        mockGetCharacterFromCurrentGameZone));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerHandler(mockRoundManager, null,
                        mockGetCharacterFromCurrentGameZone));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerHandler(mockRoundManager, mockVariableCacheHandler,
                        null));
    }

    @Test
    void testWrite() {
        String writtenValue = roundManagerHandler.write(mockRoundManager);

        assertEquals(DATA, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> roundManagerHandler.write(null));
    }

    @Test
    void testRead() {
        RoundManager output = roundManagerHandler.read(DATA);

        assertNull(output);
        verify(mockRoundManager, times(1)).clearQueue();
        verify(mockRoundManager, times(1)).setRoundNumber(ROUND_NUMBER);
        verify(mockGetCharacterFromCurrentGameZone, times(1)).apply(CHARACTER_1_UUID);
        verify(mockRoundManager, times(1)).setCharacterPositionInQueue(mockCharacter1, 0);
        verify(mockVariableCacheHandler, times(1)).read(CHARACTER_1_ROUND_DATA);
        verify(mockRoundManager, times(1)).setCharacterRoundData(mockCharacter1, mockVariableCache1);
        verify(mockGetCharacterFromCurrentGameZone, times(1)).apply(CHARACTER_2_UUID);
        verify(mockRoundManager, times(1)).setCharacterPositionInQueue(mockCharacter2, 1);
        verify(mockVariableCacheHandler, times(1)).read(CHARACTER_2_ROUND_DATA);
        verify(mockRoundManager, times(1)).setCharacterRoundData(mockCharacter2, mockVariableCache2);
        verify(mockGetCharacterFromCurrentGameZone, times(1)).apply(CHARACTER_3_UUID);
        verify(mockRoundManager, times(1)).setCharacterPositionInQueue(mockCharacter3, 2);
        verify(mockVariableCacheHandler, times(1)).read(CHARACTER_3_ROUND_DATA);
        verify(mockRoundManager, times(1)).setCharacterRoundData(mockCharacter3, mockVariableCache3);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> roundManagerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> roundManagerHandler.read(""));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(
                TypeHandler.class.getCanonicalName() + "<" + RoundManager.class.getCanonicalName() +
                        ">", roundManagerHandler.getInterfaceName());
    }
}
