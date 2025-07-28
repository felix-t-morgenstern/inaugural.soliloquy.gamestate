package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.GameStateHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import inaugural.soliloquy.tools.testing.Mock.HandlerAndEntity;

import java.util.Map;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static inaugural.soliloquy.tools.testing.Mock.generateMockEntityAndHandler;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameStateHandlerTests {
    private final String CURRENT_GAME_ZONE_ID = randomString();
    private final String PARTY_WRITTEN_VALUE = randomString();
    private final String DATA_WRITTEN_VALUE = randomString();
    private final String ROUND_MANAGER_WRITTEN_VALUE = randomString();
    private final String ROUND_BASED_TIMER_MANAGER_WRITTEN_VALUE = randomString();
    private final String CLOCK_BASED_TIMER_MANAGER_WRITTEN_VALUE = randomString();

    private final HandlerAndEntity<Party> MOCK_PARTY_AND_HANDLER =
            generateMockEntityAndHandler(Party.class, PARTY_WRITTEN_VALUE);
    private final Party PARTY = MOCK_PARTY_AND_HANDLER.entity;
    private final TypeHandler<Party> PARTY_HANDLER = MOCK_PARTY_AND_HANDLER.handler;

    @SuppressWarnings("rawtypes") private final HandlerAndEntity<Map>
            MOCK_VARIABLE_CACHE_AND_HANDLER =
            generateMockEntityAndHandler(Map.class, DATA_WRITTEN_VALUE);
    @SuppressWarnings("unchecked") private final Map<String, Object> MOCK_DATA =
            MOCK_VARIABLE_CACHE_AND_HANDLER.entity;
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> VARIABLE_CACHE_HANDLER =
            MOCK_VARIABLE_CACHE_AND_HANDLER.handler;

    private final HandlerAndEntity<RoundManager> MOCK_ROUND_MANAGER_AND_HANDLER =
            generateMockEntityAndHandler(RoundManager.class, ROUND_MANAGER_WRITTEN_VALUE);
    private final RoundManager MOCK_ROUND_MANAGER = MOCK_ROUND_MANAGER_AND_HANDLER.entity;
    private final TypeHandler<RoundManager> MOCK_ROUND_MANAGER_HANDLER =
            MOCK_ROUND_MANAGER_AND_HANDLER.handler;

    private final HandlerAndEntity<RoundBasedTimerManager>
            MOCK_ROUND_BASED_TIMER_MANAGER_AND_HANDLER =
            generateMockEntityAndHandler(RoundBasedTimerManager.class,
                    ROUND_BASED_TIMER_MANAGER_WRITTEN_VALUE);
    private final RoundBasedTimerManager MOCK_ROUND_BASED_TIMER_MANAGER =
            MOCK_ROUND_BASED_TIMER_MANAGER_AND_HANDLER.entity;
    private final TypeHandler<RoundBasedTimerManager> MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER =
            MOCK_ROUND_BASED_TIMER_MANAGER_AND_HANDLER.handler;

    private final HandlerAndEntity<ClockBasedTimerManager>
            MOCK_CLOCK_BASED_TIMER_MANAGER_AND_HANDLER =
            generateMockEntityAndHandler(ClockBasedTimerManager.class,
                    CLOCK_BASED_TIMER_MANAGER_WRITTEN_VALUE);
    private final ClockBasedTimerManager MOCK_CLOCK_BASED_TIMER_MANAGER =
            MOCK_CLOCK_BASED_TIMER_MANAGER_AND_HANDLER.entity;
    private final TypeHandler<ClockBasedTimerManager> MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER =
            MOCK_CLOCK_BASED_TIMER_MANAGER_AND_HANDLER.handler;

    @Mock private GameZone mockGameZone;
    @Mock private GameZoneRepo mockGameZoneRepo;
    @Mock private GameState mockGameState;

    private TypeHandler<GameState> handler;

    private final String WRITTEN_DATA = String.format(
            "{\"party\":\"%s\",\"data\":\"%s\",\"currentGameZoneId\":\"%s\"," +
                    "\"roundManager\":\"%s\",\"roundBasedTimerManager\":\"%s\"," +
                    "\"clockBasedTimerManager\":\"%s\"}",
            PARTY_WRITTEN_VALUE, DATA_WRITTEN_VALUE, CURRENT_GAME_ZONE_ID,
            ROUND_MANAGER_WRITTEN_VALUE, ROUND_BASED_TIMER_MANAGER_WRITTEN_VALUE,
            CLOCK_BASED_TIMER_MANAGER_WRITTEN_VALUE);

    @BeforeEach
    public void setUp() {
        mockGameZone = mock(GameZone.class);
        when(mockGameZone.id()).thenReturn(CURRENT_GAME_ZONE_ID);

        mockGameZoneRepo = mock(GameZoneRepo.class);
        when(mockGameZoneRepo.currentGameZone()).thenReturn(mockGameZone);

        mockGameState = mock(GameState.class);
        when(mockGameState.gameZoneRepo()).thenReturn(mockGameZoneRepo);
        when(mockGameState.party()).thenReturn(PARTY);
        when(mockGameState.data()).thenReturn(MOCK_DATA);
        when(mockGameState.roundManager()).thenReturn(MOCK_ROUND_MANAGER);
        when(mockGameState.roundBasedTimerManager()).thenReturn(MOCK_ROUND_BASED_TIMER_MANAGER);
        when(mockGameState.clockBasedTimerManager()).thenReturn(MOCK_CLOCK_BASED_TIMER_MANAGER);

        handler =
                new GameStateHandler(mockGameState, PARTY_HANDLER, VARIABLE_CACHE_HANDLER,
                        MOCK_ROUND_MANAGER_HANDLER, MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER,
                        MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(null, PARTY_HANDLER, VARIABLE_CACHE_HANDLER,
                        MOCK_ROUND_MANAGER_HANDLER, MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER,
                        MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, null, VARIABLE_CACHE_HANDLER,
                        MOCK_ROUND_MANAGER_HANDLER, MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER,
                        MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER, null,
                        MOCK_ROUND_MANAGER_HANDLER, MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER,
                        MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER, VARIABLE_CACHE_HANDLER,
                        null, MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER,
                        MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER, VARIABLE_CACHE_HANDLER,
                        MOCK_ROUND_MANAGER_HANDLER, null, MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER, VARIABLE_CACHE_HANDLER,
                        MOCK_ROUND_MANAGER_HANDLER, MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER, null));
    }

    @Test
    public void testWrite() {
        String output = handler.write(mockGameState);

        assertEquals(WRITTEN_DATA, output);
        verify(PARTY_HANDLER, once()).write(PARTY);
        verify(VARIABLE_CACHE_HANDLER, once()).write(MOCK_DATA);
        verify(MOCK_ROUND_MANAGER_HANDLER, once()).write(MOCK_ROUND_MANAGER);
        verify(MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER, once()).write(
                MOCK_ROUND_BASED_TIMER_MANAGER);
        verify(MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER, once()).write(
                MOCK_CLOCK_BASED_TIMER_MANAGER);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        GameState output = handler.read(WRITTEN_DATA);

        assertNull(output);
        verify(mockGameZoneRepo, once()).loadGameZone(CURRENT_GAME_ZONE_ID);
        verify(PARTY_HANDLER, once()).read(PARTY_WRITTEN_VALUE);
        verify(VARIABLE_CACHE_HANDLER, once()).read(DATA_WRITTEN_VALUE);
        verify(MOCK_ROUND_MANAGER_HANDLER, once()).read(ROUND_MANAGER_WRITTEN_VALUE);
        verify(MOCK_ROUND_BASED_TIMER_MANAGER_HANDLER, once()).read(
                ROUND_BASED_TIMER_MANAGER_WRITTEN_VALUE);
        verify(MOCK_CLOCK_BASED_TIMER_MANAGER_HANDLER, once()).read(
                CLOCK_BASED_TIMER_MANAGER_WRITTEN_VALUE);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
