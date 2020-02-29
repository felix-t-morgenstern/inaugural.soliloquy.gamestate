package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameZoneImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameZoneImplTests {
    private final String ID = "zoneId";
    private final String ZONE_TYPE = "zoneType";
    private final int MAX_X_COORDINATE = 1;
    private final int MAX_Y_COORDINATE = 2;
    private final Character CHARACTER = new CharacterStub();
    private final Tile[][] TILES = new Tile[MAX_X_COORDINATE+1][MAX_Y_COORDINATE+1];
    private final CoordinateFactory COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final VariableCache DATA = new VariableCacheStub();
    private final ArrayList<Character> ADDED_TO_END_OF_ROUND_MANAGER = new ArrayList<>();
    private final ArrayList<Character> REMOVED_FROM_ROUND_MANAGER = new ArrayList<>();

    private GameZone _gameZone;

    @BeforeEach
    void setUp() {
        for(int x = 0; x < TILES.length; x++) {
            for(int y = 0; y < TILES[0].length; y++) {
                TILES[x][y] = new TileStub(x, y, new VariableCacheStub());
            }
        }
        TILES[1][0].characters().add(CHARACTER);
        _gameZone = new GameZoneImpl(ID, ZONE_TYPE, TILES, COORDINATE_FACTORY,
                COLLECTION_FACTORY, DATA, ADDED_TO_END_OF_ROUND_MANAGER::add,
                REMOVED_FROM_ROUND_MANAGER::add);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(null, ZONE_TYPE,
                TILES, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl("", ZONE_TYPE,
                TILES, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, null,
                TILES, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, "",
                TILES, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                null, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        Tile[][] tilesWithZeroXIndex = new Tile[0][1];
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                tilesWithZeroXIndex, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        Tile[][] tilesWithZeroYIndex = new Tile[1][0];
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                tilesWithZeroYIndex, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        Tile[][] tilesWithNullEntry = new Tile[1][1];
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                tilesWithNullEntry, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        Tile[][] tilesWithAssignedTile = new Tile[1][1];
        tilesWithAssignedTile[0][0] = new TileStub(0, 0, new VariableCacheStub());
        tilesWithAssignedTile[0][0].assignGameZoneAfterAddedToGameZone(new GameZoneStub());
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                tilesWithAssignedTile, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        Tile[][] tilesWithMismatchedXCoordinate = new Tile[1][1];
        tilesWithMismatchedXCoordinate[0][0] = new TileStub(1, 0, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                tilesWithMismatchedXCoordinate, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        Tile[][] tilesWithMismatchedYCoordinate = new Tile[1][1];
        tilesWithMismatchedYCoordinate[0][0] = new TileStub(0, 1, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                tilesWithMismatchedYCoordinate, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                TILES, null, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                TILES, COORDINATE_FACTORY, null, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                TILES, COORDINATE_FACTORY, COLLECTION_FACTORY, null,
                ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                TILES, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                null, REMOVED_FROM_ROUND_MANAGER:: add));
        assertThrows(IllegalArgumentException.class, () -> new GameZoneImpl(ID, ZONE_TYPE,
                TILES, COORDINATE_FACTORY, COLLECTION_FACTORY, DATA,
                ADDED_TO_END_OF_ROUND_MANAGER::add, null));
    }

    @Test
    void testConstructorAssignsGameZoneToTile() {
        for(int x = 0; x <= MAX_X_COORDINATE; x++) {
            for(int y = 0; y <= MAX_Y_COORDINATE; y++) {
                assertSame(_gameZone, TILES[x][y].gameZone());
            }
        }
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameZone.class.getCanonicalName(), _gameZone.getInterfaceName());
    }

    @Test
    void testZoneType() {
        assertEquals(ZONE_TYPE, _gameZone.type());
    }

    @Test
    void testMaxCoordinates() {
        assertEquals(MAX_X_COORDINATE, _gameZone.maxCoordinates().getX());
        assertEquals(MAX_Y_COORDINATE, _gameZone.maxCoordinates().getY());
    }

    @Test
    void testTile() {
        for(int x = 0; x <= MAX_X_COORDINATE; x++) {
            for(int y = 0; y <= MAX_Y_COORDINATE; y++) {
                Tile tile = _gameZone.tile(x, y);
                assertNotNull(tile);
                assertSame(_gameZone, tile.gameZone());
                assertEquals(x, tile.location().getX());
                assertEquals(y, tile.location().getY());
            }
        }
    }

    @Test
    void testTileWithInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> _gameZone.tile(MAX_X_COORDINATE+1, 0));
        assertThrows(IllegalArgumentException.class, () -> _gameZone.tile(0, MAX_Y_COORDINATE+1));
        assertThrows(IllegalArgumentException.class, () -> _gameZone.tile(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> _gameZone.tile(0, -1));
    }

    @Test
    void testOnEntry() {
        assertNotNull(_gameZone.onEntry());
        assertNotSame(_gameZone.onEntry(), _gameZone.onExit());
        assertEquals(Action.class.getCanonicalName() + "<" + Void.class.getCanonicalName() + ">",
                _gameZone.onEntry().getArchetype().getInterfaceName());
    }

    @Test
    void testOnExit() {
        assertNotNull(_gameZone.onExit());
        assertNotSame(_gameZone.onEntry(), _gameZone.onExit());
        assertEquals(Action.class.getCanonicalName() + "<" + Void.class.getCanonicalName() + ">",
                _gameZone.onExit().getArchetype().getInterfaceName());
    }

    @Test
    void testId() {
        assertEquals(ID, _gameZone.id());
    }

    @Test
    void testGetName() {
        final String name = "name";

        _gameZone.setName(name);

        assertEquals(name, _gameZone.getName());
    }

    @Test
    void testSetName() {
        String newName = "newName";

        _gameZone.setName(newName);

        assertEquals(newName, _gameZone.getName());
    }

    @Test
    void testSetNullOrEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> _gameZone.setName(null));
        assertThrows(IllegalArgumentException.class, () -> _gameZone.setName(""));
    }

    @Test
    void testData() {
        assertSame(DATA, _gameZone.data());
    }

    @Test
    void testCharactersPresentInConstructorParamsAreAddedToGameZone() {
        ReadableCollection<Character> charactersInGameZone = _gameZone.charactersRepresentation();

        assertEquals(1, charactersInGameZone.size());
        assertTrue(charactersInGameZone.contains(CHARACTER));
    }

    @Test
    void testAddCharacterToGameZoneViaTileCharactersAndCharactersIteratorAndAddedToRoundManager() {
        Character character = new CharacterStub();

        _gameZone.tile(0,0).characters().add(character);

        ReadableCollection<Character> charactersInGameZone = _gameZone.charactersRepresentation();
        assertEquals(2, charactersInGameZone.size());
        assertTrue(charactersInGameZone.contains(CHARACTER));
        assertTrue(charactersInGameZone.contains(character));
        assertEquals(1, ADDED_TO_END_OF_ROUND_MANAGER.size());
        assertSame(character, ADDED_TO_END_OF_ROUND_MANAGER.get(0));
    }

    @Test
    void testRemoveCharacterFromGameZoneViaTileCharactersAndRemovedFromRoundManager() {
        _gameZone.tile(0,0).characters().remove(CHARACTER);

        assertEquals(0, _gameZone.charactersRepresentation().size());
        assertEquals(1, REMOVED_FROM_ROUND_MANAGER.size());
        assertSame(CHARACTER, REMOVED_FROM_ROUND_MANAGER.get(0));
    }

    @Test
    void testDelete() {
        HashSet<Tile> tiles = new HashSet<>();
        for(int x = 0; x <= MAX_X_COORDINATE; x++) {
            for(int y = 0; y <= MAX_Y_COORDINATE; y++) {
                tiles.add(_gameZone.tile(x,y));
            }
        }

        _gameZone.delete();

        assertTrue(_gameZone.isDeleted());
        tiles.forEach(tile -> assertTrue(tile.isDeleted()));
    }
}
