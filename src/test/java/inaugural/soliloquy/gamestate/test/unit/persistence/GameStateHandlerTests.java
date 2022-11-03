package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.GameStateHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.timers.ClockBasedTimerManager;
import soliloquy.specs.gamestate.entities.timers.RoundBasedTimerManager;
import inaugural.soliloquy.tools.testing.Mock.TypeAndHandler;

import static inaugural.soliloquy.tools.testing.Mock.generateMockEntityAndHandler;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameStateHandlerTests {
    private final String CURRENT_GAME_ZONE_ID = "currentGameZoneId";
    private final String PARTY_WRITTEN_VALUE = "party";
    private final String VARIABLE_CACHE_WRITTEN_VALUE = "variableCache";
    private final String CAMERA_WRITTEN_VALUE = "camera";
    private final String ROUND_MANAGER_WRITTEN_VALUE = "roundManager";
    private final String ROUND_BASED_TIMER_MANAGER_WRITTEN_VALUE = "roundBasedTimerManager";
    private final String CLOCK_BASED_TIMER_MANAGER_WRITTEN_VALUE = "clockBasedTimerManager";

    private final TypeAndHandler<Party> MOCK_PARTY_AND_HANDLER =
            generateMockEntityAndHandler(Party.class, PARTY_WRITTEN_VALUE);
    private final Party PARTY = MOCK_PARTY_AND_HANDLER.entity;
    private final TypeHandler<Party> PARTY_HANDLER = MOCK_PARTY_AND_HANDLER.handler;

    private final TypeAndHandler<VariableCache> MOCK_VARIABLE_CACHE_AND_HANDLER =
            generateMockEntityAndHandler(VariableCache.class, VARIABLE_CACHE_WRITTEN_VALUE);
    private final VariableCache VARIABLE_CACHE = MOCK_VARIABLE_CACHE_AND_HANDLER.entity;
    private final TypeHandler<VariableCache> VARIABLE_CACHE_HANDLER =
            MOCK_VARIABLE_CACHE_AND_HANDLER.handler;

    private final TypeAndHandler<Camera> MOCK_CAMERA_AND_HANDLER =
            generateMockEntityAndHandler(Camera.class, CAMERA_WRITTEN_VALUE);
    private final Camera CAMERA = MOCK_CAMERA_AND_HANDLER.entity;
    private final TypeHandler<Camera> CAMERA_HANDLER = MOCK_CAMERA_AND_HANDLER.handler;

    private final TypeAndHandler<RoundManager> MOCK_ROUND_MANAGER_AND_HANDLER =
            generateMockEntityAndHandler(RoundManager.class, ROUND_MANAGER_WRITTEN_VALUE);
    private final RoundManager ROUND_MANAGER = MOCK_ROUND_MANAGER_AND_HANDLER.entity;
    private final TypeHandler<RoundManager> ROUND_MANAGER_HANDLER =
            MOCK_ROUND_MANAGER_AND_HANDLER.handler;

    private final TypeAndHandler<RoundBasedTimerManager>
            MOCK_ROUND_BASED_TIMER_MANAGER_AND_HANDLER =
            generateMockEntityAndHandler(RoundBasedTimerManager.class,
                    ROUND_BASED_TIMER_MANAGER_WRITTEN_VALUE);
    private final RoundBasedTimerManager ROUND_BASED_TIMER_MANAGER =
            MOCK_ROUND_BASED_TIMER_MANAGER_AND_HANDLER.entity;
    private final TypeHandler<RoundBasedTimerManager> ROUND_BASED_TIMER_MANAGER_HANDLER =
            MOCK_ROUND_BASED_TIMER_MANAGER_AND_HANDLER.handler;

    private final TypeAndHandler<ClockBasedTimerManager>
            MOCK_CLOCK_BASED_TIMER_MANAGER_AND_HANDLER =
            generateMockEntityAndHandler(ClockBasedTimerManager.class,
                    CLOCK_BASED_TIMER_MANAGER_WRITTEN_VALUE);
    private final ClockBasedTimerManager CLOCK_BASED_TIMER_MANAGER =
            MOCK_CLOCK_BASED_TIMER_MANAGER_AND_HANDLER.entity;
    private final TypeHandler<ClockBasedTimerManager> CLOCK_BASED_TIMER_MANAGER_HANDLER =
            MOCK_CLOCK_BASED_TIMER_MANAGER_AND_HANDLER.handler;

    @Mock private GameZone mockGameZone;
    @Mock private GameZonesRepo mockGameZonesRepo;
    @Mock private GameState mockGameState;

    private TypeHandler<GameState> gameStateHandler;

    private final String WRITTEN_DATA = "{\"party\":\"party\",\"variableCache\":\"variableCache" +
            "\",\"currentGameZoneId\":\"currentGameZoneId\",\"camera\":\"camera\"," +
            "\"roundManager\":\"roundManager\"," +
            "\"roundBasedTimerHandler\":\"roundBasedTimerManager\"," +
            "\"clockBasedTimerHandler\":\"clockBasedTimerManager\"}";

    @BeforeEach
    void setUp() {
        mockGameZone = mock(GameZone.class);
        when(mockGameZone.id()).thenReturn(CURRENT_GAME_ZONE_ID);

        mockGameZonesRepo = mock(GameZonesRepo.class);
        when(mockGameZonesRepo.getGameZone(anyString())).thenReturn(mockGameZone);

        mockGameState = mock(GameState.class);
        when(mockGameState.gameZonesRepo()).thenReturn(mockGameZonesRepo);
        when(mockGameState.party()).thenReturn(PARTY);
        when(mockGameState.getVariableCache()).thenReturn(VARIABLE_CACHE);
        when(mockGameState.getCurrentGameZone()).thenReturn(mockGameZone);
        when(mockGameState.camera()).thenReturn(CAMERA);
        when(mockGameState.roundManager()).thenReturn(ROUND_MANAGER);
        when(mockGameState.roundBasedTimerManager()).thenReturn(ROUND_BASED_TIMER_MANAGER);
        when(mockGameState.clockBasedTimerManager()).thenReturn(CLOCK_BASED_TIMER_MANAGER);

        gameStateHandler =
                new GameStateHandler(mockGameState, PARTY_HANDLER, VARIABLE_CACHE_HANDLER,
                        CAMERA_HANDLER, ROUND_MANAGER_HANDLER, ROUND_BASED_TIMER_MANAGER_HANDLER,
                        CLOCK_BASED_TIMER_MANAGER_HANDLER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(null, PARTY_HANDLER,
                        VARIABLE_CACHE_HANDLER, CAMERA_HANDLER, ROUND_MANAGER_HANDLER,
                        ROUND_BASED_TIMER_MANAGER_HANDLER, CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, null,
                        VARIABLE_CACHE_HANDLER, CAMERA_HANDLER, ROUND_MANAGER_HANDLER,
                        ROUND_BASED_TIMER_MANAGER_HANDLER, CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER,
                        null, CAMERA_HANDLER, ROUND_MANAGER_HANDLER,
                        ROUND_BASED_TIMER_MANAGER_HANDLER, CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER,
                        VARIABLE_CACHE_HANDLER, null, ROUND_MANAGER_HANDLER,
                        ROUND_BASED_TIMER_MANAGER_HANDLER, CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER,
                        VARIABLE_CACHE_HANDLER, CAMERA_HANDLER, null,
                        ROUND_BASED_TIMER_MANAGER_HANDLER, CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER,
                        VARIABLE_CACHE_HANDLER, CAMERA_HANDLER, ROUND_MANAGER_HANDLER,
                        null, CLOCK_BASED_TIMER_MANAGER_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new GameStateHandler(mockGameState, PARTY_HANDLER,
                        VARIABLE_CACHE_HANDLER, CAMERA_HANDLER, ROUND_MANAGER_HANDLER,
                        ROUND_BASED_TIMER_MANAGER_HANDLER, null));
    }

    @Test
    void testWrite() {
        String output = gameStateHandler.write(mockGameState);

        assertEquals(WRITTEN_DATA, output);
        verify(PARTY_HANDLER, times(1)).write(PARTY);
        verify(VARIABLE_CACHE_HANDLER, times(1)).write(VARIABLE_CACHE);
        verify(CAMERA_HANDLER, times(1)).write(CAMERA);
        verify(ROUND_MANAGER_HANDLER, times(1)).write(ROUND_MANAGER);
        verify(ROUND_BASED_TIMER_MANAGER_HANDLER, times(1)).write(ROUND_BASED_TIMER_MANAGER);
        verify(CLOCK_BASED_TIMER_MANAGER_HANDLER, times(1)).write(CLOCK_BASED_TIMER_MANAGER);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> gameStateHandler.write(null));
    }

    @Test
    void testRead() {
        GameState output = gameStateHandler.read(WRITTEN_DATA);

        assertNull(output);
        verify(mockGameZonesRepo, times(1)).getGameZone(CURRENT_GAME_ZONE_ID);
        verify(mockGameState, times(1)).setCurrentGameZone(mockGameZone);
        verify(PARTY_HANDLER, times(1)).read(PARTY_WRITTEN_VALUE);
        verify(VARIABLE_CACHE_HANDLER, times(1)).read(VARIABLE_CACHE_WRITTEN_VALUE);
        verify(CAMERA_HANDLER, times(1)).read(CAMERA_WRITTEN_VALUE);
        verify(ROUND_MANAGER_HANDLER, times(1)).read(ROUND_MANAGER_WRITTEN_VALUE);
        verify(ROUND_BASED_TIMER_MANAGER_HANDLER, times(1)).read(
                ROUND_BASED_TIMER_MANAGER_WRITTEN_VALUE);
        verify(CLOCK_BASED_TIMER_MANAGER_HANDLER, times(1)).read(
                CLOCK_BASED_TIMER_MANAGER_WRITTEN_VALUE);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> gameStateHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> gameStateHandler.read(""));
    }

    @Test
    void testArchetype() {
        assertNotNull(gameStateHandler.getArchetype());
        assertEquals(GameState.class.getCanonicalName(),
                gameStateHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        GameState.class.getCanonicalName() + ">",
                gameStateHandler.getInterfaceName());
    }
}
