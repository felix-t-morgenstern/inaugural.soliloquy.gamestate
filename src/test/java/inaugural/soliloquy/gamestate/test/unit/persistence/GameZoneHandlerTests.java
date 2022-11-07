package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.GameZoneHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameZone;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeTileHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeVariableCacheHandler;
import inaugural.soliloquy.gamestate.test.spydoubles.GameZoneFactorySpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameZoneHandlerTests {
    private final GameZoneFactorySpyDouble GAME_ZONE_FACTORY = new GameZoneFactorySpyDouble();
    private final FakeTileHandler TILE_HANDLER = new FakeTileHandler();
    private final FakeVariableCacheHandler DATA_HANDLER =
            new FakeVariableCacheHandler();
    @SuppressWarnings("rawtypes")
    private final HashMap<String, Action> ACTIONS = new HashMap<>();
    private final String ON_ENTRY_ACTION_ID = "onEntryActionId";
    private final String ON_EXIT_ACTION_ID = "onExitActionId";
    private final String ID = "id";
    private final String TYPE = "type";
    private final VariableCache DATA = new VariableCacheStub();
    private final String NAME = "name";

    @Mock private Action<Void> mockOnEntryAction;
    @Mock private Action<Void> mockOnExitAction;

    private TypeHandler<GameZone> gameZoneHandler;

    private final String WRITTEN_DATA =
            "{\"id\":\"id\",\"type\":\"type\",\"name\":\"name\",\"data\":\"VariableCache0\"," +
                    "\"onEntry\":[\"onEntryActionId\"],\"onExit\":[\"onExitActionId\"]," +
                    "\"maxX\":2,\"maxY\":2,\"tiles\":[[\"Tile0\",\"Tile1\",\"Tile2\"],[\"Tile3\"," +
                    "\"Tile4\",\"Tile5\"],[\"Tile6\",\"Tile7\",\"Tile8\"]]}";

    @BeforeEach
    void setUp() {
        //noinspection unchecked
        mockOnEntryAction = mock(Action.class);
        when(mockOnEntryAction.id()).thenReturn(ON_ENTRY_ACTION_ID);

        //noinspection unchecked
        mockOnExitAction = mock(Action.class);
        when(mockOnExitAction.id()).thenReturn(ON_EXIT_ACTION_ID);

        ACTIONS.put(ON_ENTRY_ACTION_ID, mockOnEntryAction);
        ACTIONS.put(ON_EXIT_ACTION_ID, mockOnExitAction);
        gameZoneHandler = new GameZoneHandler(GAME_ZONE_FACTORY, TILE_HANDLER,
                DATA_HANDLER, ACTIONS::get);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(null, TILE_HANDLER,
                        DATA_HANDLER, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(GAME_ZONE_FACTORY, null,
                        DATA_HANDLER, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(GAME_ZONE_FACTORY, TILE_HANDLER,
                        null, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneHandler(GAME_ZONE_FACTORY, TILE_HANDLER,
                        DATA_HANDLER, null));
    }

    @Test
    void testWrite() {
        Tile[][] tiles = new Tile[3][3];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = new FakeTile();
            }
        }
        GameZone gameZone = new FakeGameZone(ID, TYPE, tiles, DATA);
        gameZone.setName(NAME);
        gameZone.onEntry().add(mockOnEntryAction);
        gameZone.onExit().add(mockOnExitAction);

        String writtenData = gameZoneHandler.write(gameZone);

        assertEquals(WRITTEN_DATA, writtenData);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> gameZoneHandler.write(null));
    }

    @Test
    void testRead() {
        GameZone gameZone = gameZoneHandler.read(WRITTEN_DATA);

        assertNotNull(gameZone);
        assertEquals(ID, GAME_ZONE_FACTORY._inputId);
        assertEquals(TYPE, GAME_ZONE_FACTORY._inputZoneType);
        assertEquals(NAME, gameZone.getName());
        int index = 0;
        for (int x = 0; x < GAME_ZONE_FACTORY._inputTiles.length; x++) {
            for (int y = 0; y < GAME_ZONE_FACTORY._inputTiles[0].length; y++) {
                assertEquals(TILE_HANDLER.READ_INPUTS.get(index), "Tile" + index);
                assertSame(TILE_HANDLER.READ_OUTPUTS.get(index++),
                        GAME_ZONE_FACTORY._inputTiles[x][y]);
            }
        }
        assertSame(DATA_HANDLER.READ_OUTPUTS.get(0), GAME_ZONE_FACTORY._inputData);
        assertEquals(1, gameZone.onEntry().size());
        assertTrue(gameZone.onEntry().contains(mockOnEntryAction));
        assertEquals(1, gameZone.onExit().size());
        assertTrue(gameZone.onExit().contains(mockOnExitAction));
    }
}
