package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.WallSegmentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.WallSegment;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WallSegmentImplTests {
    @Mock private VariableCache mockData;
    @Mock private WallSegmentType mockWallSegmentType;

    private WallSegment tileWallSegment;

    @BeforeEach
    void setUp() {
        mockData = mock(VariableCache.class);
        mockWallSegmentType = mock(WallSegmentType.class);

        tileWallSegment = new WallSegmentImpl(mockData);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new WallSegmentImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(WallSegment.class.getCanonicalName(),
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
    void testSetAndGetName() {
        final var name = "name";

        tileWallSegment.setName(name);

        assertEquals(name, tileWallSegment.getName());
    }

    @Test
    void testDelete() {
        // TODO: Add a WallSegment here
        assertFalse(tileWallSegment.isDeleted());

        tileWallSegment.delete();

        // TODO: Test whether added WallSegment was deleted
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
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.data());
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.getName());
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.setName(""));
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.movementEvents());
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.abilityEvents());
        assertThrows(EntityDeletedException.class, () -> tileWallSegment.makeGameEventTarget());
    }
}
