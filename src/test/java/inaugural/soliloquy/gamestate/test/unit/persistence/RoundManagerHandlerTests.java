package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RoundManagerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class RoundManagerHandlerTests {
    private final int ROUND_NUMBER = randomInt();
    private final UUID CHARACTER_1_UUID = UUID.randomUUID();
    private final UUID CHARACTER_2_UUID = UUID.randomUUID();
    private final UUID CHARACTER_3_UUID = UUID.randomUUID();
    private final String CHARACTER_1_ROUND_DATA = randomString();
    private final String CHARACTER_2_ROUND_DATA = randomString();
    private final String CHARACTER_3_ROUND_DATA = randomString();

    @Mock private Character mockCharacter1;
    @Mock private Character mockCharacter2;
    @Mock private Character mockCharacter3;
    @Mock private Map<String, Object> mockData1;
    @Mock private Map<String, Object> mockData2;
    @Mock private Map<String, Object> mockData3;
    @SuppressWarnings("rawtypes") @Mock private TypeHandler<Map> mockMapHandler;
    @Mock private Function<UUID, Character> mockGetCharacterFromCurrentGameZone;
    @Mock private RoundManager mockRoundManager;

    private TypeHandler<RoundManager> handler;

    private final String DATA = String.format(
            "{\"roundNumber\":%d,\"characterIds\":[\"%s\",\"%s\",\"%s\"]," +
                    "\"characterRoundData\":[\"%s\",\"%s\",\"%s\"]}",
            ROUND_NUMBER, CHARACTER_1_UUID, CHARACTER_2_UUID, CHARACTER_3_UUID,
            CHARACTER_1_ROUND_DATA, CHARACTER_2_ROUND_DATA, CHARACTER_3_ROUND_DATA);

    @BeforeEach
    public void setUp() {
        lenient().when(mockCharacter1.uuid()).thenReturn(CHARACTER_1_UUID);
        lenient().when(mockCharacter2.uuid()).thenReturn(CHARACTER_2_UUID);
        lenient().when(mockCharacter3.uuid()).thenReturn(CHARACTER_3_UUID);

        lenient().when(mockMapHandler.read(CHARACTER_1_ROUND_DATA)).thenReturn(mockData1);
        lenient().when(mockMapHandler.read(CHARACTER_2_ROUND_DATA)).thenReturn(mockData2);
        lenient().when(mockMapHandler.read(CHARACTER_3_ROUND_DATA)).thenReturn(mockData3);
        lenient().when(mockMapHandler.write(mockData1)).thenReturn(CHARACTER_1_ROUND_DATA);
        lenient().when(mockMapHandler.write(mockData2)).thenReturn(CHARACTER_2_ROUND_DATA);
        lenient().when(mockMapHandler.write(mockData3)).thenReturn(CHARACTER_3_ROUND_DATA);

        lenient().when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_1_UUID)).thenReturn(
                mockCharacter1);
        lenient().when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_2_UUID)).thenReturn(
                mockCharacter2);
        lenient().when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_3_UUID)).thenReturn(
                mockCharacter3);

        lenient().when(mockRoundManager.getRoundNumber()).thenReturn(ROUND_NUMBER);
        lenient().when(mockRoundManager.characterQueueRepresentation()).thenReturn(listOf(
                pairOf(mockCharacter1, mockData1),
                pairOf(mockCharacter2, mockData2),
                pairOf(mockCharacter3, mockData3)
        ));

        handler = new RoundManagerHandler(mockRoundManager, mockMapHandler,
                mockGetCharacterFromCurrentGameZone);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerHandler(null, mockMapHandler,
                        mockGetCharacterFromCurrentGameZone));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerHandler(mockRoundManager, null,
                        mockGetCharacterFromCurrentGameZone));
        assertThrows(IllegalArgumentException.class,
                () -> new RoundManagerHandler(mockRoundManager, mockMapHandler, null));
    }

    @Test
    public void testWrite() {
        var writtenValue = handler.write(mockRoundManager);

        assertEquals(DATA, writtenValue);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var output = handler.read(DATA);

        assertNull(output);
        verify(mockRoundManager, once()).clearQueue();
        verify(mockRoundManager, once()).setRoundNumber(ROUND_NUMBER);
        verify(mockGetCharacterFromCurrentGameZone, once()).apply(CHARACTER_1_UUID);
        verify(mockRoundManager, once()).setCharacterPositionInQueue(mockCharacter1, 0);
        verify(mockMapHandler, once()).read(CHARACTER_1_ROUND_DATA);
        verify(mockRoundManager, once()).setCharacterRoundData(mockCharacter1, mockData1);
        verify(mockGetCharacterFromCurrentGameZone, once()).apply(CHARACTER_2_UUID);
        verify(mockRoundManager, once()).setCharacterPositionInQueue(mockCharacter2, 1);
        verify(mockMapHandler, once()).read(CHARACTER_2_ROUND_DATA);
        verify(mockRoundManager, once()).setCharacterRoundData(mockCharacter2, mockData2);
        verify(mockGetCharacterFromCurrentGameZone, once()).apply(CHARACTER_3_UUID);
        verify(mockRoundManager, once()).setCharacterPositionInQueue(mockCharacter3, 2);
        verify(mockMapHandler, once()).read(CHARACTER_3_ROUND_DATA);
        verify(mockRoundManager, once()).setCharacterRoundData(mockCharacter3, mockData3);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
