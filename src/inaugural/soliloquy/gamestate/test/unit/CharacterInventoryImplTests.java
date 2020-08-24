package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CharacterInventoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterInventory;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInventoryImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();
    private final Item ITEM = new FakeItem();

    private CharacterInventory _characterInventory;

    @BeforeEach
    void setUp() {
        _characterInventory = new CharacterInventoryImpl(CHARACTER, COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterInventoryImpl(null,
                COLLECTION_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new CharacterInventoryImpl(CHARACTER,
                null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterInventory.class.getCanonicalName(),
                _characterInventory.getInterfaceName());
    }

    @Test
    void testAddAndContains() {
        _characterInventory.add(ITEM);

        assertTrue(_characterInventory.contains(ITEM));
    }

    @Test
    void testAddAndRemove() {
        _characterInventory.add(ITEM);

        assertTrue(_characterInventory.remove(ITEM));
        assertFalse(_characterInventory.remove(ITEM));
    }

    @Test
    void testThrowsWhenItemIsNull() {
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(null));
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.contains(null));
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.remove(null));
    }

    @Test
    void testThrowWhenItemIsDeleted() {
        ITEM.delete();

        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.contains(ITEM));
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.remove(ITEM));
    }

    @Test
    void testRepresentation() {
        Item item1 = new FakeItem();
        Item item2 = new FakeItem();
        Item item3 = new FakeItem();
        _characterInventory.add(item1);
        _characterInventory.add(item2);
        _characterInventory.add(item3);

        ReadableCollection<Item> representation = _characterInventory.representation();

        assertNotNull(representation);
        assertNotNull(representation.getArchetype());
        assertEquals(Item.class.getCanonicalName(),
                representation.getArchetype().getInterfaceName());
        assertEquals(3, representation.size());
        assertTrue(representation.contains(item1));
        assertTrue(representation.contains(item2));
        assertTrue(representation.contains(item3));
    }

    @Test
    void testThrowOnAddItemPresentElsewhere() {
        ((FakeItem)ITEM)._inventoryCharacter = new FakeCharacter();
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));

        ((FakeItem)ITEM)._inventoryCharacter = null;
        ((FakeItem)ITEM)._tileFixture = new FakeTileFixture();
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));

        ((FakeItem)ITEM)._tileFixture = null;
        ((FakeItem)ITEM)._tile = new FakeTile();
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));

        ((FakeItem)ITEM)._tile = null;
        ((FakeItem)ITEM)._equipmentCharacter = new FakeCharacter();
        ((FakeItem)ITEM)._equipmentSlotType = "slotType";
        assertThrows(IllegalArgumentException.class, () -> _characterInventory.add(ITEM));
    }

    @Test
    void testIterator() {
        Item item1 = new FakeItem();
        Item item2 = new FakeItem();
        Item item3 = new FakeItem();

        _characterInventory.add(item1);
        _characterInventory.add(item2);
        _characterInventory.add(item3);

        ArrayList<Item> itemsFromIterator = new ArrayList<>();

        _characterInventory.forEach(itemsFromIterator::add);

        assertEquals(3, itemsFromIterator.size());
        assertTrue(itemsFromIterator.contains(item1));
        assertTrue(itemsFromIterator.contains(item2));
        assertTrue(itemsFromIterator.contains(item3));
    }

    @Test
    void testDelete() {
        _characterInventory.add(ITEM);

        _characterInventory.delete();

        assertTrue(_characterInventory.isDeleted());
        assertTrue(ITEM.isDeleted());
    }

    @Test
    void testDeletionInvariant() {
        _characterInventory.delete();

        assertThrows(EntityDeletedException.class, () -> _characterInventory.add(ITEM));
        assertThrows(EntityDeletedException.class, () -> _characterInventory.contains(ITEM));
        assertThrows(EntityDeletedException.class, () -> _characterInventory.remove(ITEM));
        assertThrows(EntityDeletedException.class, () -> _characterInventory.representation());
    }

    @Test
    void testCharacterDeletedInvariant() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class, () -> _characterInventory.add(ITEM));
        assertThrows(IllegalStateException.class, () -> _characterInventory.contains(ITEM));
        assertThrows(IllegalStateException.class, () -> _characterInventory.remove(ITEM));
        assertThrows(IllegalStateException.class, () -> _characterInventory.representation());
    }
}
