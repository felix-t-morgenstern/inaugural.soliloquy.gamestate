package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.WallSegmentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.WallSegment;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.WallSegmentType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class WallSegmentImplTests {
    @Mock private Map<String, Object> mockData;
    @Mock private WallSegmentType mockWallSegmentType;

    private WallSegment wallSegment;

    @BeforeEach
    public void setUp() {
        wallSegment = new WallSegmentImpl(mockData);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new WallSegmentImpl(null));
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
    public void testSetTypeWithInvalidArgs() {
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
