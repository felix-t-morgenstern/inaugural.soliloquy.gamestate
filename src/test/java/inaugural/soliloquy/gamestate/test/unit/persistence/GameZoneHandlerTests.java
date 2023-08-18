package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.GameZoneHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameZoneHandlerTests {
    @SuppressWarnings("rawtypes")
    private final Map<String, Action> ACTIONS = mapOf();
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

    @Before
    public void setUp() {
        when(mockTileHandler.read(anyString())).thenReturn(mockTile);
        when(mockTileHandler.write(any())).thenReturn(TILE);

        when(mockVariableCacheHandler.read(anyString())).thenReturn(mockData);
        when(mockVariableCacheHandler.write(any())).thenReturn(DATA);

        when(mockOnEntryAction.id()).thenReturn(ON_ENTRY_ACTION_ID);

        when(mockOnExitAction.id()).thenReturn(ON_EXIT_ACTION_ID);

        when(mockOnEntry.size()).thenReturn(1);
        //noinspection rawtypes
        when(mockOnEntry.iterator()).thenReturn(listOf((Action) mockOnEntryAction).iterator());

        when(mockOnExit.size()).thenReturn(1);
        //noinspection rawtypes
        when(mockOnExit.iterator()).thenReturn(listOf((Action) mockOnExitAction).iterator());

        when(mockGameZone.id()).thenReturn(ID);
        when(mockGameZone.type()).thenReturn(TYPE);
        when(mockGameZone.getName()).thenReturn(NAME);
        when(mockGameZone.data()).thenReturn(mockData);
        when(mockGameZone.maxCoordinates())
                .thenReturn(Coordinate2d.of(TILES_WIDTH - 1, TILES_HEIGHT - 1));
        when(mockGameZone.onEntry()).thenReturn(mockOnEntry);
        when(mockGameZone.onExit()).thenReturn(mockOnExit);
        when(mockGameZone.tile(any())).thenReturn(mockTile);

        when(mockGameZoneFactory.make(anyString(), anyString(), any(), any()))
                .thenReturn(mockGameZone);

        ACTIONS.put(ON_ENTRY_ACTION_ID, mockOnEntryAction);
        ACTIONS.put(ON_EXIT_ACTION_ID, mockOnExitAction);
        gameZoneHandler = new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                mockVariableCacheHandler, ACTIONS::get, TILES_PER_BATCH, THREAD_POOL_SIZE);
    }

    @Test
    public void testConstructorWithInvalidParams() {
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
    public void testWrite() {
        var writtenData = gameZoneHandler.write(mockGameZone);

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
        for (var x = 0; x < TILES_WIDTH; x++) {
            for (var y = 0; y < TILES_HEIGHT; y++) {
                verify(mockGameZone, times(1)).tile(eq(Coordinate2d.of(x, y)));
            }
        }
    }

    @Test
    public void testWriteBubblesUpInnerThreadExceptions() {
        var e = new IllegalArgumentException();
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
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> gameZoneHandler.write(null));
    }

    @Test
    public void testRead() {
        var gameZone = gameZoneHandler.read(WRITTEN_DATA);

        assertNotNull(gameZone);
        assertSame(mockGameZone, gameZone);
        verify(mockTileHandler, times(TOTAL_NUMBER_TILES)).read(TILE);
        Tile[][] tiles = new Tile[TILES_WIDTH][TILES_HEIGHT];
        for (var x = 0; x < TILES_WIDTH; x++) {
            Arrays.fill(tiles[x], mockTile);
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
    public void testReadBubblesUpInnerThreadExceptions() {
        var e = new IllegalArgumentException();
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
    public void testArchetype() {
        assertNotNull(gameZoneHandler.archetype());
        assertEquals(GameZone.class.getCanonicalName(),
                gameZoneHandler.archetype().getInterfaceName());
    }

    @Test
    public void testGetInterface() {
        assertEquals(
                TypeHandler.class.getCanonicalName() + "<" + GameZone.class.getCanonicalName() +
                        ">", gameZoneHandler.getInterfaceName());
    }

    private String generateExpectedWrittenTiles(int width, int height) {
        var builder = new StringBuilder("[");
        for (var x = 0; x < width; x++) {
            builder.append("[");
            for (var y = 0; y < height; y++) {
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
