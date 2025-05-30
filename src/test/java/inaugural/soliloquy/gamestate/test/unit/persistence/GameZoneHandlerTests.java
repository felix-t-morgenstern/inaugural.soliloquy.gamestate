package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.entities.GameZoneImpl;
import inaugural.soliloquy.gamestate.persistence.GameZoneHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameZoneHandlerTests {
    private final String ENTRY_ACTION_ID = randomString();
    private final String EXIT_ACTION_ID = randomString();
    private final String ID = randomString();
    private final String NAME = randomString();
    // Arbitrary numbers must be chosen here so the tiles written in the output will be of a
    // consistent size
    private final Coordinate2d MAX_COORDINATES = randomCoordinate2d();
    private final Coordinate3d TILE_LOCATION = randomCoordinate3d();
    private final String DATA_STR = randomString();
    private final int TILES_PER_BATCH = randomIntInRange(2, 10);
    private final String TILE_STR = randomString();
    private final int THREAD_POOL_SIZE = randomIntInRange(2, 5);

    private Tile mockTile;
    private TypeHandler<Tile> mockTileHandler;
    @SuppressWarnings("rawtypes") private TypeHandler<Map> mockMapHandler;
    private Map<String, Object> mockData;
    /** @noinspection rawtypes */
    private Action mockEntryAction;
    /** @noinspection rawtypes */
    private Action mockExitAction;
    @SuppressWarnings("rawtypes") private Function<String, Action> mockActionLookup;
    @SuppressWarnings("rawtypes") private List<Action> mockZoneOnEntry;
    @SuppressWarnings("rawtypes") private List<Action> mockZoneOnExit;
    private Set<Tile> mockZoneTiles;
    @Mock private GameZone mockGameZone;
    @Mock private GameZoneFactory mockGameZoneFactory;

    private TypeHandler<GameZone> handler;

    private final String WRITTEN_DATA = String.format(
            "{\"id\":\"%s\",\"name\":\"%s\"," + "\"data\":\"%s\",\"onEntry\":[\"%s\"]," +
                    "\"onExit\":[\"%s\"],\"maxX\":%d," +
                    "\"maxY\":%d,\"tiles\":[\"{\\\"x\\\":%d,\\\"y\\\":%d," +
                    "\\\"z\\\":%d,\\\"tile\\\":\\\"%s\\\"}\"]}", ID, NAME, DATA_STR,
            ENTRY_ACTION_ID, EXIT_ACTION_ID, MAX_COORDINATES.X, MAX_COORDINATES.Y, TILE_LOCATION.X,
            TILE_LOCATION.Y, TILE_LOCATION.Z, TILE_STR);

    @BeforeEach
    public void setUp() {
        var tileAndHandler = generateMockEntityAndHandler(Tile.class, TILE_STR);
        mockTile = tileAndHandler.entity;
        lenient().when(mockTile.location()).thenReturn(TILE_LOCATION);
        mockTileHandler = tileAndHandler.handler;

        var dataAndHandler = generateMockEntityAndHandler(Map.class, DATA_STR);
        //noinspection unchecked
        mockData = dataAndHandler.entity;
        mockMapHandler = dataAndHandler.handler;

        var actionsAndLookup =
                generateMockLookupFunctionWithId(Action.class, ENTRY_ACTION_ID, EXIT_ACTION_ID);
        mockActionLookup = actionsAndLookup.lookup;
        mockEntryAction = actionsAndLookup.entities.get(0);
        mockExitAction = actionsAndLookup.entities.get(1);

        mockZoneOnEntry = generateMockList(mockEntryAction);
        mockZoneOnExit = generateMockList(mockExitAction);

        mockZoneTiles = generateMockSet(mockTile);

        lenient().when(mockGameZone.id()).thenReturn(ID);
        lenient().when(mockGameZone.getName()).thenReturn(NAME);
        lenient().when(mockGameZone.data()).thenReturn(mockData);
        lenient().when(mockGameZone.maxCoordinates()).thenReturn(MAX_COORDINATES);
        lenient().when(mockGameZone.onEntry()).thenReturn(mockZoneOnEntry);
        lenient().when(mockGameZone.onExit()).thenReturn(mockZoneOnExit);
        lenient().when(mockGameZone.tiles()).thenReturn(mockZoneTiles);

        lenient().when(mockGameZoneFactory.make(anyString(), any(), any())).thenReturn(mockGameZone);

        handler = new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                mockMapHandler, mockActionLookup, TILES_PER_BATCH, THREAD_POOL_SIZE);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(null, mockTileHandler,
                        mockMapHandler, mockActionLookup, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, null,
                        mockMapHandler, mockActionLookup, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        null, mockActionLookup, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockMapHandler, null, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockMapHandler, mockActionLookup, randomIntWithInclusiveCeiling(0),
                        THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockMapHandler, mockActionLookup, TILES_PER_BATCH,
                        randomIntWithInclusiveCeiling(0)));
    }

    @Test
    public void testTypeHandled() {
        assertEquals(GameZoneImpl.class.getCanonicalName(), handler.typeHandled());
    }

    @Test
    public void testWrite() {
        var writtenData = handler.write(mockGameZone);

        assertEquals(WRITTEN_DATA, writtenData);
        verify(mockGameZone).id();
        verify(mockGameZone).getName();
        verify(mockGameZone).data();
        verify(mockMapHandler).write(mockData);
        verify(mockGameZone, times(2)).onEntry();
        verify(mockZoneOnEntry).size();
        verify(mockZoneOnEntry).iterator();
        verify(mockEntryAction).id();
        verify(mockGameZone, times(2)).onExit();
        verify(mockZoneOnEntry).size();
        verify(mockZoneOnEntry).iterator();
        verify(mockExitAction).id();
        verify(mockGameZone).maxCoordinates();
        verify(mockGameZone).tiles();
        verify(mockZoneTiles).iterator();
        verify(mockTileHandler).write(mockTile);
    }

    @Test
    public void testWriteBubblesUpInnerThreadExceptions() {
        var e = new IllegalArgumentException();
        when(mockTileHandler.write(any())).thenThrow(e);

        try {
            handler.write(mockGameZone);
            fail("Exception should have been thrown");
        }
        catch (Exception thrown) {
            assertSame(e, thrown);
        }
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var output = handler.read(WRITTEN_DATA);

        assertNotNull(output);
        assertSame(mockGameZone, output);
        verify(mockMapHandler).read(DATA_STR);
        verify(mockGameZoneFactory).make(eq(ID), eq(MAX_COORDINATES), same(mockData));
        verify(mockGameZone).setName(NAME);
        verify(mockActionLookup, times(2)).apply(anyString());
        verify(mockActionLookup).apply(ENTRY_ACTION_ID);
        verify(mockGameZone).onEntry();
        verify(mockZoneOnEntry).add(mockEntryAction);
        verify(mockActionLookup).apply(EXIT_ACTION_ID);
        verify(mockGameZone).onExit();
        verify(mockZoneOnExit).add(mockExitAction);
        verify(mockTileHandler).read(TILE_STR);
        verify(mockGameZone).putTile(same(mockTile), eq(TILE_LOCATION));
    }

    @Test
    public void testReadBubblesUpInnerThreadExceptions() {
        var e = new IllegalArgumentException();
        when(mockTileHandler.read(anyString())).thenThrow(e);

        try {
            handler.read(WRITTEN_DATA);
            fail("Exception should have been thrown");
        }
        catch (Exception thrown) {
            assertSame(e, thrown);
        }
    }
}
