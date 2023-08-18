package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterInventoryImpl;
import inaugural.soliloquy.gamestate.entities.TileFixtureImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CharacterInventoryImplTests {
    @Mock private Character mockCharacter;
    @Mock private Item mockItem1;
    @Mock private Item mockItem2;
    @Mock private Item mockItem3;

    private CharacterInventory characterInventory;

    @Before
    public void setUp() {
        characterInventory = new CharacterInventoryImpl(mockCharacter);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterInventoryImpl(null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(CharacterInventory.class.getCanonicalName(),
                characterInventory.getInterfaceName());
    }

    @Test
    public void testAddAndContains() {
        characterInventory.add(mockItem1);

        assertTrue(characterInventory.contains(mockItem1));
    }

    @Test
    public void testAddAndRemove() {
        characterInventory.add(mockItem1);

        assertTrue(characterInventory.remove(mockItem1));
        assertFalse(characterInventory.remove(mockItem1));
    }

    @Test
    public void testThrowsWhenItemIsNull() {
        assertThrows(IllegalArgumentException.class, () -> characterInventory.add(null));
        assertThrows(IllegalArgumentException.class, () -> characterInventory.contains(null));
        assertThrows(IllegalArgumentException.class, () -> characterInventory.remove(null));
    }

    @Test
    public void testThrowWhenItemIsDeleted() {
        when(mockItem1.isDeleted()).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> characterInventory.add(mockItem1));
        assertThrows(IllegalArgumentException.class, () -> characterInventory.contains(mockItem1));
        assertThrows(IllegalArgumentException.class, () -> characterInventory.remove(mockItem1));
    }

    @Test
    public void testRepresentation() {
        characterInventory.add(mockItem1);
        characterInventory.add(mockItem2);
        characterInventory.add(mockItem3);

        var representation = characterInventory.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(mockItem1));
        assertTrue(representation.contains(mockItem2));
        assertTrue(representation.contains(mockItem3));
    }

    @Test
    public void testThrowOnAddItemPresentElsewhere() {
        when(mockItem1.inventoryCharacter()).thenReturn(mockCharacter);
        assertThrows(IllegalArgumentException.class, () -> characterInventory.add(mockItem1));

        when(mockItem1.inventoryCharacter()).thenReturn(null);
        when(mockItem1.tileFixture()).thenReturn(mock(TileFixtureImpl.class));
        assertThrows(IllegalArgumentException.class, () -> characterInventory.add(mockItem1));

        when(mockItem1.tileFixture()).thenReturn(null);
        when(mockItem1.tile()).thenReturn(mock(Tile.class));
        assertThrows(IllegalArgumentException.class, () -> characterInventory.add(mockItem1));

        lenient().when(mockItem1.tile()).thenReturn(null);
        when(mockItem1.equipmentSlot()).thenReturn(pairOf(mockCharacter, randomString()));
        assertThrows(IllegalArgumentException.class, () -> characterInventory.add(mockItem1));
    }

    @Test
    public void testIterator() {
        characterInventory.add(mockItem1);
        characterInventory.add(mockItem2);
        characterInventory.add(mockItem3);

        List<Item> itemsFromIterator = listOf();

        characterInventory.forEach(itemsFromIterator::add);

        assertEquals(3, itemsFromIterator.size());
        assertTrue(itemsFromIterator.contains(mockItem1));
        assertTrue(itemsFromIterator.contains(mockItem2));
        assertTrue(itemsFromIterator.contains(mockItem3));
    }

    @Test
    public void testDelete() {
        characterInventory.add(mockItem1);

        characterInventory.delete();

        assertTrue(characterInventory.isDeleted());
        verify(mockItem1).delete();
    }

    @Test
    public void testDeletionInvariant() {
        characterInventory.delete();

        assertThrows(EntityDeletedException.class, () -> characterInventory.add(mockItem1));
        assertThrows(EntityDeletedException.class, () -> characterInventory.contains(mockItem1));
        assertThrows(EntityDeletedException.class, () -> characterInventory.remove(mockItem1));
        assertThrows(EntityDeletedException.class, () -> characterInventory.representation());
    }

    @Test
    public void testCharacterDeletedInvariant() {
        when(mockCharacter.isDeleted()).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> characterInventory.add(mockItem1));
        assertThrows(IllegalStateException.class, () -> characterInventory.contains(mockItem1));
        assertThrows(IllegalStateException.class, () -> characterInventory.remove(mockItem1));
        assertThrows(IllegalStateException.class, () -> characterInventory.representation());
    }
}
