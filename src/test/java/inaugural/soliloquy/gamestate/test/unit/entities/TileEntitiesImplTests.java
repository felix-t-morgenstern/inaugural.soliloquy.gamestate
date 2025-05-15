package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileEntitiesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class TileEntitiesImplTests {
    @Mock private Tile mockTile;
    @Mock private Item mockItem1;
    @Mock private Item mockItem2;
    @Mock private Item mockItem3;

    private TileEntitiesImpl<Item> tileEntities;

    @BeforeEach
    public void setUp() {
        tileEntities = new TileEntitiesImpl<>(mockTile);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new TileEntitiesImpl<>(null));
    }

    @Test
    public void testAddAndContains() {
        assertFalse(tileEntities.contains(mockItem1));

        tileEntities.add(mockItem1);

        when(mockItem1.tile()).thenReturn(mockTile);
        assertTrue(tileEntities.contains(mockItem1));
    }

    @Test
    public void testAddItemAlreadyElsewhereInVariousLocations() {
        when(mockItem1.equipmentSlot()).thenReturn(pairOf(mock(Character.class), randomString()));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(mockItem1));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(mockItem1, 0));

        when(mockItem1.equipmentSlot()).thenReturn(null);
        when(mockItem1.inventoryCharacter()).thenReturn(mock(Character.class));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(mockItem1));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(mockItem1, 0));

        when(mockItem1.inventoryCharacter()).thenReturn(null);
        when(mockItem1.tile()).thenReturn(mock(Tile.class));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(mockItem1));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(mockItem1, 0));

        lenient().when(mockItem1.tile()).thenReturn(null);
        when(mockItem1.tileFixture()).thenReturn(mock(TileFixture.class));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(mockItem1));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(mockItem1, 0));
    }

    @Test
    public void testRemove() {
        assertFalse(tileEntities.remove(mockItem1));
        tileEntities.add(mockItem1);

        when(mockItem1.tile()).thenReturn(mockTile);
        assertTrue(tileEntities.remove(mockItem1));
        assertFalse(tileEntities.remove(mockItem1));
    }

    @Test
    public void testAddContainsAndRemoveWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(null));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.add(null, 0));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.contains(null));
        assertThrows(IllegalArgumentException.class, () -> tileEntities.remove(null));
    }

    @Test
    public void testGetRepresentation() {
        tileEntities.add(mockItem1);
        tileEntities.add(mockItem2);
        tileEntities.add(mockItem3);

        var representation = tileEntities.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.containsKey(mockItem1));
        assertTrue(representation.containsKey(mockItem2));
        assertTrue(representation.containsKey(mockItem3));
    }

    @Test
    public void testAddAtZIndex() {
        var zIndex = randomInt();

        tileEntities.add(mockItem1, zIndex);
        var representation = tileEntities.representation();

        assertEquals((Integer) zIndex, representation.get(mockItem1));
    }

    @Test
    public void testIterator() {
        Integer z1 = randomInt();
        Integer z2 = randomInt();
        Integer z3 = randomInt();
        tileEntities.add(mockItem1, z1);
        tileEntities.add(mockItem2, z2);
        tileEntities.add(mockItem3, z3);

        List<Pair<Item, Integer>> fromIterator = listOf();

        tileEntities.forEach(fromIterator::add);

        assertEquals(3, fromIterator.size());
        var entityFound = new boolean[3];
        fromIterator.forEach(pair -> {
            if (pair.FIRST == mockItem1) {
                assertFalse(entityFound[0]);
                assertEquals(z1, pair.SECOND);
                entityFound[0] = true;
            }
            if (pair.FIRST == mockItem2) {
                assertFalse(entityFound[1]);
                assertEquals(z2, pair.SECOND);
                entityFound[1] = true;
            }
            if (pair.FIRST == mockItem3) {
                assertFalse(entityFound[2]);
                assertEquals(z3, pair.SECOND);
                entityFound[2] = true;
            }
        });
    }

    @Test
    public void testInitializeActionAfterAdding() {
        List<Item> addedToGameZone = listOf();

        tileEntities.initializeActionAfterAdding(addedToGameZone::add);
        tileEntities.add(mockItem1);

        assertEquals(1, addedToGameZone.size());
        assertTrue(addedToGameZone.contains(mockItem1));
    }

    @Test
    public void testInitializeActionAfterRemoving() {
        List<Item> removedFromGameZone = listOf();

        tileEntities.initializeActionAfterRemoving(removedFromGameZone::add);
        tileEntities.remove(mockItem1);

        assertEquals(0, removedFromGameZone.size());

        tileEntities.add(mockItem1);
        when(mockItem1.tile()).thenReturn(mockTile);
        tileEntities.remove(mockItem1);

        assertEquals(1, removedFromGameZone.size());
        assertTrue(removedFromGameZone.contains(mockItem1));
    }

    @Test
    public void testInitializeMoreThanOnce() {
        tileEntities.initializeActionAfterAdding(null);
        tileEntities.initializeActionAfterAdding(e -> {});
        assertThrows(UnsupportedOperationException.class,
                () -> tileEntities.initializeActionAfterAdding(e -> {}));

        tileEntities.initializeActionAfterRemoving(null);
        tileEntities.initializeActionAfterRemoving(e -> {});
        assertThrows(UnsupportedOperationException.class,
                () -> tileEntities.initializeActionAfterRemoving(e -> {}));
    }

    @Test
    public void testDelete() {
        assertFalse(tileEntities.isDeleted());

        tileEntities.add(mockItem1);
        tileEntities.add(mockItem2);
        tileEntities.add(mockItem3);

        tileEntities.delete();

        assertTrue(tileEntities.isDeleted());

        verify(mockItem1).delete();
        verify(mockItem2).delete();
        verify(mockItem3).delete();
    }

    @Test
    public void testDeletedInvariant() {
        tileEntities.delete();

        assertThrows(EntityDeletedException.class, () -> tileEntities.representation());
        assertThrows(EntityDeletedException.class, () -> tileEntities.add(mockItem1));
        assertThrows(EntityDeletedException.class, () -> tileEntities.add(mockItem1, 0));
        assertThrows(EntityDeletedException.class, () -> tileEntities.contains(mockItem1));
        assertThrows(EntityDeletedException.class, () -> tileEntities.remove(mockItem1));
        assertThrows(EntityDeletedException.class, () -> tileEntities.iterator());
    }

    @Test
    public void testTileDeletedInvariant() {
        when(mockTile.isDeleted()).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> tileEntities.representation());
        assertThrows(IllegalStateException.class, () -> tileEntities.add(mockItem1));
        assertThrows(IllegalStateException.class, () -> tileEntities.add(mockItem1, 0));
        assertThrows(IllegalStateException.class, () -> tileEntities.contains(mockItem1));
        assertThrows(IllegalStateException.class, () -> tileEntities.remove(mockItem1));
        assertThrows(IllegalStateException.class, () -> tileEntities.iterator());
    }

    @Test
    public void testItemAssignmentInvariant() {
        tileEntities.add(mockItem1);
        when(mockItem1.tile()).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> tileEntities.add(mockItem1));
        assertThrows(IllegalStateException.class, () -> tileEntities.add(mockItem1, 0));
        assertThrows(IllegalStateException.class, () -> tileEntities.contains(mockItem1));
        assertThrows(IllegalStateException.class, () -> tileEntities.remove(mockItem1));
    }
}
