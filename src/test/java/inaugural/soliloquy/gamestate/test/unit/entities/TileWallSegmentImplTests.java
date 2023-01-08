package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileWallSegmentImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeTile;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileWallSegments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDimensions;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TileWallSegmentImplTests {
    @Mock private VariableCache mockData;
    @Mock private WallSegmentType mockWallSegmentType;

    private TileWallSegment tileWallSegment;

    @BeforeEach
    void setUp() {
        mockData = mock(VariableCache.class);
        mockWallSegmentType = mock(WallSegmentType.class);

        tileWallSegment = new TileWallSegmentImpl(mockData);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegmentImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegment.class.getCanonicalName(),
                tileWallSegment.getInterfaceName());
    }

    @Test
    void testData() {
        assertSame(mockData, tileWallSegment.data());
    }

    @Test
    void testSetAndGetType() {
        tileWallSegment.setType(mockWallSegmentType);

        assertSame(mockWallSegmentType, tileWallSegment.getType());
    }

    @Test
    void testSetTypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> tileWallSegment.setType(null));
    }

    @Test
    void testAssignTileWallSegmentsToTileAfterAddingToTileWallSegmentsAndGetTile() {
        var tile = new FakeTile();
        ((FakeTileWallSegments) tile.wallSegments()).SEGMENTS
                .get(TileWallSegmentDirection.NORTH).put(tileWallSegment,
                new TileWallSegmentDimensions() {
                    @Override
                    public String getInterfaceName() {
                        return null;
                    }

                    @Override
                    public int getZIndex() {
                        return 0;
                    }

                    @Override
                    public int getHeight() {
                        return 0;
                    }
                });

        assertNull(tileWallSegment.tile());

        tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(tile);

        assertSame(tile, tileWallSegment.tile());
    }

    @Test
    void testSetAndGetName() {
        final var name = "name";

        tileWallSegment.setName(name);

        assertEquals(name, tileWallSegment.getName());
    }

    @Test
    void testDelete() {
        // TODO: Add a TileWallSegment here
        assertFalse(tileWallSegment.isDeleted());

        tileWallSegment.delete();

        // TODO: Test whether added TileWallSegment was deleted
        assertTrue(tileWallSegment.isDeleted());
    }

    @Test
    void testMovementEvents() {
        assertNotNull(tileWallSegment.movementEvents());
    }

    @Test
    void testAbilityEvents() {
        assertNotNull(tileWallSegment.abilityEvents());
    }

    @Test
    void testMakeGameEventTarget() {
        var gameEventTarget = tileWallSegment.makeGameEventTarget();

        assertNotNull(gameEventTarget);
        assertNotNull(gameEventTarget.tileWallSegment());
        assertNull(gameEventTarget.tile());
        assertNull(gameEventTarget.tileFixture());
        assertEquals(GameEventTarget.class.getCanonicalName(), gameEventTarget.getInterfaceName());
    }

    @Test
    void testDeletionInvariant() {
        tileWallSegment.delete();

        assertThrows(EntityDeletedException.class, () -> tileWallSegment.getType());
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegment.setType(mock(WallSegmentType.class)));
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.tile());
        assertThrows(EntityDeletedException.class,
                () -> tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.data());
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.getName());
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.setName(""));
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.movementEvents());
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.abilityEvents());
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.makeGameEventTarget());
    }

    @Test
    void testAggregateAssignmentInvariant() {
        var tile = new FakeTile();
        tile.wallSegments().add(TileWallSegmentDirection.NORTH, tileWallSegment, 0);

        ((FakeTileWallSegments) tile.wallSegments()).SEGMENTS
                .get(TileWallSegmentDirection.NORTH).remove(tileWallSegment);

        assertThrows(IllegalStateException.class, () -> tileWallSegment.getType());
        assertThrows(IllegalStateException.class,
                () -> tileWallSegment.setType(mock(WallSegmentType.class)));
        assertThrows(IllegalStateException.class, () -> tileWallSegment.tile());
        assertThrows(IllegalStateException.class,
                () -> tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> tileWallSegment.data());
        assertThrows(IllegalStateException.class, () -> tileWallSegment.getName());
        assertThrows(IllegalStateException.class, () -> tileWallSegment.setName(""));
        assertThrows(IllegalStateException.class, () -> tileWallSegment.movementEvents());
        assertThrows(IllegalStateException.class, () -> tileWallSegment.abilityEvents());
        assertThrows(IllegalStateException.class, () -> tileWallSegment.makeGameEventTarget());
    }
}
