package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.WallSegmentImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.WallSegment;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class WallSegmentImplTests {
    @Mock private VariableCache mockData;
    @Mock private WallSegmentType mockWallSegmentType;

    private WallSegment wallSegment;

    @Before
    public void setUp() {
        mockData = mock(VariableCache.class);
        mockWallSegmentType = mock(WallSegmentType.class);

        wallSegment = new WallSegmentImpl(mockData);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new WallSegmentImpl(null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(WallSegment.class.getCanonicalName(),
                wallSegment.getInterfaceName());
    }

    @Test
    public void testData() {
        assertSame(mockData, wallSegment.data());
    }

    @Test
    public void testSetAndGetType() {
        wallSegment.setType(mockWallSegmentType);

        assertSame(mockWallSegmentType, wallSegment.getType());
    }

    @Test
    public void testSetTypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> wallSegment.setType(null));
    }

    @Test
    public void testDelete() {
        // TODO: Add a WallSegment here
        assertFalse(wallSegment.isDeleted());

        wallSegment.delete();

        // TODO: Test whether added WallSegment was deleted
        assertTrue(wallSegment.isDeleted());
    }

    @Test
    public void testMovementEvents() {
        assertNotNull(wallSegment.movementEvents());
    }

    @Test
    public void testAbilityEvents() {
        assertNotNull(wallSegment.abilityEvents());
    }

    @Test
    public void testMakeGameEventTarget() {
        var gameEventTarget = wallSegment.makeGameEventTarget();

        assertNotNull(gameEventTarget);
        assertNotNull(gameEventTarget.tileWallSegment());
        assertNull(gameEventTarget.tile());
        assertNull(gameEventTarget.tileFixture());
        assertEquals(GameEventTarget.class.getCanonicalName(), gameEventTarget.getInterfaceName());
    }

    @Test
    public void testDeletionInvariant() {
        wallSegment.delete();

        assertThrows(EntityDeletedException.class, () -> wallSegment.getType());
        assertThrows(EntityDeletedException.class,
                () -> wallSegment.setType(mock(WallSegmentType.class)));
        assertThrows(EntityDeletedException.class, () -> wallSegment.data());
        assertThrows(EntityDeletedException.class, () -> wallSegment.movementEvents());
        assertThrows(EntityDeletedException.class, () -> wallSegment.abilityEvents());
        assertThrows(EntityDeletedException.class, () -> wallSegment.makeGameEventTarget());
    }
}
