package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.GameZoneImpl;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.apache.commons.lang3.function.TriConsumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.shared.GameZoneTerrain;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.HashSet;
import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.valueobjects.Coordinate3d.addOffsets3d;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;
import static soliloquy.specs.gamestate.entities.WallSegmentOrientation.*;

@RunWith(MockitoJUnitRunner.class)
public class GameZoneImplTests {
    private final String ID = randomString();
    // These ranges are chosen arbitrarily to make the tests meaningful but not burdensome; as
    // other tests state, the max must merely be positive
    private final Coordinate2d MAX_COORDINATES =
            Coordinate2d.of(randomIntInRange(10, 100), randomIntInRange(10, 100));
    private final Coordinate3d TILE_LOCATION =
            Coordinate3d.of(randomIntInRange(0, MAX_COORDINATES.X),
                    randomIntInRange(0, MAX_COORDINATES.Y), randomInt());
    private final Coordinate3d SEGMENT_LOCATION = Coordinate3d.of(MAX_COORDINATES.X,
            MAX_COORDINATES.Y, randomInt());
    private final VariableCache DATA = new VariableCacheStub();
    private final List<Character> ADDED_TO_END_OF_ROUND_MANAGER = listOf();
    private final List<Character> REMOVED_FROM_ROUND_MANAGER = listOf();

    @Mock private TriConsumer<GameZoneTerrain, GameZone, Coordinate3d>
            mockAssignLocationAfterPlacement;
    @Mock private Tile mockTile;
    @Mock private WallSegmentType mockSegmentTypeHorizontal;
    @Mock private WallSegmentType mockSegmentTypeVertical;
    @Mock private WallSegment mockSegment1;
    @Mock private WallSegment mockSegment2;
    @Mock private WallSegment mockSegment3;

    private GameZone gameZone;

    @Before
    public void setUp() {
        when(mockSegmentTypeHorizontal.orientation()).thenReturn(HORIZONTAL);
        when(mockSegmentTypeVertical.orientation()).thenReturn(VERTICAL);

        when(mockSegment1.getType()).thenReturn(mockSegmentTypeVertical);
        when(mockSegmentTypeVertical.orientation()).thenReturn(VERTICAL);

        gameZone = new GameZoneImpl(ID, MAX_COORDINATES, DATA, ADDED_TO_END_OF_ROUND_MANAGER::add,
                REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(null, MAX_COORDINATES, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl("", MAX_COORDINATES, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, null, DATA, ADDED_TO_END_OF_ROUND_MANAGER::add,
                        REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, Coordinate2d.of(0, randomIntWithInclusiveFloor(1)), DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, Coordinate2d.of(randomIntWithInclusiveFloor(1), 0), DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, MAX_COORDINATES, null,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, MAX_COORDINATES, DATA, null,
                        REMOVED_FROM_ROUND_MANAGER::add, mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, MAX_COORDINATES, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, null, mockAssignLocationAfterPlacement));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, MAX_COORDINATES, DATA,
                        ADDED_TO_END_OF_ROUND_MANAGER::add, REMOVED_FROM_ROUND_MANAGER::add, null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(GameZone.class.getCanonicalName(), gameZone.getInterfaceName());
    }

    @Test
    public void testMaxCoordinates() {
        assertEquals(MAX_COORDINATES, gameZone.maxCoordinates());
    }

    @Test
    public void testAddAndGetTile() {
        gameZone.putTile(mockTile, TILE_LOCATION);

        var tileAtLoc = gameZone.tile(TILE_LOCATION);

        assertNotNull(tileAtLoc);
        assertSame(mockTile, tileAtLoc);
        verify(mockAssignLocationAfterPlacement)
                .accept(same(mockTile), same(gameZone), eq(TILE_LOCATION));
    }

    @Test
    public void testAddTileReplacesAndReturnsPrev() {
        gameZone.putTile(mockTile, TILE_LOCATION);
        var newMockTile = mock(Tile.class);

        var prev = gameZone.putTile(newMockTile, TILE_LOCATION);

        assertNotNull(prev);
        assertSame(mockTile, prev);
    }

    @Test
    public void testTilesAtEmptyCoord2d() {
        var tiles = gameZone.tiles(TILE_LOCATION.to2d());

        assertNotNull(tiles);
        assertTrue(tiles.isEmpty());
    }

    @Test
    public void testTilesAtCoord2d() {
        var mockTile2 = mock(Tile.class);
        var mockTile2Location =
                Coordinate3d.of(TILE_LOCATION.X, TILE_LOCATION.Y, TILE_LOCATION.Z + 1);
        var mockTile3 = mock(Tile.class);
        var mockTile3Location =
                Coordinate3d.of(TILE_LOCATION.X, TILE_LOCATION.Y, TILE_LOCATION.Z + 2);

        gameZone.putTile(mockTile, TILE_LOCATION);
        gameZone.putTile(mockTile2, mockTile2Location);
        gameZone.putTile(mockTile3, mockTile3Location);
        var tiles = gameZone.tiles(TILE_LOCATION.to2d());

        assertNotNull(tiles);
    }

    @Test
    public void testTilesAtCoord2dWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> gameZone.tiles(null));
        assertThrows(IllegalArgumentException.class, () -> gameZone.tiles(Coordinate2d.of(-1, 0)));
        assertThrows(IllegalArgumentException.class, () -> gameZone.tiles(Coordinate2d.of(0, -1)));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tiles(Coordinate2d.of(MAX_COORDINATES.X + 1, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tiles(Coordinate2d.of(0, MAX_COORDINATES.Y + 1)));
    }

    @Test
    public void testAddTileWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> gameZone.putTile(null, TILE_LOCATION));
        assertThrows(IllegalArgumentException.class, () -> gameZone.putTile(mockTile, null));
        assertThrows(IllegalArgumentException.class, () -> gameZone.putTile(mockTile,
                Coordinate3d.of(-1, randomIntInRange(0, MAX_COORDINATES.Y), randomInt())));
        assertThrows(IllegalArgumentException.class, () -> gameZone.putTile(mockTile,
                Coordinate3d.of(randomIntInRange(0, MAX_COORDINATES.X), -1, randomInt())));
        assertThrows(IllegalArgumentException.class, () -> gameZone.putTile(mockTile,
                Coordinate3d.of(MAX_COORDINATES.X + 1, randomIntInRange(0, MAX_COORDINATES.Y),
                        randomInt())));
        assertThrows(IllegalArgumentException.class, () -> gameZone.putTile(mockTile,
                Coordinate3d.of(randomIntInRange(0, MAX_COORDINATES.X), MAX_COORDINATES.Y + 1,
                        randomInt())));
    }

    @Test
    public void testTileWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tile(Coordinate3d.of(MAX_COORDINATES.X + 1, 0, randomInt())));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tile(Coordinate3d.of(0, MAX_COORDINATES.Y + 1, randomInt())));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tile(Coordinate3d.of(-1, 0, randomInt())));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.tile(Coordinate3d.of(0, -1, randomInt())));
    }

    @Test
    public void testTiles() {
        var mockTile2 = mock(Tile.class);
        var mockTile3 = mock(Tile.class);
        var tileLocation2 = Coordinate3d.of(randomIntInRange(0, MAX_COORDINATES.X),
                randomIntInRange(0, MAX_COORDINATES.Y), randomInt());
        var tileLocation3 = Coordinate3d.of(randomIntInRange(0, MAX_COORDINATES.X),
                randomIntInRange(0, MAX_COORDINATES.Y), randomInt());
        gameZone.putTile(mockTile, TILE_LOCATION);
        gameZone.putTile(mockTile2, tileLocation2);
        gameZone.putTile(mockTile3, tileLocation3);

        var allTiles = gameZone.tiles();

        assertNotNull(allTiles);
        assertEquals(3, allTiles.size());
        assertTrue(allTiles.contains(mockTile));
        assertTrue(allTiles.contains(mockTile2));
        assertTrue(allTiles.contains(mockTile3));
    }

    @Test
    public void testRemoveTileReturnsNullWhenNoTilePresent() {
        var tileAtLoc = gameZone.removeTile(TILE_LOCATION);

        assertNull(tileAtLoc);
    }

    @Test
    public void testRemoveTileReturnsTile() {
        gameZone.putTile(mockTile, TILE_LOCATION);

        var tileAtLoc = gameZone.removeTile(TILE_LOCATION);

        assertSame(mockTile, tileAtLoc);
    }

    @Test
    public void testRemoveTileRemovesTile() {
        gameZone.putTile(mockTile, TILE_LOCATION);
        gameZone.removeTile(TILE_LOCATION);

        var tileAtLoc = gameZone.removeTile(TILE_LOCATION);

        assertNull(tileAtLoc);
    }

    @Test
    public void testRemoveTilesReturnsEmptyMapWhenNoTilesPresent() {
        assertTrue(gameZone.tiles(TILE_LOCATION.to2d()).isEmpty());
    }

    @Test
    public void testRemoveTileUnassignsLocationAfterPlacement() {

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
    public void testSetAndGetName() {
        var name = randomString();

        gameZone.setName(name);

        assertEquals(name, gameZone.getName());
    }

    @Test
    public void testSetNullOrEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> gameZone.setName(null));
        assertThrows(IllegalArgumentException.class, () -> gameZone.setName(""));
    }

    @Test
    public void testPutAndGetSegments() {
        makeAndAddMockSegment(CORNER, TILE_LOCATION, gameZone);

        var returned = gameZone.segments(TILE_LOCATION.to2d(), CORNER);

        assertNotNull(returned);
    }

    @Test
    public void testGetSegmentsWhenNotPresent() {
        var returned = gameZone.segments(TILE_LOCATION.to2d(), CORNER);

        assertTrue(returned.isEmpty());
    }

    @Test
    public void testPutAndGetSegmentsAtTileLocation() {
        var locNeAndE = addOffsets3d(TILE_LOCATION, 1, 0, 0);
        var locSwAndS = addOffsets3d(TILE_LOCATION, 0, 1, 0);
        var locSe = addOffsets3d(TILE_LOCATION, 1, 1, 0);
        var segmentNW = makeAndAddMockSegment(CORNER, TILE_LOCATION, gameZone);
        var segmentN = makeAndAddMockSegment(HORIZONTAL, TILE_LOCATION, gameZone);
        var segmentNE = makeAndAddMockSegment(CORNER, locNeAndE, gameZone);
        var segmentW = makeAndAddMockSegment(VERTICAL, TILE_LOCATION, gameZone);
        var segmentE = makeAndAddMockSegment(VERTICAL, locNeAndE, gameZone);
        var segmentSW = makeAndAddMockSegment(CORNER, locSwAndS, gameZone);
        var segmentS = makeAndAddMockSegment(HORIZONTAL, locSwAndS, gameZone);
        var segmentSE = makeAndAddMockSegment(CORNER, locSe, gameZone);

        var segments = gameZone.segments(TILE_LOCATION.to2d());

        assertNotNull(segments);
        assertEquals(3, segments.size());
        assertNotNull(segments.get(CORNER));
        assertEquals(4, segments.get(CORNER).size());
        assertSame(segmentNW, segments.get(CORNER).get(TILE_LOCATION));
        assertSame(segmentNE, segments.get(CORNER).get(locNeAndE));
        assertSame(segmentSW, segments.get(CORNER).get(locSwAndS));
        assertSame(segmentSE, segments.get(CORNER).get(locSe));
        assertEquals(2, segments.get(HORIZONTAL).size());
        assertSame(segmentN, segments.get(HORIZONTAL).get(TILE_LOCATION));
        assertSame(segmentS, segments.get(HORIZONTAL).get(locSwAndS));
        assertEquals(2, segments.get(VERTICAL).size());
        assertSame(segmentW, segments.get(VERTICAL).get(TILE_LOCATION));
        assertSame(segmentE, segments.get(VERTICAL).get(locNeAndE));
    }

    @Test
    public void testGetSegmentBeforeAdd() {
        assertNull(gameZone.segment(CORNER, TILE_LOCATION));
    }

    @Test
    public void testGetSegmentAfterAdd() {
        var segmentAdded = makeAndAddMockSegment(CORNER, TILE_LOCATION, gameZone);

        var segment = gameZone.segment(CORNER, TILE_LOCATION);

        assertNotNull(segment);
        assertSame(segmentAdded, segment);
    }

    private WallSegment makeAndAddMockSegment(WallSegmentOrientation orientation, Coordinate3d loc,
                                              GameZone gameZone) {
        var mockSegmentType = mock(WallSegmentType.class);
        when(mockSegmentType.orientation()).thenReturn(orientation);
        var mockSegment = mock(WallSegment.class);
        when(mockSegment.getType()).thenReturn(mockSegmentType);

        gameZone.putSegment(loc, mockSegment);
        return mockSegment;
    }

    @Test
    public void testPutSegmentOverridesPrevious() {
        when(mockSegment1.getType()).thenReturn(mockSegmentTypeHorizontal);
        when(mockSegment2.getType()).thenReturn(mockSegmentTypeHorizontal);

        gameZone.putSegment(SEGMENT_LOCATION, mockSegment1);
        gameZone.putSegment(SEGMENT_LOCATION, mockSegment2);
        var segmentsAtLocation = gameZone.segments(SEGMENT_LOCATION.to2d());

        assertNotNull(segmentsAtLocation);
        assertEquals(1, segmentsAtLocation.get(HORIZONTAL).size());
        assertTrue(segmentsAtLocation.get(HORIZONTAL).containsKey(SEGMENT_LOCATION));
        assertSame(mockSegment2, segmentsAtLocation.get(HORIZONTAL).get(SEGMENT_LOCATION));
        verify(mockAssignLocationAfterPlacement).accept(same(mockSegment1), same(gameZone), eq(SEGMENT_LOCATION));
        verify(mockAssignLocationAfterPlacement).accept(same(mockSegment2), same(gameZone), eq(SEGMENT_LOCATION));
    }

    @Test
    public void testPutSegmentReturnsPrev() {
        when(mockSegment1.getType()).thenReturn(mockSegmentTypeHorizontal);
        when(mockSegment2.getType()).thenReturn(mockSegmentTypeHorizontal);
        gameZone.putSegment(SEGMENT_LOCATION, mockSegment1);

        var prev = gameZone.putSegment(SEGMENT_LOCATION, mockSegment2);

        assertSame(mockSegment1, prev);
    }

    @Test
    public void testGetSegmentsWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> gameZone.segments(null));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.segments(Coordinate2d.of(-1, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.segments(Coordinate2d.of(0, -1)));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.segments(Coordinate2d.of(MAX_COORDINATES.X + 2, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.segments(Coordinate2d.of(0, MAX_COORDINATES.Y + 2)));
        // NB: These calls are made to ensure that max coord2ds for segments are one greater than
        // Tiles, to contain the southern- and easternmost segments
        gameZone.segments(Coordinate2d.of(MAX_COORDINATES.X + 1, 0));
        gameZone.segments(Coordinate2d.of(0, MAX_COORDINATES.Y + 1));
    }

    @Test
    public void testPutSegmentWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.putSegment(Coordinate3d.of(-1, 0, randomInt()), mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.putSegment(Coordinate3d.of(0, -1, randomInt()), mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.putSegment(Coordinate3d.of(MAX_COORDINATES.X + 2, 0, randomInt()),
                        mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.putSegment(Coordinate3d.of(0, MAX_COORDINATES.Y + 2, randomInt()),
                        mockSegment1));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.putSegment(validCoordinate3d(), null));
        when(mockSegment1.getType()).thenReturn(null);
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.putSegment(validCoordinate3d(), mockSegment1));
        when(mockSegment1.getType()).thenReturn(mockSegmentTypeVertical);
        when(mockSegmentTypeVertical.orientation()).thenReturn(null);
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.putSegment(validCoordinate3d(), mockSegment1));
        when(mockSegmentTypeVertical.orientation()).thenReturn(VERTICAL);
        // NB: These calls are made to ensure that max coord2ds for segments are one greater than
        // Tiles, to contain the southern- and easternmost segments
        gameZone.putSegment(Coordinate3d.of(MAX_COORDINATES.X + 1, 0, 0), mockSegment1);
        gameZone.putSegment(Coordinate3d.of(0, MAX_COORDINATES.Y + 1, 0), mockSegment1);
    }

    @Test
    public void testRemoveSegment() {
        assertNull(gameZone.removeSegment(SEGMENT_LOCATION, VERTICAL));

        gameZone.putSegment(SEGMENT_LOCATION, mockSegment1);
        var prevSegment = gameZone.removeSegment(SEGMENT_LOCATION, VERTICAL);
        var segmentsAfterRemoval = gameZone.segments(SEGMENT_LOCATION.to2d()).get(VERTICAL);

        assertNotNull(prevSegment);
        assertSame(mockSegment1, prevSegment);
        assertNull(gameZone.removeSegment(SEGMENT_LOCATION, VERTICAL));
        assertNotNull(segmentsAfterRemoval);
        assertEquals(0, segmentsAfterRemoval.size());
    }

    @Test
    public void testRemoveSegmentWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(null, HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(Coordinate3d.of(-1, 0, randomInt()),
                        HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(Coordinate3d.of(0, -1, randomInt()),
                        HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(
                        Coordinate3d.of(MAX_COORDINATES.X + 2, 0, randomInt()),
                        HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(
                        Coordinate3d.of(0, MAX_COORDINATES.Y + 2, randomInt()),
                        HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeSegment(validCoordinate3d(), null));
    }

    @Test
    public void testRemoveAllSegments() {
        when(mockSegment1.getType()).thenReturn(mockSegmentTypeVertical);
        when(mockSegment2.getType()).thenReturn(mockSegmentTypeVertical);
        when(mockSegment3.getType()).thenReturn(mockSegmentTypeVertical);
        var location = validCoordinate2d();
        gameZone.putSegment(location.to3d(randomInt()), mockSegment1);
        gameZone.putSegment(location.to3d(randomInt()), mockSegment2);
        gameZone.putSegment(location.to3d(randomInt()), mockSegment3);

        gameZone.removeAllSegments(location, VERTICAL);
        var segmentsAfterRemoveAll = gameZone.segments(location).get(VERTICAL);

        assertNotNull(segmentsAfterRemoveAll);
        assertEquals(0, segmentsAfterRemoveAll.size());
    }

    @Test
    public void testRemoveAllSegmentsWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(null, HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate2d.of(-1, 0),
                        HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate2d.of(0, -1),
                        HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate2d.of(MAX_COORDINATES.X + 2, 0),
                        HORIZONTAL));
        assertThrows(IllegalArgumentException.class,
                () -> gameZone.removeAllSegments(Coordinate2d.of(0, MAX_COORDINATES.Y + 2),
                        HORIZONTAL));
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
        return Coordinate3d.of(randomIntInRange(0, MAX_COORDINATES.X),
                randomIntInRange(0, MAX_COORDINATES.Y), z);
    }

    @Test
    public void testData() {
        assertSame(DATA, gameZone.data());
    }

    @Test
    public void testAddCharacterToGameZoneAfterAddedToTile() {
        // TODO: Test and implement this method
    }

    @Test
    public void testRemoveCharacterFromGameZoneAfterAddedToTile() {
        // TODO: Test and implement this method
    }

    @Test
    public void testRemoveCharacterFromGameZoneViaTileCharactersAndRemovedFromRoundManager() {
        // TODO: Test and implement this functionality
    }

    @Test
    public void testDelete() {
        var tiles = new HashSet<Tile>();
        for (var x = 0; x <= MAX_COORDINATES.X; x++) {
            for (var y = 0; y <= MAX_COORDINATES.Y; y++) {
                tiles.addAll(gameZone.tiles(Coordinate2d.of(x, y)));
            }
        }

        gameZone.delete();

        assertTrue(gameZone.isDeleted());
        tiles.forEach(tile -> assertTrue(tile.isDeleted()));
    }
}
