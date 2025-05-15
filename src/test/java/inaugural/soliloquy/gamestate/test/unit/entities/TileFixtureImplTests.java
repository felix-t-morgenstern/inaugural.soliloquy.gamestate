package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileFixtureImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;
import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

@ExtendWith(MockitoExtension.class)
public class TileFixtureImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    @Mock private FixtureType mockFixtureType;
    @Mock private Vertex mockTileOffset;
    @Mock private TileFixtureItems mockTileFixtureItems;
    @Mock private Function<TileFixture, TileFixtureItems> mockTileFixtureItemsFactory;
    @Mock private Tile mockTile;
    @Mock private TileEntities<TileFixture> mockTileFixtures;

    private Map<String, Object> data;

    private TileFixture tileFixture;

    @BeforeEach
    public void setUp() {
        data = mapOf(pairOf(randomString(), randomInt()));

        when(mockFixtureType.defaultTileOffset()).thenReturn(mockTileOffset);

        when(mockTileFixtureItemsFactory.apply(any())).thenReturn(mockTileFixtureItems);

        tileFixture = new TileFixtureImpl(UUID, mockFixtureType, mockTileFixtureItemsFactory, data);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureImpl(null, mockFixtureType, mockTileFixtureItemsFactory,
                data));
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureImpl(UUID, null, mockTileFixtureItemsFactory,
                data));
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureImpl(UUID, mockFixtureType, null,
                data));
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureImpl(UUID, mockFixtureType, mockTileFixtureItemsFactory, null));
    }

    @Test
    public void testUuid() {
        assertSame(UUID, tileFixture.uuid());
    }

    @Test
    public void testType() {
        assertSame(mockFixtureType, tileFixture.type());
    }

    @Test
    public void testMovementEvents() {
        assertNotNull(tileFixture.movementEvents());
    }

    @Test
    public void testAbilityEvents() {
        assertNotNull(tileFixture.abilityEvents());
    }

    @Test
    public void testMakeGameEventTarget() {
        var gameEventTarget = tileFixture.makeGameEventTarget();

        assertNotNull(gameEventTarget);
        assertNull(gameEventTarget.tile());
        assertNull(gameEventTarget.tileWallSegment());
        assertNotNull(gameEventTarget.tileFixture());
        assertSame(tileFixture, gameEventTarget.tileFixture());
    }

    @Test
    public void testTileFixtureItems() {
        assertNotNull(tileFixture.items());
        assertSame(mockTileFixtureItems, tileFixture.items());
        verify(mockTileFixtureItemsFactory).apply(tileFixture);
    }

    @Test
    public void testAssignTileAfterAddedToTileEntitiesOfType() {
        when(mockTile.fixtures()).thenReturn(mockTileFixtures);
        when(mockTileFixtures.contains(any())).thenReturn(true);

        tileFixture.assignTileAfterAddedToTileEntitiesOfType(mockTile);

        verify(mockTile).fixtures();
        verify(mockTileFixtures).contains(tileFixture);
    }

    @Test
    public void testData() {
        assertEquals(data, tileFixture.data());
    }

    @Test
    public void testDelete() {
        when(mockTile.fixtures()).thenReturn(mockTileFixtures);
        when(mockTileFixtures.contains(any())).thenReturn(true);
        tileFixture.assignTileAfterAddedToTileEntitiesOfType(mockTile);

        tileFixture.delete();

        assertTrue(tileFixture.isDeleted());
        verify(mockTileFixtureItems).delete();
        verify(mockTile, atLeast(1)).fixtures();
        verify(mockTileFixtures).remove(tileFixture);
    }

    @Test
    public void testSetAndGetName() {
        var name = randomString();

        tileFixture.setName(name);

        assertEquals(name, tileFixture.getName());
    }

    @Test
    public void testSetAndGetTileOffset() {
        var newTileOffset = mock(Vertex.class);

        tileFixture.setTileOffset(newTileOffset);

        assertSame(newTileOffset, tileFixture.getTileOffset());
    }

    @Test
    public void testCreatedWithDefaultOffsets() {
        assertSame(mockTileOffset, tileFixture.getTileOffset());
    }

    @Test
    public void testDeletedInvariant() {
        tileFixture.delete();

        assertThrows(EntityDeletedException.class, () -> tileFixture.tile());
        assertThrows(EntityDeletedException.class, () -> tileFixture.type());
        assertThrows(EntityDeletedException.class, () -> tileFixture.getTileOffset());
        assertThrows(EntityDeletedException.class,
                () -> tileFixture.setTileOffset(vertexOf(0f, 0f)));
        assertThrows(EntityDeletedException.class, () -> tileFixture.movementEvents());
        assertThrows(EntityDeletedException.class, () -> tileFixture.items());
        assertThrows(EntityDeletedException.class,
                () -> tileFixture.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(EntityDeletedException.class, () -> tileFixture.data());
        assertThrows(EntityDeletedException.class, () -> tileFixture.getName());
        assertThrows(EntityDeletedException.class, () -> tileFixture.setName(""));
    }

    @Test
    public void testContainingTileInvariant() {
        when(mockTile.fixtures()).thenReturn(mockTileFixtures);
        when(mockTileFixtures.contains(any())).thenReturn(true);
        tileFixture.assignTileAfterAddedToTileEntitiesOfType(mockTile);
        when(mockTileFixtures.contains(any())).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> tileFixture.tile());
        assertThrows(IllegalStateException.class, () -> tileFixture.type());
        assertThrows(IllegalStateException.class, () -> tileFixture.getTileOffset());
        assertThrows(IllegalStateException.class,
                () -> tileFixture.setTileOffset(vertexOf(0f, 0f)));
        assertThrows(IllegalStateException.class, () -> tileFixture.movementEvents());
        assertThrows(IllegalStateException.class, () -> tileFixture.items());
        assertThrows(IllegalStateException.class,
                () -> tileFixture.assignTileAfterAddedToTileEntitiesOfType(null));
        assertThrows(IllegalStateException.class, () -> tileFixture.data());
        assertThrows(IllegalStateException.class, () -> tileFixture.delete());
        assertThrows(IllegalStateException.class, () -> tileFixture.getName());
        assertThrows(IllegalStateException.class, () -> tileFixture.setName(""));
    }
}
