package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileFixtureItemsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class TileFixtureItemsImplTests {
    @Mock private TileFixture mockTileFixture;
    @Mock private Item mockItem;
    @Mock private Item mockItem2;
    @Mock private Item mockItem3;
    @Mock private Character mockCharacter;

    private TileFixtureItems tileFixtureItems;

    @BeforeEach
    public void setUp() {
        tileFixtureItems = new TileFixtureItemsImpl(mockTileFixture);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new TileFixtureItemsImpl(null));
    }

    @Test
    public void testAddAndContains() {
        assertFalse(tileFixtureItems.contains(mockItem));

        tileFixtureItems.add(mockItem);

        when(mockItem.tileFixture()).thenReturn(mockTileFixture);
        assertTrue(tileFixtureItems.contains(mockItem));
    }

    @Test
    public void testAddCallsItemAssignmentFunction() {
        tileFixtureItems.add(mockItem);

        verify(mockItem, once()).assignTileFixtureAfterAddedItemToTileFixtureItems(mockTileFixture);
    }

    @Test
    public void testRemoveCallsItemAssignmentFunction() {
        tileFixtureItems.add(mockItem);

        when(mockItem.tileFixture()).thenReturn(mockTileFixture);
        tileFixtureItems.remove(mockItem);

        verify(mockItem, once()).assignTileFixtureAfterAddedItemToTileFixtureItems(null);
    }

    @Test
    public void testAddItemAlreadyPresentInOtherLocationTypes() {
        when(mockItem.equipmentSlot()).thenReturn(pairOf(mockCharacter, randomString()));

        assertThrows(IllegalArgumentException.class, () -> tileFixtureItems.add(mockItem));

        when(mockItem.equipmentSlot()).thenReturn(null);
        when(mockItem.inventoryCharacter()).thenReturn(mockCharacter);

        assertThrows(IllegalArgumentException.class, () -> tileFixtureItems.add(mockItem));

        when(mockItem.inventoryCharacter()).thenReturn(null);
        when(mockItem.tile()).thenReturn(mock(Tile.class));

        assertThrows(IllegalArgumentException.class, () -> tileFixtureItems.add(mockItem));

        when(mockItem.tileFixture()).thenReturn(mockTileFixture);

        assertThrows(IllegalArgumentException.class, () -> tileFixtureItems.add(mockItem));
    }

    @Test
    public void testAddAndContainsWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> tileFixtureItems.add(null));
        assertThrows(IllegalArgumentException.class, () -> tileFixtureItems.contains(null));
    }

    @Test
    public void testRemove() {
        assertFalse(tileFixtureItems.contains(mockItem));
        tileFixtureItems.add(mockItem);
        when(mockItem.tileFixture()).thenReturn(mockTileFixture);
        assertTrue(tileFixtureItems.contains(mockItem));

        assertTrue(tileFixtureItems.remove(mockItem));
        assertFalse(tileFixtureItems.remove(mockItem));
        assertFalse(tileFixtureItems.contains(mockItem));
    }

    @Test
    public void testRemoveWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> tileFixtureItems.remove(null));
    }

    @Test
    public void testGetRepresentation() {
        tileFixtureItems.add(mockItem);
        tileFixtureItems.add(mockItem2);
        tileFixtureItems.add(mockItem3);

        var representation = tileFixtureItems.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(mockItem));
        assertTrue(representation.contains(mockItem2));
        assertTrue(representation.contains(mockItem3));
    }

    @Test
    public void testDelete() {
        assertFalse(tileFixtureItems.isDeleted());
        tileFixtureItems.add(mockItem);
        tileFixtureItems.delete();

        assertTrue(tileFixtureItems.isDeleted());
        verify(mockItem, once()).delete();
    }

    @Test
    public void testDeletedInvariant() {
        tileFixtureItems.delete();

        assertThrows(EntityDeletedException.class, () -> tileFixtureItems.representation());
        assertThrows(EntityDeletedException.class, () -> tileFixtureItems.add(mockItem));
        assertThrows(EntityDeletedException.class, () -> tileFixtureItems.remove(mockItem));
        assertThrows(EntityDeletedException.class, () -> tileFixtureItems.contains(mockItem));
    }

    @Test
    public void testTileFixtureDeletedInvariant() {
        when(mockTileFixture.isDeleted()).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> tileFixtureItems.representation());
        assertThrows(IllegalStateException.class, () -> tileFixtureItems.add(mockItem));
        assertThrows(IllegalStateException.class, () -> tileFixtureItems.remove(mockItem));
        assertThrows(IllegalStateException.class, () -> tileFixtureItems.contains(mockItem));
    }

    @Test
    public void testItemAssignmentInvariant() {
        tileFixtureItems.add(mockItem);
        when(mockItem.tileFixture()).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> tileFixtureItems.add(mockItem));
        assertThrows(IllegalStateException.class, () -> tileFixtureItems.remove(mockItem));
        assertThrows(IllegalStateException.class, () -> tileFixtureItems.contains(mockItem));
    }
}
