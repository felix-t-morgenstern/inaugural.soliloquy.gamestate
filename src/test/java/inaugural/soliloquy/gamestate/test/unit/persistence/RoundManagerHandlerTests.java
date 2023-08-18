package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.RoundManagerHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.RoundManager;

import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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
    @Mock private VariableCache mockVariableCache1;
    @Mock private VariableCache mockVariableCache2;
    @Mock private VariableCache mockVariableCache3;
    @Mock private TypeHandler<VariableCache> mockVariableCacheHandler;
    @Mock private Function<UUID, Character> mockGetCharacterFromCurrentGameZone;
    @Mock private RoundManager mockRoundManager;

    private TypeHandler<RoundManager> roundManagerHandler;

    private final String DATA = String.format(
            "{\"roundNumber\":%d,\"characterIds\":[\"%s\",\"%s\",\"%s\"]," +
                    "\"characterRoundData\":[\"%s\",\"%s\",\"%s\"]}",
            ROUND_NUMBER, CHARACTER_1_UUID, CHARACTER_2_UUID, CHARACTER_3_UUID,
            CHARACTER_1_ROUND_DATA, CHARACTER_2_ROUND_DATA, CHARACTER_3_ROUND_DATA);

    @Before
    public void setUp() {
        when(mockCharacter1.uuid()).thenReturn(CHARACTER_1_UUID);
        when(mockCharacter2.uuid()).thenReturn(CHARACTER_2_UUID);
        when(mockCharacter3.uuid()).thenReturn(CHARACTER_3_UUID);

        when(mockVariableCacheHandler.read(CHARACTER_1_ROUND_DATA)).thenReturn(mockVariableCache1);
        when(mockVariableCacheHandler.read(CHARACTER_2_ROUND_DATA)).thenReturn(mockVariableCache2);
        when(mockVariableCacheHandler.read(CHARACTER_3_ROUND_DATA)).thenReturn(mockVariableCache3);
        when(mockVariableCacheHandler.write(mockVariableCache1)).thenReturn(CHARACTER_1_ROUND_DATA);
        when(mockVariableCacheHandler.write(mockVariableCache2)).thenReturn(CHARACTER_2_ROUND_DATA);
        when(mockVariableCacheHandler.write(mockVariableCache3)).thenReturn(CHARACTER_3_ROUND_DATA);

        when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_1_UUID)).thenReturn(
                mockCharacter1);
        when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_2_UUID)).thenReturn(
                mockCharacter2);
        when(mockGetCharacterFromCurrentGameZone.apply(CHARACTER_3_UUID)).thenReturn(
                mockCharacter3);
        
        when(mockRoundManager.getRoundNumber()).thenReturn(ROUND_NUMBER);
        when(mockRoundManager.characterQueueRepresentation()).thenReturn(listOf(
                pairOf(mockCharacter1, mockVariableCache1),
                pairOf(mockCharacter2, mockVariableCache2),
                pairOf(mockCharacter3, mockVariableCache3)
        ));

        roundManagerHandler = new RoundManagerHandler(mockRoundManager, mockVariableCacheHandler,
                mockGetCharacterFromCurrentGameZone);
    }

    @Test
    public void testConstructorWithInvalidParams() {
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
    public void testWrite() {
        var writtenValue = roundManagerHandler.write(mockRoundManager);

        assertEquals(DATA, writtenValue);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> roundManagerHandler.write(null));
    }

    @Test
    public void testRead() {
        var output = roundManagerHandler.read(DATA);

        assertNull(output);
        verify(mockRoundManager, times(1)).clearQueue();
        verify(mockRoundManager, times(1)).setRoundNumber(ROUND_NUMBER);
        verify(mockGetCharacterFromCurrentGameZone, times(1)).apply(CHARACTER_1_UUID);
        verify(mockRoundManager, times(1)).setCharacterPositionInQueue(mockCharacter1, 0);
        verify(mockVariableCacheHandler, times(1)).read(CHARACTER_1_ROUND_DATA);
        verify(mockRoundManager, times(1)).setCharacterRoundData(mockCharacter1,
                mockVariableCache1);
        verify(mockGetCharacterFromCurrentGameZone, times(1)).apply(CHARACTER_2_UUID);
        verify(mockRoundManager, times(1)).setCharacterPositionInQueue(mockCharacter2, 1);
        verify(mockVariableCacheHandler, times(1)).read(CHARACTER_2_ROUND_DATA);
        verify(mockRoundManager, times(1)).setCharacterRoundData(mockCharacter2,
                mockVariableCache2);
        verify(mockGetCharacterFromCurrentGameZone, times(1)).apply(CHARACTER_3_UUID);
        verify(mockRoundManager, times(1)).setCharacterPositionInQueue(mockCharacter3, 2);
        verify(mockVariableCacheHandler, times(1)).read(CHARACTER_3_ROUND_DATA);
        verify(mockRoundManager, times(1)).setCharacterRoundData(mockCharacter3,
                mockVariableCache3);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> roundManagerHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> roundManagerHandler.read(""));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(
                TypeHandler.class.getCanonicalName() + "<" + RoundManager.class.getCanonicalName() +
                        ">", roundManagerHandler.getInterfaceName());
    }
}
