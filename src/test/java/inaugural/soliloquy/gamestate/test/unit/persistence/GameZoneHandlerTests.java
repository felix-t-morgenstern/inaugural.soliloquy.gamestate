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
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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
    private TypeHandler<VariableCache> mockDataHandler;
    private VariableCache mockData;
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

    private TypeHandler<GameZone> gameZoneHandler;

    private final String WRITTEN_DATA = String.format(
            "{\"id\":\"%s\",\"name\":\"%s\"," + "\"data\":\"%s\",\"onEntry\":[\"%s\"]," +
                    "\"onExit\":[\"%s\"],\"maxX\":%d," +
                    "\"maxY\":%d,\"tiles\":[\"{\\\"x\\\":%d,\\\"y\\\":%d," +
                    "\\\"z\\\":%d,\\\"tile\\\":\\\"%s\\\"}\"]}", ID, NAME, DATA_STR,
            ENTRY_ACTION_ID, EXIT_ACTION_ID, MAX_COORDINATES.X, MAX_COORDINATES.Y, TILE_LOCATION.X,
            TILE_LOCATION.Y, TILE_LOCATION.Z, TILE_STR);

    @Before
    public void setUp() {
        var tileAndHandler = generateMockEntityAndHandler(Tile.class, TILE_STR);
        mockTile = tileAndHandler.entity;
        when(mockTile.location()).thenReturn(TILE_LOCATION);
        mockTileHandler = tileAndHandler.handler;

        var dataAndHandler = generateMockEntityAndHandler(VariableCache.class, DATA_STR);
        mockData = dataAndHandler.entity;
        mockDataHandler = dataAndHandler.handler;

        var actionsAndLookup =
                generateMockLookupFunctionWithId(Action.class, ENTRY_ACTION_ID, EXIT_ACTION_ID);
        mockActionLookup = actionsAndLookup.lookup;
        mockEntryAction = actionsAndLookup.entities.get(0);
        mockExitAction = actionsAndLookup.entities.get(1);

        mockZoneOnEntry = generateMockList(mockEntryAction);
        mockZoneOnExit = generateMockList(mockExitAction);

        mockZoneTiles = generateMockSet(mockTile);

        when(mockGameZone.id()).thenReturn(ID);
        when(mockGameZone.getName()).thenReturn(NAME);
        when(mockGameZone.data()).thenReturn(mockData);
        when(mockGameZone.maxCoordinates()).thenReturn(MAX_COORDINATES);
        when(mockGameZone.onEntry()).thenReturn(mockZoneOnEntry);
        when(mockGameZone.onExit()).thenReturn(mockZoneOnExit);
        when(mockGameZone.tiles()).thenReturn(mockZoneTiles);

        when(mockGameZoneFactory.make(anyString(), any(), any())).thenReturn(mockGameZone);

        gameZoneHandler = new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                mockDataHandler, mockActionLookup, TILES_PER_BATCH, THREAD_POOL_SIZE);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(null, mockTileHandler,
                        mockDataHandler, mockActionLookup, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, null,
                        mockDataHandler, mockActionLookup, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        null, mockActionLookup, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockDataHandler, null, TILES_PER_BATCH, THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockDataHandler, mockActionLookup, randomIntWithInclusiveCeiling(0),
                        THREAD_POOL_SIZE));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(mockGameZoneFactory, mockTileHandler,
                        mockDataHandler, mockActionLookup, TILES_PER_BATCH,
                        randomIntWithInclusiveCeiling(0)));
    }

    @Test
    public void testWrite() {
        var writtenData = gameZoneHandler.write(mockGameZone);

        assertEquals(WRITTEN_DATA, writtenData);
        verify(mockGameZone).id();
        verify(mockGameZone).getName();
        verify(mockGameZone).data();
        verify(mockDataHandler).write(mockData);
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

        //noinspection CatchMayIgnoreException
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
        var output = gameZoneHandler.read(WRITTEN_DATA);

        assertNotNull(output);
        assertSame(mockGameZone, output);
        verify(mockDataHandler).read(DATA_STR);
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
        verify(mockGameZone).addTile(same(mockTile), eq(TILE_LOCATION));
    }

    @Test
    public void testReadBubblesUpInnerThreadExceptions() {
        var e = new IllegalArgumentException();
        when(mockTileHandler.read(anyString())).thenThrow(e);

        //noinspection CatchMayIgnoreException
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
}
