package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.GameZoneHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class GameZoneHandlerTests {
    @SuppressWarnings("rawtypes")
    private final HashMap<String, Action> ACTIONS = new HashMap<>();
    private final String ON_ENTRY_ACTION_ID = randomString();
    private final String ON_EXIT_ACTION_ID = randomString();
    private final String ID = randomString();
    private final String TYPE = randomString();
    private final String NAME = randomString();
    private final String DATA = randomString();
    private final int TILES_WIDTH = randomIntInRange(5, 10);
    private final int TILES_HEIGHT = randomIntInRange(5, 10);
    private final int TOTAL_NUMBER_TILES = TILES_WIDTH * TILES_HEIGHT;
    private final int TILES_PER_BATCH = randomIntInRange(2, 10);
    private final String TILE = randomString();
    private final int THREAD_POOL_SIZE = randomIntInRange(2, 5);

    @Mock private Tile mockTile;
    @Mock private TypeHandler<Tile> mockTileHandler;
    @Mock private VariableCache mockData;
    @Mock private TypeHandler<VariableCache> mockVariableCacheHandler;
    /** @noinspection rawtypes */
    @Mock private List<Action> mockOnEntry;
    /** @noinspection rawtypes */
    @Mock private List<Action> mockOnExit;
    @Mock private GameZone mockGameZone;
    @Mock private GameZoneFactory mockGameZoneFactory;
    @Mock private Action<Void> mockOnEntryAction;
    @Mock private Action<Void> mockOnExitAction;

    private TypeHandler<GameZone> gameZoneHandler;

    private final String WRITTEN_DATA = String.format(
            "{\"id\":\"%s\",\"type\":\"%s\",\"name\":\"%s\",\"data\":\"%s\",\"onEntry\":[\"%s\"]," +
                    "\"onExit\":[\"%s\"],\"maxX\":%d,\"maxY\":%d,\"tiles\":%s}",
            ID, TYPE, NAME, DATA, ON_ENTRY_ACTION_ID, ON_EXIT_ACTION_ID, TILES_WIDTH - 1,
            TILES_HEIGHT - 1, generateExpectedWrittenTiles(TILES_WIDTH, TILES_HEIGHT));

    @BeforeEach
    void setUp() {
        mockTile = mock(Tile.class);

        //noinspection unchecked
        mockTileHandler = (TypeHandler<Tile>) mock(TypeHandler.class);
        when(mockTileHandler.read(anyString())).thenReturn(mockTile);
        when(mockTileHandler.write(any())).thenReturn(TILE);

        mockData = mock(VariableCache.class);

        //noinspection unchecked
        mockVariableCacheHandler = (TypeHandler<VariableCache>) mock(TypeHandler.class);
        when(mockVariableCacheHandler.read(anyString())).thenReturn(mockData);
        when(mockVariableCacheHandler.write(any())).thenReturn(DATA);

        //noinspection unchecked
        mockOnEntryAction = mock(Action.class);
        when(mockOnEntryAction.id()).thenReturn(ON_ENTRY_ACTION_ID);

        //noinspection unchecked
        mockOnExitAction = mock(Action.class);
        when(mockOnExitAction.id()).thenReturn(ON_EXIT_ACTION_ID);

        //noinspection unchecked,rawtypes
        mockOnEntry = (List<Action>) mock(List.class);
        when(mockOnEntry.size()).thenReturn(1);
        //noinspection rawtypes
        when(mockOnEntry.iterator()).thenReturn(new ArrayList<Action>() {{
            add(mockOnEntryAction);
        }}.iterator());

        //noinspection unchecked,rawtypes
        mockOnExit = (List<Action>) mock(List.class);
        when(mockOnExit.size()).thenReturn(1);
        //noinspection rawtypes
        when(mockOnExit.iterator()).thenReturn(new ArrayList<Action>() {{
            add(mockOnExitAction);
        }}.iterator());

        mockGameZone = mock(GameZone.class);
        when(mockGameZone.id()).thenReturn(ID);
        when(mockGameZone.type()).thenReturn(TYPE);
        when(mockGameZone.getName()).thenReturn(NAME);
        when(mockGameZone.data()).thenReturn(mockData);
        when(mockGameZone.maxCoordinates())
                .thenReturn(Coordinate2d.of(TILES_WIDTH - 1, TILES_HEIGHT - 1));
        when(mockGameZone.onEntry()).thenReturn(mockOnEntry);
        when(mockGameZone.onExit()).thenReturn(mockOnExit);
        when(mockGameZone.tile(any())).thenReturn(mockTile);

        mockGameZoneFactory = mock(GameZoneFactory.class);
        when(mockGameZoneFactory.make(anyString(), anyString(), any(), any()))
                .thenReturn(mockGameZone);

        ACTIONS.put(ON_ENTRY_ACTION_ID, mockOnEntryAction);
        ACTIONS.put(ON_EXIT_ACTION_ID, mockOnExitAction);
        gameZoneHandler = new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                mockVariableCacheHandler, ACTIONS::get, TILES_PER_BATCH, THREAD_POOL_SIZE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(null, mockTileHandler,
                        mockVariableCacheHandler, ACTIONS::get, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, null,
                        mockVariableCacheHandler, ACTIONS::get, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        null, ACTIONS::get, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockVariableCacheHandler, null, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockVariableCacheHandler, ACTIONS::get, randomIntWithInclusiveCeiling(0),
                        THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockVariableCacheHandler, ACTIONS::get, TILES_PER_BATCH,
                        randomIntWithInclusiveCeiling(0)));
    }

    @Test
    void testWrite() {
        String writtenData = gameZoneHandler.write(mockGameZone);

        assertEquals(WRITTEN_DATA, writtenData);
        verify(mockGameZone, times(1)).id();
        verify(mockGameZone, times(1)).type();
        verify(mockGameZone, times(1)).getName();
        verify(mockGameZone, times(1)).data();
        verify(mockVariableCacheHandler, times(1)).write(mockData);
        verify(mockGameZone, times(2)).onEntry();
        verify(mockOnEntry, times(1)).size();
        verify(mockOnEntry, times(1)).iterator();
        verify(mockOnEntryAction, times(1)).id();
        verify(mockGameZone, times(2)).onExit();
        verify(mockOnEntry, times(1)).size();
        verify(mockOnEntry, times(1)).iterator();
        verify(mockOnExitAction, times(1)).id();
        verify(mockGameZone, times(1)).maxCoordinates();
        verify(mockGameZone, times(TOTAL_NUMBER_TILES)).tile(any());
        verify(mockTileHandler, times(TOTAL_NUMBER_TILES)).write(mockTile);
        for (int x = 0; x < TILES_WIDTH; x++) {
            for (int y = 0; y < TILES_HEIGHT; y++) {
                verify(mockGameZone, times(1)).tile(eq(Coordinate2d.of(x, y)));
            }
        }
    }

    @Test
    void testWriteBubblesUpInnerThreadExceptions() {
        Exception e = new IllegalArgumentException();
        when(mockTileHandler.write(any())).thenThrow(e);

        try {
            gameZoneHandler.write(mockGameZone);
            fail("Exception should have been thrown");
        }
        catch (Exception thrown) {
            assertSame(e, thrown);
        }
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> gameZoneHandler.write(null));
    }

    @Test
    void testRead() {
        GameZone gameZone = gameZoneHandler.read(WRITTEN_DATA);

        assertNotNull(gameZone);
        assertSame(mockGameZone, gameZone);
        verify(mockTileHandler, times(TOTAL_NUMBER_TILES)).read(TILE);
        Tile[][] tiles = new Tile[TILES_WIDTH][TILES_HEIGHT];
        for (int x = 0; x < TILES_WIDTH; x++) {
            for (int y = 0; y < TILES_HEIGHT; y++) {
                tiles[x][y] = mockTile;
            }
        }
        verify(mockVariableCacheHandler, times(1)).read(DATA);
        verify(mockGameZoneFactory, times(1)).make(eq(ID), eq(TYPE), eq(tiles), same(mockData));
        verify(mockGameZone, times(1)).setName(NAME);
        verify(mockGameZone, times(1)).onEntry();
        verify(mockOnEntry, times(1)).add(mockOnEntryAction);
        verify(mockGameZone, times(1)).onExit();
        verify(mockOnExit, times(1)).add(mockOnExitAction);
    }

    @Test
    void testReadBubblesUpInnerThreadExceptions() {
        Exception e = new IllegalArgumentException();
        when(mockTileHandler.read(anyString())).thenThrow(e);

        try {
            gameZoneHandler.read(WRITTEN_DATA);
            fail("Exception should have been thrown");
        }
        catch (Exception thrown) {
            assertSame(e, thrown);
        }
    }

    @Test
    void testGetArchetype() {
        assertNotNull(gameZoneHandler.getArchetype());
        assertEquals(GameZone.class.getCanonicalName(),
                gameZoneHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterface() {
        assertEquals(
                TypeHandler.class.getCanonicalName() + "<" + GameZone.class.getCanonicalName() +
                        ">", gameZoneHandler.getInterfaceName());
    }

    private String generateExpectedWrittenTiles(int width, int height) {
        StringBuilder builder = new StringBuilder("[");
        for (int x = 0; x < width; x++) {
            builder.append("[");
            for (int y = 0; y < height; y++) {
                builder.append("\"");
                builder.append(TILE);
                builder.append("\"");
                if (y < height - 1) {
                    builder.append(",");
                }
            }
            builder.append("]");
            if (x < width - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
