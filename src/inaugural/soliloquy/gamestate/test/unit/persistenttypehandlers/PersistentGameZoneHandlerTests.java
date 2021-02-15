package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentGameZoneHandler;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers.FakePersistentTileHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers.FakePersistentVariableCacheHandler;
import inaugural.soliloquy.gamestate.test.spydoubles.GameZoneFactorySpyDouble;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PersistentGameZoneHandlerTests {
    private final GameZoneFactorySpyDouble GAME_ZONE_FACTORY = new GameZoneFactorySpyDouble();
    private final FakePersistentTileHandler TILE_HANDLER = new FakePersistentTileHandler();
    private final FakePersistentVariableCacheHandler DATA_HANDLER =
            new FakePersistentVariableCacheHandler();
    @SuppressWarnings("rawtypes")
    private final HashMap<String, Action> ACTIONS = new HashMap<>();
    private final String ON_ENTRY_ACTION_ID = "onEntryActionId";
    private final Action<Void> ON_ENTRY_ACTION = new FakeVoidAction(ON_ENTRY_ACTION_ID);
    private final String ON_EXIT_ACTION_ID = "onExitActionId";
    private final Action<Void> ON_EXIT_ACTION = new FakeVoidAction(ON_EXIT_ACTION_ID);
    private final String ID = "id";
    private final String TYPE = "type";
    private final VariableCache DATA = new VariableCacheStub();
    private final String NAME = "name";

    private PersistentValueTypeHandler<GameZone> _gameZoneHandler;

    private final String WRITTEN_DATA = "{\"id\":\"id\",\"type\":\"type\",\"name\":\"name\",\"data\":\"VariableCache0\",\"onEntry\":[\"onEntryActionId\"],\"onExit\":[\"onExitActionId\"],\"maxX\":2,\"maxY\":2,\"tiles\":[[\"Tile0\",\"Tile1\",\"Tile2\"],[\"Tile3\",\"Tile4\",\"Tile5\"],[\"Tile6\",\"Tile7\",\"Tile8\"]]}";

    @BeforeEach
    void setUp() {
        ACTIONS.put(ON_ENTRY_ACTION_ID, ON_ENTRY_ACTION);
        ACTIONS.put(ON_EXIT_ACTION_ID, ON_EXIT_ACTION);
        _gameZoneHandler = new PersistentGameZoneHandler(GAME_ZONE_FACTORY, TILE_HANDLER,
                DATA_HANDLER, ACTIONS::get);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameZoneHandler(null, TILE_HANDLER,
                        DATA_HANDLER, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameZoneHandler(GAME_ZONE_FACTORY, null,
                        DATA_HANDLER, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameZoneHandler(GAME_ZONE_FACTORY, TILE_HANDLER,
                        null, ACTIONS::get));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentGameZoneHandler(GAME_ZONE_FACTORY, TILE_HANDLER,
                        DATA_HANDLER, null));
    }

    @Test
    void testWrite() {
        Tile[][] tiles = new Tile[3][3];
        for(int x = 0; x < tiles.length; x++) {
            for(int y = 0; y < tiles[0].length; y++){
                tiles[x][y] = new FakeTile();
            }
        }
        GameZone gameZone = new FakeGameZone(ID, TYPE, tiles, DATA);
        gameZone.setName(NAME);
        gameZone.onEntry().add(ON_ENTRY_ACTION);
        gameZone.onExit().add(ON_EXIT_ACTION);

        String writtenData = _gameZoneHandler.write(gameZone);

        assertEquals(WRITTEN_DATA, writtenData);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _gameZoneHandler.write(null));
    }

    @Test
    void testRead() {
        GameZone gameZone = _gameZoneHandler.read(WRITTEN_DATA);

        assertNotNull(gameZone);
        assertEquals(ID, GAME_ZONE_FACTORY._inputId);
        assertEquals(TYPE, GAME_ZONE_FACTORY._inputZoneType);
        assertEquals(NAME, gameZone.getName());
        int index = 0;
        for(int x = 0; x < GAME_ZONE_FACTORY._inputTiles.length; x++) {
            for(int y = 0; y < GAME_ZONE_FACTORY._inputTiles[0].length; y++) {
                assertEquals(TILE_HANDLER.READ_INPUTS.get(index), "Tile" + index);
                assertSame(TILE_HANDLER.READ_OUTPUTS.get(index++),
                        GAME_ZONE_FACTORY._inputTiles[x][y]);
            }
        }
        assertSame(DATA_HANDLER.READ_OUTPUTS.get(0), GAME_ZONE_FACTORY._inputData);
        assertEquals(1, gameZone.onEntry().size());
        assertTrue(gameZone.onEntry().contains(ON_ENTRY_ACTION));
        assertEquals(1, gameZone.onExit().size());
        assertTrue(gameZone.onExit().contains(ON_EXIT_ACTION));
    }
}
