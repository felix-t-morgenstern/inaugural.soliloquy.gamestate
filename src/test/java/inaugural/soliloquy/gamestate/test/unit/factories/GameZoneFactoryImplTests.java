package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.GameZoneFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameZone;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameZoneFactoryImplTests {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") private final ArrayList<Character>
            ADDED_TO_END_OF_ROUND_MANAGER = new ArrayList<>();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") private final ArrayList<Character>
            REMOVED_FROM_ROUND_MANAGER = new ArrayList<>();
    private final String ID = "GameZoneId";
    private final String TYPE = "GameZoneType";
    private final Tile[][] TILES = new Tile[1][2];
    private final VariableCache DATA = new VariableCacheStub();

    private GameZoneFactory _gameZoneFactory;

    @BeforeEach
    void setUp() {
        _gameZoneFactory = new GameZoneFactoryImpl(ADDED_TO_END_OF_ROUND_MANAGER::add,
                REMOVED_FROM_ROUND_MANAGER::add);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(null, REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(ADDED_TO_END_OF_ROUND_MANAGER::add, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameZoneFactory.class.getCanonicalName(),
                _gameZoneFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        TILES[0][0] = new FakeTile(0, 0, new VariableCacheStub());
        TILES[0][1] = new FakeTile(0, 1, new VariableCacheStub());

        GameZone gameZone = _gameZoneFactory.make(ID, TYPE, TILES, DATA);

        assertEquals(ID, gameZone.id());
        assertEquals(TYPE, gameZone.type());
        assertEquals(0, gameZone.maxCoordinates().x());
        assertEquals(1, gameZone.maxCoordinates().y());
        assertSame(TILES[0][0], gameZone.tile(Coordinate.of(0, 0)));
        assertSame(TILES[0][1], gameZone.tile(Coordinate.of(0, 1)));
        assertSame(DATA, gameZone.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(null, TYPE, TILES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make("", TYPE, TILES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, null, TILES,
                DATA));
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, "", TILES,
                DATA));
        Tile[][] tilesWithZeroXIndex = new Tile[0][1];
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, TYPE,
                tilesWithZeroXIndex, DATA));
        Tile[][] tilesWithZeroYIndex = new Tile[1][0];
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, TYPE,
                tilesWithZeroYIndex, DATA));
        Tile[][] tilesWithNullEntry = new Tile[1][1];
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, TYPE,
                tilesWithNullEntry, DATA));
        Tile[][] tilesWithAssignedTile = new Tile[1][1];
        tilesWithAssignedTile[0][0] = new FakeTile(0, 0, new VariableCacheStub());
        tilesWithAssignedTile[0][0].assignGameZoneAfterAddedToGameZone(new FakeGameZone());
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, TYPE,
                tilesWithAssignedTile, DATA));
        Tile[][] tilesWithMismatchedXCoordinate = new Tile[1][1];
        tilesWithMismatchedXCoordinate[0][0] = new FakeTile(1, 0, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, TYPE,
                tilesWithMismatchedXCoordinate, DATA));
        Tile[][] tilesWithMismatchedYCoordinate = new Tile[1][1];
        tilesWithMismatchedYCoordinate[0][0] = new FakeTile(0, 1, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, TYPE,
                tilesWithMismatchedYCoordinate, DATA));
        assertThrows(IllegalArgumentException.class, () -> _gameZoneFactory.make(ID, TYPE, TILES,
                null));
    }
}
