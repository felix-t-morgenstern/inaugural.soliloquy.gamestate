package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.GameZoneImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameZone;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameZoneImplTests {
    private final String ID = randomString();
    private final String ZONE_TYPE = randomString();
    // These ranges are chosen arbitrarily to make the tests meaningful but not burdensome
    private final int MAX_X_COORDINATE = randomIntInRange(10, 100);
    private final int MAX_Y_COORDINATE = randomIntInRange(10, 100);
    private final UUID CHARACTER_1_UUID = UUID.randomUUID();
    private final UUID CHARACTER_2_UUID = UUID.randomUUID();
    private final Tile[][] TILES = new Tile[MAX_X_COORDINATE + 1][MAX_Y_COORDINATE + 1];
    private final VariableCache DATA = new VariableCacheStub();
    private final List<Character> ADDED_TO_END_OF_ROUND_MANAGER = listOf();
    private final List<Character> REMOVED_FROM_ROUND_MANAGER = listOf();
    private final int MOCK_SEGMENT_1_Z = randomInt();
    private final int MOCK_SEGMENT_2_Z = randomInt();
    private final int MOCK_SEGMENT_3_Z = randomInt();

    @Mock private Character mockCharacter1;
    @Mock private Character mockCharacter2;

    @Mock private WallSegmentType mockSegmentType1;
    @Mock private WallSegmentType mockSegmentType2;
    @Mock private WallSegmentType mockSegmentType3;
    @Mock private WallSegment mockSegment1;
    @Mock private WallSegment mockSegment2;
    @Mock private WallSegment mockSegment3;

    private GameZone gameZone;

    @Before
    public void setUp() {
        when(mockCharacter1.uuid()).thenReturn(CHARACTER_1_UUID);
        when(mockCharacter2.uuid()).thenReturn(CHARACTER_2_UUID);

        for (var x = 0; x < TILES.length; x++) {
            for (var y = 0; y < TILES[0].length; y++) {
                TILES[x][y] = new FakeTile(x, y, new VariableCacheStub());
            }
        }
        TILES[1][0].characters().add(mockCharacter1);
        gameZone = new GameZoneImpl(ID, ZONE_TYPE, TILES, DATA, ADDED_TO_END_OF_ROUND_MANAGER::add,
                REMOVED_FROM_ROUND_MANAGER::add);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(null, ZONE_TYPE, TILES, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl("", ZONE_TYPE, TILES, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, null, TILES, DATA, ADDED_TO_END_OF_ROUND_MANAGER::add,
                        REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, "", TILES, DATA, ADDED_TO_END_OF_ROUND_MANAGER::add,
                        REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, null, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        var tilesWithZeroXIndex = new Tile[0][1];
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, tilesWithZeroXIndex, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        var tilesWithZeroYIndex = new Tile[1][0];
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, tilesWithZeroYIndex, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        var tilesWithNullEntry = new Tile[1][1];
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, tilesWithNullEntry, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        var tilesWithAssignedTile = new Tile[1][1];
        tilesWithAssignedTile[0][0] = new FakeTile(0, 0, new VariableCacheStub());
        tilesWithAssignedTile[0][0].assignGameZoneAfterAddedToGameZone(new FakeGameZone());
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, tilesWithAssignedTile, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        var tilesWithMismatchedXCoordinate = new Tile[1][1];
        tilesWithMismatchedXCoordinate[0][0] = new FakeTile(1, 0, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, tilesWithMismatchedXCoordinate, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        var tilesWithMismatchedYCoordinate = new Tile[1][1];
        tilesWithMismatchedYCoordinate[0][0] = new FakeTile(0, 1, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, tilesWithMismatchedYCoordinate, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, TILES, null,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, TILES, DATA, null,
                        REMOVED_FROM_ROUND_MANAGER::add));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, TILES, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, null));
    }

    @Test
    public void testConstructorAssignsGameZoneToTile() {
        for (var x = 0; x <= MAX_X_COORDINATE; x++) {
            for (var y = 0; y <= MAX_Y_COORDINATE; y++) {
                assertSame(gameZone, TILES[x][y].gameZone());
            }
        }
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(GameZone.class.getCanonicalName(), gameZone.getInterfaceName());
    }

    @Test
    public void testZoneType() {
        assertEquals(ZONE_TYPE, gameZone.type());
    }

    @Test
    public void testMaxCoordinates() {
        assertEquals(MAX_X_COORDINATE, gameZone.maxCoordinates().x());
        assertEquals(MAX_Y_COORDINATE, gameZone.maxCoordinates().y());
    }

    @Test
    public void testTile() {
        for (var x = 0; x <= MAX_X_COORDINATE; x++) {
            for (var y = 0; y <= MAX_Y_COORDINATE; y++) {
                var tile = gameZone.tile(Coordinate.of(x, y));
                assertNotNull(tile);
                assertSame(gameZone, tile.gameZone());
                assertEquals(x, tile.location().x());
                assertEquals(y, tile.location().y());
            }
        }
    }

    @Test
    public void testTileWithInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tile(Coordinate.of(MAX_X_COORDINATE + 1, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tile(Coordinate.of(0, MAX_Y_COORDINATE + 1)));
        assertThrows(IllegalArgumentException.class, () -> gameZone.tile(Coordinate.of(-1, 0)));
        assertThrows(IllegalArgumentException.class, () -> gameZone.tile(Coordinate.of(0, -1)));
    }

    @Test
    public void testOnEntry() {
        assertNotNull(gameZone.onEntry());
        assertSame(gameZone.onEntry(), gameZone.onEntry());
    }

    @Test
    public void testOnExit() {
        assertNotNull(gameZone.onExit());
        assertSame(gameZone.onExit(), gameZone.onExit());
    }

    @Test
    public void testId() {
        assertEquals(ID, gameZone.id());
    }

    @Test
    public void testGetName() {
        var name = randomString();

        gameZone.setName(name);

        assertEquals(name, gameZone.getName());
    }

    @Test
    public void testSetName() {
        var newName = randomString();

        gameZone.setName(newName);

        assertEquals(newName, gameZone.getName());
    }

    @Test
    public void testSetNullOrEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> gameZone.setName(null));
        assertThrows(IllegalArgumentException.class, () -> gameZone.setName(""));
    }

    @Test
    public void testSetAndGetSegment() {
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        var mockSegment1Location = validCoordinate();

        when(mockSegmentType2.direction()).thenReturn(WallSegmentDirection.NORTHWEST);
        when(mockSegment2.getType()).thenReturn(mockSegmentType2);
        var mockSegment2And3Location = validCoordinate();

        when(mockSegmentType3.direction()).thenReturn(WallSegmentDirection.WEST);
        when(mockSegment3.getType()).thenReturn(mockSegmentType3);

        gameZone.setSegment(mockSegment1Location, MOCK_SEGMENT_1_Z, mockSegment1);
        gameZone.setSegment(mockSegment2And3Location, MOCK_SEGMENT_2_Z, mockSegment2);
        gameZone.setSegment(mockSegment2And3Location, MOCK_SEGMENT_3_Z, mockSegment3);

        assertEquals(mapOf(Pair.of(MOCK_SEGMENT_1_Z, mockSegment1)),
                gameZone.getSegments(mockSegment1Location, WallSegmentDirection.NORTH));
        assertEquals(mapOf(Pair.of(MOCK_SEGMENT_2_Z, mockSegment2)),
                gameZone.getSegments(mockSegment2And3Location, WallSegmentDirection.NORTHWEST));
        assertEquals(mapOf(Pair.of(MOCK_SEGMENT_3_Z, mockSegment3)),
                gameZone.getSegments(mockSegment2And3Location, WallSegmentDirection.WEST));
        assertEquals(mapOf(), gameZone.getSegments(validCoordinate(), WallSegmentDirection.NORTH));
    }

    @Test
    public void testSetSegmentOverridesPrevious() {
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        when(mockSegment2.getType()).thenReturn(mockSegmentType1);
        var location = validCoordinate();
        var z = randomInt();

        gameZone.setSegment(location, z, mockSegment1);
        gameZone.setSegment(location, z, mockSegment2);
        var segmentsAtLocation = gameZone.getSegments(location, WallSegmentDirection.NORTH);

        assertNotNull(segmentsAtLocation);
        assertEquals(1, segmentsAtLocation.size());
        assertTrue(segmentsAtLocation.containsKey(z));
        assertSame(mockSegment2, segmentsAtLocation.get(z));
    }

    @Test
    public void testGetSegmentsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(null, WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(Coordinate.of(-1, 0), WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(Coordinate.of(0, -1), WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(Coordinate.of(MAX_X_COORDINATE + 2, 0),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(Coordinate.of(0, MAX_Y_COORDINATE + 2),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(validCoordinate(), null));
    }

    @Test
    public void testSetSegmentWithInvalidParams() {
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(null, randomInt(), mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(Coordinate.of(-1, 0), randomInt(), mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(Coordinate.of(0, -1), randomInt(), mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(Coordinate.of(MAX_X_COORDINATE + 2, 0), randomInt(),
                        mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(Coordinate.of(0, MAX_Y_COORDINATE + 2), randomInt(),
                        mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(validCoordinate(), randomInt(), null));
        when(mockSegment1.getType()).thenReturn(null);
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(validCoordinate(), randomInt(), mockSegment1));
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        when(mockSegmentType1.direction()).thenReturn(null);
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(validCoordinate(), randomInt(), mockSegment1));
    }

    @Test
    public void testRemoveSegment() {
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        var location = validCoordinate();
        var z = randomInt();

        assertFalse(gameZone.removeSegment(location, z, WallSegmentDirection.NORTH));

        gameZone.setSegment(location, z, mockSegment1);
        var wasPresent = gameZone.removeSegment(location, z, WallSegmentDirection.NORTH);
        var segmentsAtLocation = gameZone.getSegments(location, WallSegmentDirection.NORTH);

        assertTrue(wasPresent);
        assertFalse(gameZone.removeSegment(location, z, WallSegmentDirection.NORTH));
        assertNotNull(segmentsAtLocation);
        assertEquals(0, segmentsAtLocation.size());
    }

    @Test
    public void testRemoveSegmentWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(null, randomInt(), WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(Coordinate.of(-1, 0), randomInt(),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(Coordinate.of(0, -1), randomInt(),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(Coordinate.of(MAX_X_COORDINATE + 2, 0), randomInt(),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(Coordinate.of(0, MAX_Y_COORDINATE + 2), randomInt(),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(validCoordinate(), randomInt(), null));
    }

    @Test
    public void testRemoveAllSegments() {
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        when(mockSegment2.getType()).thenReturn(mockSegmentType1);
        when(mockSegment3.getType()).thenReturn(mockSegmentType1);
        var location = validCoordinate();
        gameZone.setSegment(location, randomInt(), mockSegment1);
        gameZone.setSegment(location, randomInt(), mockSegment2);
        gameZone.setSegment(location, randomInt(), mockSegment3);

        gameZone.removeAllSegments(location, WallSegmentDirection.NORTH);
        var segmentsAfterRemoveAll = gameZone.getSegments(location, WallSegmentDirection.NORTH);

        assertNotNull(segmentsAfterRemoveAll);
        assertEquals(0, segmentsAfterRemoveAll.size());
    }

    @Test
    public void testRemoveAllSegmentsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(null, WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate.of(-1, 0), WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate.of(0, -1), WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate.of(MAX_X_COORDINATE + 2, 0),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate.of(0, MAX_Y_COORDINATE + 2),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(validCoordinate(), null));
    }

    private Coordinate validCoordinate() {
        return Coordinate.of(randomIntInRange(0, MAX_X_COORDINATE),
                randomIntInRange(0, MAX_Y_COORDINATE));
    }

    @Test
    public void testData() {
        assertSame(DATA, gameZone.data());
    }

    @Test
    public void testCharactersPresentInConstructorParamsAreAddedToGameZone() {
        var charactersInGameZone = gameZone.charactersRepresentation();

        assertEquals(1, charactersInGameZone.size());
        assertTrue(charactersInGameZone.containsValue(mockCharacter1));
    }

    @Test
    public void testAddCharacterToGameZoneViaTileCharactersAndCharactersIteratorAndAddedToRoundManager() {
        gameZone.tile(Coordinate.of(0, 0)).characters().add(mockCharacter2);

        var charactersInGameZone = gameZone.charactersRepresentation();
        assertEquals(2, charactersInGameZone.size());
        assertTrue(charactersInGameZone.containsValue(mockCharacter1));
        assertTrue(charactersInGameZone.containsValue(mockCharacter2));
        assertEquals(1, ADDED_TO_END_OF_ROUND_MANAGER.size());
        assertSame(mockCharacter2, ADDED_TO_END_OF_ROUND_MANAGER.get(0));
        assertNotSame(gameZone.charactersRepresentation(), charactersInGameZone);
    }

    @Test
    public void testRemoveCharacterFromGameZoneViaTileCharactersAndRemovedFromRoundManager() {
        gameZone.tile(Coordinate.of(0, 0)).characters().remove(mockCharacter1);

        assertEquals(0, gameZone.charactersRepresentation().size());
        assertEquals(1, REMOVED_FROM_ROUND_MANAGER.size());
        assertSame(mockCharacter1, REMOVED_FROM_ROUND_MANAGER.get(0));
    }

    @Test
    public void testDelete() {
        var tiles = new HashSet<Tile>();
        for (var x = 0; x <= MAX_X_COORDINATE; x++) {
            for (var y = 0; y <= MAX_Y_COORDINATE; y++) {
                tiles.add(gameZone.tile(Coordinate.of(x, y)));
            }
        }

        gameZone.delete();

        assertTrue(gameZone.isDeleted());
        tiles.forEach(tile -> assertTrue(tile.isDeleted()));
    }
}
