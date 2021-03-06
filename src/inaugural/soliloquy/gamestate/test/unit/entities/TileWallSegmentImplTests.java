package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileWallSegmentImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDimensions;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import static org.junit.jupiter.api.Assertions.*;

class TileWallSegmentImplTests {
    private final VariableCache DATA = new VariableCacheStub();
    private final WallSegmentType WALL_SEGMENT_TYPE = new FakeWallSegmentType();

    private TileWallSegment _tileWallSegment;

    @BeforeEach
    void setUp() {
        _tileWallSegment = new TileWallSegmentImpl(DATA);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileWallSegmentImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileWallSegment.class.getCanonicalName(),
                _tileWallSegment.getInterfaceName());
    }

    @Test
    void testData() {
        assertSame(DATA, _tileWallSegment.data());
    }

    @Test
    void testSetAndGetType() {
        _tileWallSegment.setType(WALL_SEGMENT_TYPE);

        assertSame(WALL_SEGMENT_TYPE, _tileWallSegment.getType());
    }

    @Test
    void testAssignTileWallSegmentsToTileAfterAddingToTileWallSegmentsAndGetTile() {
        Tile tile = new FakeTile();
        ((FakeTileWallSegments)tile.wallSegments()).SEGMENTS
                .get(TileWallSegmentDirection.NORTH).put(_tileWallSegment,
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

        assertNull(_tileWallSegment.tile());

        _tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(tile);

        assertSame(tile, _tileWallSegment.tile());
    }

    @Test
    void testSetAndGetName() {
        final String name = "name";

        _tileWallSegment.setName(name);

        assertEquals(name, _tileWallSegment.getName());
    }

    @Test
    void testDelete() {
        // TODO: Add a TileWallSegment here
        assertFalse(_tileWallSegment.isDeleted());

        _tileWallSegment.delete();

        // TODO: Test whether added TileWallSegment was deleted
        assertTrue(_tileWallSegment.isDeleted());
    }

    @Test
    void testDeletionInvariant() {
        _tileWallSegment.delete();

        assertThrows(EntityDeletedException.class, () -> _tileWallSegment.getType());
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegment.setType(new FakeWallSegmentType()));
        assertThrows(EntityDeletedException.class, () -> _tileWallSegment.tile());
        assertThrows(EntityDeletedException.class,
                () -> _tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(EntityDeletedException.class, () -> _tileWallSegment.data());
        assertThrows(EntityDeletedException.class, () -> _tileWallSegment.getName());
        assertThrows(EntityDeletedException.class, () -> _tileWallSegment.setName(""));
    }

    @Test
    void testAggregateAssignmentInvariant() {
        Tile tile = new FakeTile();
        tile.wallSegments().add(TileWallSegmentDirection.NORTH, _tileWallSegment, 0);

        ((FakeTileWallSegments)tile.wallSegments()).SEGMENTS
                .get(TileWallSegmentDirection.NORTH).remove(_tileWallSegment);

        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getType());
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegment.setType(new FakeWallSegmentType()));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.tile());
        assertThrows(IllegalStateException.class,
                () -> _tileWallSegment.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.data());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.getName());
        assertThrows(IllegalStateException.class, () -> _tileWallSegment.setName(""));
    }
}
