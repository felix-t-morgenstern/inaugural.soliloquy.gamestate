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
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
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
    private final int MAX_X_Coordinate2d = randomIntInRange(10, 100);
    private final int MAX_Y_Coordinate2d = randomIntInRange(10, 100);
    private final UUID CHARACTER_1_UUID = UUID.randomUUID();
    private final UUID CHARACTER_2_UUID = UUID.randomUUID();
    private final Tile[][] TILES = new Tile[MAX_X_Coordinate2d + 1][MAX_Y_Coordinate2d + 1];
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
        var tilesWithMismatchedXCoordinate2d = new Tile[1][1];
        tilesWithMismatchedXCoordinate2d[0][0] = new FakeTile(1, 0, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, tilesWithMismatchedXCoordinate2d, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add));
        var tilesWithMismatchedYCoordinate2d = new Tile[1][1];
        tilesWithMismatchedYCoordinate2d[0][0] = new FakeTile(0, 1, new VariableCacheStub());
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, ZONE_TYPE, tilesWithMismatchedYCoordinate2d, DATA,
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
        for (var x = 0; x <= MAX_X_Coordinate2d; x++) {
            for (var y = 0; y <= MAX_Y_Coordinate2d; y++) {
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
    public void testmaxCoordinates() {
        assertEquals(MAX_X_Coordinate2d, gameZone.maxCoordinates().X);
        assertEquals(MAX_Y_Coordinate2d, gameZone.maxCoordinates().Y);
    }

    @Test
    public void testTile() {
        for (var x = 0; x <= MAX_X_Coordinate2d; x++) {
            for (var y = 0; y <= MAX_Y_Coordinate2d; y++) {
                var tile = gameZone.tile(Coordinate2d.of(x, y));
                assertNotNull(tile);
                assertSame(gameZone, tile.gameZone());
                assertEquals(x, tile.location().X);
                assertEquals(y, tile.location().Y);
            }
        }
    }

    @Test
    public void testTileWithInvalidCoordinate2ds() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tile(Coordinate2d.of(MAX_X_Coordinate2d + 1, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tile(Coordinate2d.of(0, MAX_Y_Coordinate2d + 1)));
        assertThrows(IllegalArgumentException.class, () -> gameZone.tile(Coordinate2d.of(-1, 0)));
        assertThrows(IllegalArgumentException.class, () -> gameZone.tile(Coordinate2d.of(0, -1)));
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
        var mockSegment1Location = validCoordinate3d(MOCK_SEGMENT_1_Z);

        when(mockSegmentType2.direction()).thenReturn(WallSegmentDirection.NORTHWEST);
        when(mockSegment2.getType()).thenReturn(mockSegmentType2);
        var mockSegment2And3TileLocation = validCoordinate2d();
        var mockSegment2Location =
                Coordinate3d.of(mockSegment2And3TileLocation.X, mockSegment2And3TileLocation.Y,
                        MOCK_SEGMENT_2_Z);

        when(mockSegmentType3.direction()).thenReturn(WallSegmentDirection.WEST);
        when(mockSegment3.getType()).thenReturn(mockSegmentType3);
        var mockSegment3Location =
                Coordinate3d.of(mockSegment2And3TileLocation.X, mockSegment2And3TileLocation.Y,
                        MOCK_SEGMENT_3_Z);

        gameZone.setSegment(mockSegment1Location, mockSegment1);
        gameZone.setSegment(mockSegment2Location, mockSegment2);
        gameZone.setSegment(mockSegment3Location, mockSegment3);

        assertEquals(mapOf(Pair.of(MOCK_SEGMENT_1_Z, mockSegment1)),
                gameZone.getSegments(mockSegment1Location.to2d(), WallSegmentDirection.NORTH));
        assertEquals(mapOf(Pair.of(MOCK_SEGMENT_2_Z, mockSegment2)),
                gameZone.getSegments(mockSegment2And3TileLocation, WallSegmentDirection.NORTHWEST));
        assertEquals(mapOf(Pair.of(MOCK_SEGMENT_3_Z, mockSegment3)),
                gameZone.getSegments(mockSegment2And3TileLocation, WallSegmentDirection.WEST));
        assertEquals(mapOf(),
                gameZone.getSegments(validCoordinate2d(), WallSegmentDirection.NORTH));
    }

    @Test
    public void testSetSegmentOverridesPrevious() {
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        when(mockSegment2.getType()).thenReturn(mockSegmentType1);
        var location = validCoordinate3d(randomInt());

        gameZone.setSegment(location, mockSegment1);
        gameZone.setSegment(location, mockSegment2);
        var segmentsAtLocation = gameZone.getSegments(location.to2d(), WallSegmentDirection.NORTH);

        assertNotNull(segmentsAtLocation);
        assertEquals(1, segmentsAtLocation.size());
        assertTrue(segmentsAtLocation.containsKey(location.Z));
        assertSame(mockSegment2, segmentsAtLocation.get(location.Z));
    }

    @Test
    public void testGetSegmentsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(null, WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(Coordinate2d.of(-1, 0), WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(Coordinate2d.of(0, -1), WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(Coordinate2d.of(MAX_X_Coordinate2d + 2, 0),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(Coordinate2d.of(0, MAX_Y_Coordinate2d + 2),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.getSegments(validCoordinate2d(), null));
    }

    @Test
    public void testSetSegmentWithInvalidParams() {
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(Coordinate3d.of(-1, 0, randomInt()), mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(Coordinate3d.of(0, -1, randomInt()), mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(Coordinate3d.of(MAX_X_Coordinate2d + 2, 0, randomInt()),
                        mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(Coordinate3d.of(0, MAX_Y_Coordinate2d + 2, randomInt()),
                        mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(validCoordinate3d(), null));
        when(mockSegment1.getType()).thenReturn(null);
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(validCoordinate3d(), mockSegment1));
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        when(mockSegmentType1.direction()).thenReturn(null);
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.setSegment(validCoordinate3d(), mockSegment1));
    }

    @Test
    public void testRemoveSegment() {
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        var location = validCoordinate3d();

        assertFalse(gameZone.removeSegment(location, WallSegmentDirection.NORTH));

        gameZone.setSegment(location, mockSegment1);
        var wasPresent = gameZone.removeSegment(location, WallSegmentDirection.NORTH);
        var segmentsAtLocation = gameZone.getSegments(location.to2d(), WallSegmentDirection.NORTH);

        assertTrue(wasPresent);
        assertFalse(gameZone.removeSegment(location, WallSegmentDirection.NORTH));
        assertNotNull(segmentsAtLocation);
        assertEquals(0, segmentsAtLocation.size());
    }

    @Test
    public void testRemoveSegmentWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(null, WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(Coordinate3d.of(-1, 0, randomInt()),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(Coordinate3d.of(0, -1, randomInt()),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(
                        Coordinate3d.of(MAX_X_Coordinate2d + 2, 0, randomInt()),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(
                        Coordinate3d.of(0, MAX_Y_Coordinate2d + 2, randomInt()),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(validCoordinate3d(), null));
    }

    @Test
    public void testRemoveAllSegments() {
        when(mockSegmentType1.direction()).thenReturn(WallSegmentDirection.NORTH);
        when(mockSegment1.getType()).thenReturn(mockSegmentType1);
        when(mockSegment2.getType()).thenReturn(mockSegmentType1);
        when(mockSegment3.getType()).thenReturn(mockSegmentType1);
        var location = validCoordinate3d();
        gameZone.setSegment(location, mockSegment1);
        gameZone.setSegment(location, mockSegment2);
        gameZone.setSegment(location, mockSegment3);

        gameZone.removeAllSegments(location.to2d(), WallSegmentDirection.NORTH);
        var segmentsAfterRemoveAll =
                gameZone.getSegments(location.to2d(), WallSegmentDirection.NORTH);

        assertNotNull(segmentsAfterRemoveAll);
        assertEquals(0, segmentsAfterRemoveAll.size());
    }

    @Test
    public void testRemoveAllSegmentsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(null, WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate2d.of(-1, 0),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate2d.of(0, -1),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate2d.of(MAX_X_Coordinate2d + 2, 0),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate2d.of(0, MAX_Y_Coordinate2d + 2),
                        WallSegmentDirection.NORTH));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(validCoordinate2d(), null));
    }

    private Coordinate2d validCoordinate2d() {
        return validCoordinate3d(randomInt()).to2d();
    }

    private Coordinate3d validCoordinate3d() {
        return validCoordinate3d(randomInt());
    }

    private Coordinate3d validCoordinate3d(int z) {
        return Coordinate3d.of(randomIntInRange(0, MAX_X_Coordinate2d),
                randomIntInRange(0, MAX_Y_Coordinate2d), z);
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
        gameZone.tile(Coordinate2d.of(0, 0)).characters().add(mockCharacter2);

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
        gameZone.tile(Coordinate2d.of(0, 0)).characters().remove(mockCharacter1);

        assertEquals(0, gameZone.charactersRepresentation().size());
        assertEquals(1, REMOVED_FROM_ROUND_MANAGER.size());
        assertSame(mockCharacter1, REMOVED_FROM_ROUND_MANAGER.get(0));
    }

    @Test
    public void testDelete() {
        var tiles = new HashSet<Tile>();
        for (var x = 0; x <= MAX_X_Coordinate2d; x++) {
            for (var y = 0; y <= MAX_Y_Coordinate2d; y++) {
                tiles.add(gameZone.tile(Coordinate2d.of(x, y)));
            }
        }

        gameZone.delete();

        assertTrue(gameZone.isDeleted());
        tiles.forEach(tile -> assertTrue(tile.isDeleted()));
    }
}
